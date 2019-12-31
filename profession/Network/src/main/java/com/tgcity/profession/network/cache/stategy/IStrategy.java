package com.tgcity.profession.network.cache.stategy;


import com.tgcity.profession.network.cache.RxCache;
import com.tgcity.profession.network.cache.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;


/**
 * @author TGCity
 * 实现缓存策略的接口，可以自定义缓存实现方式，只要实现该接口就可以了
 */
public interface IStrategy {

    /**
     * 执行缓存
     *
     * @param rxCache   缓存管理对象
     * @param apiName  api名称
     * @param requestData  请求数据
     * @param cacheTime 缓存时间
     * @param source    网络请求对象
     * @param type     要转换的目标对象
     * @return 返回带缓存的Observable流对象
     */
    <T> Observable<CacheResult<T>> execute(RxCache rxCache, String apiName, String requestData, long cacheTime, Observable<T> source, Type type);

}
