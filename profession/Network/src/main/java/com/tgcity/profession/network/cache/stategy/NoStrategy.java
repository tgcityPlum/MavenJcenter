package com.tgcity.profession.network.cache.stategy;

import com.tgcity.function.network.cache.ErrorMode;
import com.tgcity.function.network.retrofit.ApiException;
import com.tgcity.profession.network.cache.RxCache;
import com.tgcity.profession.network.cache.model.CacheResult;
import com.tgcity.profession.network.greendao.helper.HttpKeyOperationHelper;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * @author TGCity
 * 网络加载，不缓存
 * 如果次数超出规定限制 直接报错
 */
public class NoStrategy implements IStrategy {

    @Override
    public <T> Observable<CacheResult<T>> execute(RxCache rxCache, final String apiName, final String requestData, long cacheTime, Observable<T> source, Type type) {

        if (HttpKeyOperationHelper.getInstance().Effective(apiName, requestData)) {

            return source.error(new ApiException(ErrorMode.OVERLOAD));

        } else {
            return source.map(new Function<T, CacheResult<T>>() {

                @Override
                public CacheResult<T> apply(T t) throws Exception {
                    return new CacheResult<T>(false, apiName, requestData, t);
                }
            });
        }
    }
}
