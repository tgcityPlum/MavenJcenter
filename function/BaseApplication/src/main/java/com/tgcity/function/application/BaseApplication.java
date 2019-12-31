package com.tgcity.function.application;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.tgcity.utils.LogUtils;
import com.tgcity.utils.SystemUtils;

import java.util.List;

import me.jessyan.autosize.AutoSizeConfig;

/**
 * @author TGCity
 * @date 2019/12/31
 * @describe
 */
public class BaseApplication extends MultiDexApplication {
    /**
     * 静态单例
     */
    private static BaseApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instances == null) {
            instances = this;
        }
        //设置LogUtils开关
        LogUtils.init(this);
        //判断是否为主进程。防止重复初始化
        String mainProcessName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == Process.myPid()) {
                    mainProcessName = procInfo.processName;
                }
            }
        }
        if (getPackageName().equals(mainProcessName)) {
            //初始化屏幕适配
            AutoSizeConfig.getInstance()
                    .setCustomFragment(true)
                    .setLog(SystemUtils.isApkDebug(this))
                    .getUnitsManager()
                    .setSupportDP(true)
                    .setSupportSP(true);

        }

    }

    public static BaseApplication getInstances() {
        if (instances == null) {
            instances = new BaseApplication();
        }
        return instances;
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
