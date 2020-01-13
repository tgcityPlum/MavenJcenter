package com.tgcity.profession.crash.engine;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.tgcity.profession.crash.interfaces.CrashCallback;
import com.tgcity.utils.SystemUtils;

/**
 * @author tgcity
 * @description: crash核心类
 */
public class Crash {

    @SuppressLint("StaticFieldLeak")
    private volatile static Crash sInstance;

    private Context mContext;

    private CrashCallback mCallback;

    private Crash() {
    }

    public static Crash getInstance() {
        if (sInstance == null) {
            synchronized (Crash.class) {
                if (sInstance == null) {
                    sInstance = new Crash();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        if (context == null) {
            throw new RuntimeException("Context can not be null!");
        }
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        mContext = context;
        if (!SystemUtils.isMainProcess(context)) {
            return;
        }
        registerRecoveryHandler();
        registerRecoveryLifecycleCallback();
    }

    public Crash callback(CrashCallback callback) {
        this.mCallback = callback;
        return this;
    }

    private void registerRecoveryHandler() {
        CrashHandler.newInstance(Thread.getDefaultUncaughtExceptionHandler()).setCallback(mCallback).register();
    }

    private void registerRecoveryLifecycleCallback() {
        ((Application) mContext).registerActivityLifecycleCallbacks(new CrashLifecycleCallback());
    }

    Context getContext() {
        return mContext;
    }

}
