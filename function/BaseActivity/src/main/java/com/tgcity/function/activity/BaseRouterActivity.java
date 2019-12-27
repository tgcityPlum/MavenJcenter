package com.tgcity.function.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * @author TGCity
 * 基础的activity类
 * --绑定路由
 */
public abstract class BaseRouterActivity extends BaseBindViewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isUseARouter()){
            //注册 路由
            ARouter.getInstance().inject(getContext());
        }
    }

    public boolean isUseARouter() {
        return false;
    }

}
