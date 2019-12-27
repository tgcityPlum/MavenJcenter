package com.tgcity.function;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tgcity.base.eventbus.EventBusMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author TGCity
 * 本层主要监听系统内存并作出相应动作，与BaseMemoryActivity功能类似
 * 参考地址：https://mp.weixin.qq.com/s?__biz=MzIxNjc0ODExMA==&mid=2247484311&idx=1&sn=1fe0416bed4137dd45c6e9c153bb14f4&chksm=97851ab6a0f293a0cde28ff6d1091b2232e1758e9845a05549d01c62f412def742985d642630&scene=21#wechat_redirect
 */

public abstract class BaseEventBusFragment extends BaseLazyLoadFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //是否注册eventBus
        if (isUseEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 是否使用eventBus
     * @return boolean
     */
    public boolean isUseEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(EventBusMessage message) {
        onSubscribeBack(message);
    }

    public void onSubscribeBack(EventBusMessage message) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁监听广播
        if (isUseEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }

}
