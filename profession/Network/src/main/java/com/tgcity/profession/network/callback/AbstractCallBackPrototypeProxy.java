package com.tgcity.profession.network.callback;

import com.google.gson.internal.$Gson$Types;
import com.tgcity.profession.network.cache.model.CacheResult;
import com.tgcity.profession.network.utils.CallBackUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * @author TGCity
 * 回调代理
 */
public abstract class AbstractCallBackPrototypeProxy<T extends R, R> implements IType<T> {

    AbstractCallBack<R> mCallBack;

    public AbstractCallBackPrototypeProxy(AbstractCallBack<R> callBack) {
        mCallBack = callBack;
    }

    public AbstractCallBack getCallBack() {
        return mCallBack;
    }

    /**
     * CallBack代理方式，获取需要解析的Type
     * @return Type
     */
    @Override
    public Type getType() {
        Type typeArguments = null;
        if (mCallBack != null) {
            //如果用户的信息是返回List需单独处理
            Type rawType = mCallBack.getRawType();
            if (List.class.isAssignableFrom(CallBackUtils.getClass(rawType, 0)) || Map.class.isAssignableFrom(CallBackUtils.getClass(rawType, 0))) {
                typeArguments = mCallBack.getType();
            } else if (CacheResult.class.isAssignableFrom(CallBackUtils.getClass(rawType, 0))) {
                Type type = mCallBack.getType();
                typeArguments = CallBackUtils.getParameterizedType(type, 0);
            } else {
                Type type = mCallBack.getType();
                typeArguments = CallBackUtils.getClass(type, 0);
            }
        }
        if (typeArguments == null) {
            typeArguments = ResponseBody.class;
        }
        Type rawType = CallBackUtils.findNeedType(getClass());
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }
        return $Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments);
    }
}
