package com.tgcity.widget.handler;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.tgcity.profession.lifecycler.BaseLifecycleObserver;

/**
 * @author TGCity
 * @date 2020/3/20
 * @description handler结合lifecycle
 */
public class LifecycleHandler extends Handler implements BaseLifecycleObserver {

    private LifecycleOwner lifecycleOwner;

    public LifecycleHandler(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(@Nullable Callback callback, LifecycleOwner lifecycleOwner) {
        super(callback);
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(@NonNull Looper looper, LifecycleOwner lifecycleOwner) {
        super(looper);
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(@NonNull Looper looper, @Nullable Callback callback, LifecycleOwner lifecycleOwner) {
        super(looper, callback);
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    /**
     * 绑定观察者模式
     */
    private void addObserver() {
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    switch (event) {
                        case ON_CREATE:
                            onCreate(source);
                            break;
                        case ON_START:
                            onStart(source);
                            break;
                        case ON_RESUME:
                            onResume(source);
                            break;
                        case ON_PAUSE:
                            onPause(source);
                            break;
                        case ON_STOP:
                            onStop(source);
                            break;
                        case ON_DESTROY:
                            onDestroy(source);
                            break;
                        case ON_ANY:
                            throw new IllegalArgumentException("ON_ANY must not been send by anybody");
                    }
                }
            });
        }
    }

    @Override
    public void onCreate(LifecycleOwner owner) {

    }

    @Override
    public void onStart(LifecycleOwner owner) {

    }

    @Override
    public void onResume(LifecycleOwner owner) {

    }

    @Override
    public void onPause(LifecycleOwner owner) {

    }

    @Override
    public void onStop(LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        removeCallbacksAndMessages(null);
    }

}
