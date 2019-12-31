package com.tgcity.profession.network.subsciber;

import android.content.Context;
import android.support.annotation.NonNull;


import com.tgcity.function.network.retrofit.ApiException;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

import static com.tgcity.profession.network.utils.CallBackUtils.isNetworkAvailable;


/**
 * @author TGCity
 * 订阅的基类
 * 1.可以防止内存泄露。
 * 2.在onStart()没有网络时直接onCompleted();
 * 3.统一处理了异常
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    public WeakReference<Context> contextWeakReference;
    
    public BaseSubscriber() {
    }

    @Override
    public void onStart() {
        if (contextWeakReference != null && contextWeakReference.get() != null && !isNetworkAvailable(contextWeakReference.get())) {
            //Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).showLruCache();
            onComplete();
        }
    }


    public BaseSubscriber(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onComplete() {

    }


    public abstract void onError(ApiException e);

}
