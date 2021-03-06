package com.tgcity.profession.network.cache.stategy;


import android.support.annotation.NonNull;

import com.tgcity.profession.network.cache.RxCache;
import com.tgcity.profession.network.cache.model.CacheResult;
import com.tgcity.profession.network.greendao.helper.HttpKeyOperationHelper;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okio.ByteString;

/**
 * @author TGCity
 * 先显示缓存，再请求网络，处理数据的重复逻辑
 * 使用过程中需要重写返回实体类的toString方法
 */
public final class CacheAndRemoteDistinctStrategy extends BaseStrategy {

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
                    }).distinctUntilChanged(new Function<CacheResult<T>, String>() {

                        @Override
                        public String apply(@NonNull CacheResult<T> tCacheResult) throws Exception {
                            //需要重写T的toString()
                            return ByteString.of(tCacheResult.data.toString().getBytes()).md5().hex();
                        }
                    });
        }

    }

}
