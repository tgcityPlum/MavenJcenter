package com.tgcity.profession.network.callback;



import com.tgcity.profession.network.utils.CallBackUtils;

import java.lang.reflect.Type;

/**
 * @author TGCity
 * 网络请求回调
 */
public abstract class AbstractCallBack<T> implements IType<T> {

    /**
     * start
     */
    public abstract void onStart();

    /**
     * complete
     */
    public abstract void onCompleted();

    /**
     * error
     *
     * @param e Throwable
     */
    public abstract void onError(Throwable e);

    /**
     * next
     *
     * @param t T
     */
    public abstract void onNext(T t);

    /**
     * 获取需要解析的泛型T类型
     *
     * @return Type
     */
    @Override
    public Type getType() {
        return CallBackUtils.findNeedClass(getClass());
    }

    /**
     * 获取需要解析的泛型T raw类型
     *
     * @return Type
     */
    public Type getRawType() {
        return CallBackUtils.findRawType(getClass());
    }
}
