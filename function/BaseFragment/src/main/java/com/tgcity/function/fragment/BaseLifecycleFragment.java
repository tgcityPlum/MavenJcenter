package com.tgcity.function.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.tgcity.function.basefragment.R;
import com.tgcity.profession.lifecycler.BaseLifecycleObserver;
import com.tgcity.utils.LogUtils;

/**
 * @author TGCity
 * @Describe 生命周期管理
 */
public abstract class BaseLifecycleFragment extends Fragment implements BaseLifecycleObserver {

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
