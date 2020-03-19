package com.tgcity.function.activity;

import androidx.lifecycle.LifecycleOwner;

import com.umeng.analytics.MobclickAgent;

/**
 * @author TGCity
 * @date 2019/9/10
 * @describe 友盟点击事件监听
 */
public abstract class BaseMobClickActivity extends BaseImmersionBarActivity {

    @Override
    public void onResume(LifecycleOwner owner) {
        super.onResume(owner);

        MobclickAgent.onPageStart(getCurrentPage());
        MobclickAgent.onResume(getAppContext());
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        super.onPause(owner);

        MobclickAgent.onPageEnd(getCurrentPage());
        MobclickAgent.onPause(getAppContext());
    }

}
