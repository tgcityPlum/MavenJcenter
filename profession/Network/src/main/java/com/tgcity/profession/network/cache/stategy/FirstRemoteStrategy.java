package com.tgcity.profession.network.cache.stategy;


import com.tgcity.profession.network.cache.RxCache;
import com.tgcity.profession.network.cache.model.CacheResult;
import com.tgcity.profession.network.greendao.helper.HttpKeyOperationHelper;

import java.lang.reflect.Type;
import java.util.Arrays;

import io.reactivex.Observable;


/**
 * @author TGCity
 * 先请求网络，网络请求失败，再加载缓存
 * 如果次数超出规定限制 直接读取缓存
 */
public final class FirstRemoteStrategy extends BaseStrategy {

    @Override
    public <T> Observable<CacheResult<T>> execute(RxCache rxCache, String apiName, String requestData, long time, Observable<T> source, Type type) {

        //如果次数超出限制 直接读取缓存
        if (HttpKeyOperationHelper.getInstance().Effective(apiName, requestData)) {
            return loadCache(rxCache, type, apiName ,requestData, time, false);
        } else {
            Observable<CacheResult<T>> cache = loadCache(rxCache, type, apiName ,requestData, time, true);
            Observable<CacheResult<T>> remote = loadRemote(rxCache, apiName, requestData, source, false);
            //return remote.switchIfEmpty(cache);
            return Observable
                    .concatDelayError(Arrays.asList(remote, cache))
                    .take(1);
        }


    }
}
