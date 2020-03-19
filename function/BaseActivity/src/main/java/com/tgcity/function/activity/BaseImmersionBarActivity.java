package com.tgcity.function.activity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.tgcity.function.baseactivity.R;
import com.tgcity.utils.LogUtils;
import com.tgcity.xwidget.TitleBar;

/**
 * @author TGCity
 * 基础的activity
 * --处理Activity的沉浸式状态栏
 */
public abstract class BaseImmersionBarActivity extends BaseEventLogicActivity {
    /**
     * Immersion对象
     */
    private ImmersionBar mImmersionBar;
    /**
     * 标题栏对象
     */
    private View mToolBar;
    private View mTitleBar;
    /**
     * 背景颜色
     */
    private int mDefaultColor = -110;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFullScreen()) {
            mImmersionBar = ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
            mImmersionBar.init();
        } else {
            if (isUseImmersionBar()) {
                autoImmersionBar();
            } else {
                onInitImmersionBar();
            }
        }
    }

    public void onInitImmersionBar() {

    }

    /**
     * 全屏显示
     *
     * @return true 全屏  false 默认显示
     */
    public boolean isFullScreen() {
        return false;
    }

    /**
     * 沉浸式的启动状态
     *
     * @return true 开启   false 关闭
     */
    public boolean isUseImmersionBar() {
        return false;
    }

    /**
     * 设置沉浸式
     */
    public void autoImmersionBar() {
        LogUtils.d(getString(R.string.base_memory_activity_current_page_name, getCurrentPage(), getLocalClassName(), getString(R.string.base_immersion_bar_activity_autoImmersionBar)));
        mImmersionBar = ImmersionBar.with(this);
        //根据布局文件中 R.id.titleBar 寻找titleBar对象
        mTitleBar = findViewById(R.id.titleBar);
        if (mTitleBar != null) {
            //寻找titleBar对象
            //判断标题栏是否为titleBar
            if (mTitleBar instanceof TitleBar) {
                mToolBar = findViewById(R.id.toolBar);
                //判断背景
                if (mTitleBar.getBackground() == null && mToolBar.getBackground() == null) {
                    //没有设置背景，直接初始化
                    mImmersionBar.titleBar(mTitleBar);
                    mImmersionBar.init();
                    return;
                }
                mImmersionBar.titleBar(mToolBar);
                //首先获取内部的toolBar色值
                Drawable drawable = mToolBar.getBackground();
                if (drawable == null) {
                    //不存在再取titleBar色值
                    drawable = mTitleBar.getBackground();
                }

                immersionBarColor(drawable);
            } else {
                mImmersionBar.titleBar(mTitleBar);
            }
            mImmersionBar.init();
        } else {
            //未寻找titleBar对象
            //根据布局文件中 R.id.toolBar 寻找toolBar对象
            mToolBar = findViewById(R.id.toolBar);
            if (mToolBar != null) {
                //设置背景色
                Drawable drawable = mToolBar.getBackground();
                immersionBarColor(drawable);

                mImmersionBar.titleBar(mToolBar);
                mImmersionBar.init();
            }
        }
    }

    /**
     * 设置沉浸式背景色
     */
    private void immersionBarColor(Drawable drawable) {
        if (drawable == null) {
            return;
        }

        if (drawable instanceof ColorDrawable) {
            int color = ((ColorDrawable) drawable).getColor();
            //如果比#dddddd的色值深，则使用深色的状态栏文字,如果系统不支持，则使用透明度来显示
            if (color > -2236963) {
                mImmersionBar.statusBarDarkFont(true, 0.5f);
            } else {
                mImmersionBar.statusBarDarkFont(false, 0.5f);
            }
        }
    }

    /**
     * 沉浸式状态栏
     *
     * @param view       toolbar
     * @param isDarkFont 状态栏是否深色字体
     */
    public void immersionBar(View view, boolean isFitsSystemWindows, boolean isDarkFont) {
        mImmersionBar = ImmersionBar.with(this);

        if (view != null) {
            mImmersionBar.titleBar(view);
        }

        if (isDarkFont) {
            mImmersionBar.statusBarDarkFont(isDarkFont, 0.2f);
        } else {
            mImmersionBar.statusBarDarkFont(isDarkFont);
        }
        mImmersionBar.transparentStatusBar();
        mImmersionBar.fitsSystemWindows(isFitsSystemWindows);

        if (isFitsSystemWindows) {
            mImmersionBar.statusBarColor(R.color.color_white);
        }
        mImmersionBar.init();
    }

    /**
     * 沉浸式状态栏
     */
    public void immersionBar(boolean isFitsSystemWindows) {
        immersionBar(null, isFitsSystemWindows, true);
    }

    /**
     * 沉浸式状态栏
     */
    public void immersionBar(boolean isFitsSystemWindows, boolean isDarkFont) {
        immersionBar(null, isFitsSystemWindows, isDarkFont);
    }

    /**
     * 获取默认沉浸式颜色标识
     */
    private int getDefaultColor() {
        return mDefaultColor;
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        mImmersionBar = null;
        mToolBar = null;
        mTitleBar = null;
        super.onDestroy(owner);
    }

}
