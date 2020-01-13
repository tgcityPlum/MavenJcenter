package com.tgcity.profession.crash.engine;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

/**
 * @author tgcity
 * @description: 重写生命周期
 */
public class CrashLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull final Activity activity) {
        //判断activity是否已存在，存在则跳过
        if (CrashStore.getInstance().contains(activity)){
            return;
        }

        Window window = activity.getWindow();
        if (window != null) {
            //获取父类
            View decorView = window.getDecorView();
            if (decorView == null){
                return;
            }
            decorView.post(new Runnable() {
                @Override
                public void run() {
                    //存储activity类
                    CrashStore.getInstance().putActivity(activity);
                }
            });
        }

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        CrashStore.getInstance().removeActivity(activity);
    }

}