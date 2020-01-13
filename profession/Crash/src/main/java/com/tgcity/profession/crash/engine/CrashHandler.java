package com.tgcity.profession.crash.engine;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import com.tgcity.profession.crash.activity.CrashActivity;
import com.tgcity.profession.crash.interfaces.CrashCallback;
import com.tgcity.utils.SystemUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author TGCity
 */
final class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private CrashCallback mCallback;

    private CrashHandler(Thread.UncaughtExceptionHandler defHandler) {
        mDefaultUncaughtExceptionHandler = defHandler;
    }

    static CrashHandler newInstance(Thread.UncaughtExceptionHandler defHandler) {
        return new CrashHandler(defHandler);
    }

    @Override
    public synchronized void uncaughtException(Thread t, Throwable e) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();

        String stackTrace = sw.toString();
        String cause = e.getMessage();
        Throwable rootTr = e;
        while (e.getCause() != null) {
            e = e.getCause();
            e.getStackTrace();
            if (e.getStackTrace().length > 0) {
                rootTr = e;
            }
            String msg = e.getMessage();
            if (!TextUtils.isEmpty(msg)) {
                cause = msg;
            }
        }

        String exceptionType = rootTr.getClass().getName();

        String throwClassName;
        String throwMethodName;
        int throwLineNumber;

        if (rootTr.getStackTrace().length > 0) {
            StackTraceElement trace = rootTr.getStackTrace()[0];
            throwClassName = trace.getClassName();
            throwMethodName = trace.getMethodName();
            throwLineNumber = trace.getLineNumber();
        } else {
            throwClassName = "unknown";
            throwMethodName = "unknown";
            throwLineNumber = 0;
        }

        //处理回调方法
        if (mCallback != null) {
            mCallback.stackTrace(stackTrace);
            mCallback.exception(exceptionType, cause, throwClassName, throwMethodName, throwLineNumber);
            mCallback.throwable(e);
        }

        if (!isSystemDefaultUncaughtExceptionHandler(mDefaultUncaughtExceptionHandler)) {
            if (mDefaultUncaughtExceptionHandler == null) {
                killProcess();
                return;
            }
            recover();
            mDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            recover();
            killProcess();
        }

    }

    private boolean isSystemDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        if (handler == null) {
            return false;
        }
        Thread.UncaughtExceptionHandler defHandler = null;

        try {
            Class<?> clazz;
            if (Build.VERSION.SDK_INT >= 26) {
                clazz = Class.forName("com.android.internal.os.RuntimeInit$KillApplicationHandler");
            } else {
                clazz = Class.forName("com.android.internal.os.RuntimeInit$UncaughtHandler");
            }

            Object object = clazz.getDeclaredConstructor().newInstance();
            defHandler = (Thread.UncaughtExceptionHandler) object;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return defHandler != null && defHandler.getClass().isInstance(handler);
    }

    CrashHandler setCallback(CrashCallback callback) {
        mCallback = callback;
        return this;
    }

    private void recover() {

        if (SystemUtils.isAppInBackground(Crash.getInstance().getContext())) {
            killProcess();
            return;
        }

        startRecoverActivity();
    }

    private void startRecoverActivity() {
        Intent intent = new Intent();
        intent.setClass(Crash.getInstance().getContext(), CrashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);


        Crash.getInstance().getContext().startActivity(intent);
    }

    void register() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

}
