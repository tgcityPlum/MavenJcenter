package com.tgcity.function.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.tgcity.function.baseactivity.R;
import com.tgcity.utils.LogUtils;

/**
 * @author TGCity
 * 基础的activity类
 * --处理事件逻辑
 */
public abstract class BaseEventLogicActivity extends BaseRouterActivity {
    /**
     * 是否启动过，暂时仅仅用来在singleTask/singleInstance/singleTop模式下
     * 或其他类似场景下的不销毁Activity重新加载数据
     */
    private boolean launched;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        boolean flag = depositBeforeAll();

        if (flag) {
            super.onCreate(savedInstanceState);
            depositParameter();

            useInstanceState(savedInstanceState);

            initView();
            logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_initView)));

            setListener();
            launched = true;
        }
    }

    public void useInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (isReLoadWithNewIntent()) {
            setIntent(putIntent() == null ? intent : putIntent());
            depositParameter();
            initView();
        }
        super.onNewIntent(intent);
    }

    private Intent putIntent() {
        return getIntent();
    }

    /**
     * 是否重新跳转
     */
    public boolean isReLoadWithNewIntent() {
        return false;
    }

    /**
     * 是否已启动
     */
    public boolean isLaunched() {
        return launched;
    }

    public void setListener() {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_setListener)));
    }

    public abstract void initView();

    public void depositParameter() {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_depositParameter)));
    }

    public boolean depositBeforeAll() {
        logActivity(getCurrentPageName(getString(R.string.base_event_logic_activity_depositBeforeAll)));
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        launched = false;
    }

}
