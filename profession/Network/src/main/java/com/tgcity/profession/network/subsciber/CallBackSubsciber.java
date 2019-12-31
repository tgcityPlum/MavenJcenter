package com.tgcity.profession.network.subsciber;

import android.content.Context;
import android.support.annotation.NonNull;


import com.tgcity.function.network.retrofit.ApiException;
import com.tgcity.profession.network.callback.AbstractCallBack;

/**
 * @author TGCity
 * 带有callBack的回调
 * 主要作用是不需要用户订阅，只要实现callback回调
 */
public class CallBackSubsciber<T> extends BaseSubscriber<T> {

    public AbstractCallBack<T> mCallBack;
    

    public CallBackSubsciber(Context context, AbstractCallBack<T> callBack) {
        super(context);
        mCallBack = callBack;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mCallBack != null) {
            mCallBack.onStart();
        }
    }
    
    @Override
    public void onError(ApiException e) {
        if (mCallBack != null) {
            mCallBack.onError(e);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        super.onNext(t);
        if (mCallBack != null) {
            mCallBack.onNext(t);
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (mCallBack != null) {
            mCallBack.onCompleted();
        }
    }
}
