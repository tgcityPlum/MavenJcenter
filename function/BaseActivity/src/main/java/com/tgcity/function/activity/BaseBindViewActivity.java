package com.tgcity.function.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.tgcity.function.baseactivity.R;
import com.tgcity.function.eventbus.EventBusMessage;
import com.tgcity.utils.ClearViewUtils;
import com.tgcity.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author TGCity
 * 基础的activity类
 * --绑定view层
 */
public abstract class BaseBindViewActivity extends BaseOrientationActivity {
    /**
     * 根布局控件
     */
    protected View rootView;
    /**
     * 空布局，销毁时置空使用
     */
    private View nullView;
    /**
     * Context
     */
    private Context mContext;
    /**
     * ButterKnife unBinder
     */
    private Unbinder unBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置根布局控件
        rootView = findViewById(android.R.id.content);
        if (rootView != null && getBackgroundColor() != 0) {
            rootView.setBackgroundColor(getBackgroundColor());
        }
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(getViewLayout());

        setButterKnifeBind();

        //是否注册eventBus
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 是否使用eventBus
     *
     * @return boolean
     */
    public boolean isUseEventBus() {
        return false;
    }

    /**
     * get background color
     */
    public int getBackgroundColor() {
        return 0;
    }

    /**
     * abstract get layout id
     *
     * @return the layout of activity
     */
    public abstract int getViewLayout();

    /**
     * set butterKnife bind
     */
    private void setButterKnifeBind() {
        unBind = ButterKnife.bind(this);
        LogUtils.d(getString(R.string.base_bind_view_activity_current_butterKnife));
    }

    /**
     * get Context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * get Context
     */
    public Context getAppContext() {
        return mContext.getApplicationContext();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(EventBusMessage message) {
        onSubscribeBack(message);
    }

    public void onSubscribeBack(EventBusMessage message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //destroy unBinder
        if (unBind != null) {
            unBind.unbind();
            unBind = null;
        }
        //destroy rootView
        ClearViewUtils.clearAll(rootView);

        nullView = new View(mContext);
        setContentView(nullView);
        nullView = null;
        mContext = null;
        //销毁监听广播
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

}
