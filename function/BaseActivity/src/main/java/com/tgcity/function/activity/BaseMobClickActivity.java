package com.tgcity.function.activity;

import com.umeng.analytics.MobclickAgent;

/**
 * @author TGCity
 * @date 2019/9/10
 * @description 友盟点击事件监听
 */
public abstract class BaseMobClickActivity extends BaseImmersionBarActivity {

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart(getCurrentPage());
        MobclickAgent.onResume(getAppContext());
    }

    @Override
    public void onPause() {
        super.onPause();

        MobclickAgent.onPageEnd(getCurrentPage());
        MobclickAgent.onPause(getAppContext());
    }

}
