package com.tgcity.profession.network.callback;


import com.tgcity.profession.network.bean.result.HttpCommonResult;

/**
 * @author TGCity
 *
 * 提供回调代理
 * 主要用于可以自定义ApiResult
/**
 * 因为#{@link com.eagersoft.youzy.youzy.constants.AppConstant.TZY_URL}地址下的接口返回参数是由isSuccess来判断，
 * 所以新增一套回调代理，与原来的互不影响，区分的时候只需要在
 * #{@link BaseHttpData.Builder.extraRemark}参数填入
 * #{@link com.eagersoft.youzy.youzy.constants.AppConstant.API_SERVICE_TZY}即可
 */
@SuppressWarnings("ALL")
public abstract class AbstractSimpleCallBackProxy<T extends HttpCommonResult<R>, R> extends AbstractCallBackPrototypeProxy {
    AbstractCallBack<R> mCallBack;

    public AbstractSimpleCallBackProxy(AbstractCallBack<R> callBack) {
        super(callBack);
        mCallBack = callBack;
    }

    @Override
    public AbstractCallBack getCallBack() {
        return mCallBack;
    }
}
