package com.tgcity.function.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.tgcity.function.basefragment.R;
import com.tgcity.utils.ClearViewUtils;
import com.tgcity.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author TGCity
 * 懒加载fragment，扩展了沉浸式，支持嵌套懒加载
 */

public abstract class BaseLazyLoadFragment extends BaseLifecycleFragment {
    protected View rootView = null;
    /**
     * 是否对用户可见
     */
    protected boolean isVisible = true;
    /**
     * 是否加载完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean isPrepare;

    /**
     * 已经启动过
     */
    protected boolean isLaunched;

    /**
     * 沉浸式
     */
    private ImmersionBar mImmersionBar;
    private Context context;
    private Unbinder unBinder;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(setContent(), container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unBinder = ButterKnife.bind(this, rootView);
        onShowLoadingAnimation();
        LogUtils.d("当前模块页面 " + getClass() + " 正在调用 onShowLoadingAnimation()");
        onInitImmersionBar();
        LogUtils.d("当前模块页面 " + getClass() + " 正在调用 OnInitImmersionBar()");
        if (isLazyLoad()) {
            isPrepare = true;
            onLazyLoad();
            LogUtils.d("当前模块页面 " + getClass() + " 正在调用 onLazyLoad()");
        } else {
            init();
            LogUtils.d("当前模块页面 " + getClass() + " 正在调用 init()");
            onSetListener();
            LogUtils.d("当前模块页面 " + getClass() + " 正在调用 onSetListener()");
        }
        isLaunched = true;
    }

    /**
     * 结合FragmentPagerAdapter才能使用
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisible();
            LogUtils.d("当前模块页面 " + getClass() + " 处于正在与用户交互状态");
        } else {
            onInvisible();
            LogUtils.d("当前模块页面 " + getClass() + " 处于不与用户交互状态");
        }
    }

    /**
     * 是否懒加载
     *
     * @return the boolean
     */
    protected boolean isLazyLoad() {
        return true;
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisible() {
        onLazyLoad();
    }

    private void onLazyLoad() {
        LogUtils.d("当前模块页面 " + getClass() + " 的交互模式处于 " + isVisible + " 状态");
        if (isVisible && isPrepare) {
            isPrepare = false;
            init();
            LogUtils.d("当前模块页面 " + getClass() + " 正在调用 init()");
            onSetListener();
            LogUtils.d("当前模块页面 " + getClass() + " 正在调用 onSetListener()");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionBar = null;
        if (rootView != null) {
            ClearViewUtils.clearAll(rootView);
        }
        rootView = null;
        context = null;
    }


    /**
     * 用户不可见执行
     */
    protected void onInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null) {
            unBinder.unbind();
            unBinder = null;
        }
    }


    /**
     * 沉浸式状态栏
     *
     * @param view       toolbar
     * @param isDarkFont 状态栏是否深色字体
     */
    public void immersionBar(View view, boolean isFitsSystemWindows, boolean isDarkFont) {

        mImmersionBar = ImmersionBar.with(getActivity());

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
     * 绑定布局
     *
     * @return int
     */
    protected abstract int setContent();

    /**
     * 绑定View成功，调用与init之前，可以做加载动画之类的事情
     */
    protected void onShowLoadingAnimation() {

    }

    /**
     * 沉浸式,调用本类的immersionBar即可
     */
    public void onInitImmersionBar() {

    }

    /**
     * 初始化数据
     */
    protected abstract void init();

    /**
     * 做一些设置监听的事情
     */
    protected void onSetListener() {

    }

}