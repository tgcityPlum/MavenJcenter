package com.tgcity.profession.network.callback;

/**
 * @author TGCity
 * 描述：简单的回调,默认可以使用该回调，不用关注其他回调方法
 * 使用该回调默认只需要处理onError，onSuccess两个方法既成功失败
 * 如需要onStart onCompleted 在实际使用中 直接覆盖重写就好
 */
public abstract class AbstractSimpleCallBack<T> extends AbstractCallBack<T> {

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {

    }
}
