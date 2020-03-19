package com.tgcity.function.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.tgcity.function.baseactivity.R;
import com.tgcity.profession.lifecycler.BaseLifecycleObserver;
import com.tgcity.utils.LogUtils;

/**
 * @author TGCity
 * 基础的activity
 * --管理activity的生命周期
 */
public abstract class BaseLifecycleActivity extends AppCompatActivity implements BaseLifecycleObserver {

    @Override
    public void onCreate(LifecycleOwner owner) {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_onCreate)));
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_onStart)));
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_onResume)));
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_onPause)));
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_onStop)));
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_onDestroy)));
    }

    /**
     * 输出当前界面调用方法的日志
     */
    protected void logActivity(String message) {
        LogUtils.d(message);
    }

    public String getCurrentPageName(String message) {
        return getString(R.string.base_memory_activity_current_page_name, getCurrentPage(), getLocalClassName(), message);
    }

    /**
     * get current activity page
     *
     * @return the current activity name
     */
    public abstract String getCurrentPage();

}
