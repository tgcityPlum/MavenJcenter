package com.tgcity.profession.network.callback;


import com.tgcity.function.network.bean.HttpResult;

/**
 * @author TGCity
 *
 * 提供回调代理
 * 主要用于可以自定义ApiResult
 */
public abstract class AbstractCallBackProxy<T extends HttpResult<R>, R> extends AbstractCallBackPrototypeProxy {
    AbstractCallBack<R> mCallBack;

    public AbstractCallBackProxy(AbstractCallBack<R> callBack) {
        super(callBack);
        mCallBack = callBack;
    }

    @Override
    public AbstractCallBack getCallBack() {
        return mCallBack;
    }
}
