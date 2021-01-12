package com.tgcity.profession.network.cache.stategy;


import android.support.annotation.NonNull;

import com.tgcity.profession.network.cache.RxCache;
import com.tgcity.profession.network.cache.model.CacheResult;
import com.tgcity.profession.network.greendao.helper.HttpKeyOperationHelper;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @author TGCity
 * 先显示缓存，再请求网络，不进行数据的查重处理
 * 如果次数超出规定限制 直接读取缓存
 */
public final class CacheAndRemoteStrategy extends BaseStrategy {

    @Override
    public <T> Observable<CacheResult<T>> execute(RxCache rxCache, String apiName, String requestData, long time, Observable<T> source, Type type) {


        /*
         * 如果次数超出限制 直接读取缓存
         * */
        if (HttpKeyOperationHelper.getInstance().Effective(apiName, requestData)) {

            return loadCache(rxCache, type, apiName, requestData, time, false);

        } else {

            HttpKeyOperationHelper.getInstance().addKey(apiName, requestData);

            Observable<CacheResult<T>> cache = loadCache(rxCache, type, apiName, requestData, time, true);
            Observable<CacheResult<T>> remote = loadRemote(rxCache, apiName, requestData, source, false);

            return Observable.concat(cache, remote)
                    .filter(new Predicate<CacheResult<T>>() {
                        @Override
                        public boolean test(@NonNull CacheResult<T> tCacheResult) throws Exception {
                            return tCacheResult != null && tCacheResult.data != null;
                        }
                    });
        }

    }

}
