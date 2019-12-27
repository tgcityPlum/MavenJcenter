package com.tgcity.function.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tgcity.xutils.FragmentNavigatorUtils;

/**
 * @author TGCity
 * 基础的activity
 * --处理Activity中fragment的切换，主要体现在登录模块
 */
public abstract class BaseFragmentNavigatorActivity extends BaseMobClickActivity {

    private FragmentNavigatorUtils navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isUseFragmentNavigator()) {
            navigator = new FragmentNavigatorUtils(getSupportFragmentManager(), setFragmentLayoutId());
        }
    }

    /**
     * 默认不开启 Fragment指示器
     *
     * @return boolean
     */
    public boolean isUseFragmentNavigator() {
        return false;
    }

    /**
     * 设置fragment显示的根布局id
     *
     * @return int
     */
    public int setFragmentLayoutId() {
        return 0;
    }

    public void navigateToSelf() {
        if (navigator != null) {
            navigator.navigateToSelf();
        }
    }

    public void navigateTo(Fragment fragment) {
        if (navigator != null) {
            navigator.navigateTo(fragment, fragment.getClass().getSimpleName());
        }
    }

    public void navigateBack() {
        if (navigator != null) {
            navigator.navigateBack();
        }
    }

}
