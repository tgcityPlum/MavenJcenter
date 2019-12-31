package com.tgcity.profession.network.cache.stategy;



import android.support.annotation.NonNull;

import com.tgcity.function.network.cache.ErrorMode;
import com.tgcity.function.network.retrofit.ApiException;
import com.tgcity.profession.network.cache.RxCache;
import com.tgcity.profession.network.cache.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @author TGCity
 * 描述：实现缓存策略的基类
 */
public abstract class BaseStrategy implements IStrategy {

    <T> Observable<CacheResult<T>> loadCache(final RxCache rxCache, Type type, final String apiName, final String requestData, final long time, final boolean needEmpty) {
        Observable<CacheResult<T>> observable = rxCache.<T>load(type, apiName + requestData, time).flatMap(new Function<T, ObservableSource<CacheResult<T>>>() {

            @Override
            public ObservableSource<CacheResult<T>> apply(T t) throws Exception {
                if (t == null) {
                    return Observable.error(new ApiException(ErrorMode.NO_CACHE));
                }
                return Observable.just(new CacheResult<T>(true, apiName, requestData, t));
            }
        });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult<T>>>() {
                        @Override
                        public ObservableSource<? extends CacheResult<T>> apply(Throwable throwable) throws Exception {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }

    /**
     * 因策略修改 改方法暂时不使用  先保留 后续迭代酌情考虑
     *
     * @param rxCache     RxCache
     * @param apiName     String
     * @param requestData String
     * @param source      Observable
     * @param needEmpty   boolean
     * @param <T>         T
     * @return T
     */
    <T> Observable<CacheResult<T>> loadRemote2(final RxCache rxCache, final String apiName, final String requestData, Observable<T> source, final boolean needEmpty) {
        Observable<CacheResult<T>> observable = source
                .map(new Function<T, CacheResult<T>>() {

                    @Override
                    public CacheResult<T> apply(T t) throws Exception {
                        rxCache.save(apiName+requestData, t).subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(@NonNull Boolean status) throws Exception {
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                        throwable.printStackTrace();
                                    }
                                });
                        return new CacheResult<T>(false, apiName, requestData, t);
                    }
                });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult<T>>>() {
                        @Override
                        public ObservableSource<? extends CacheResult<T>> apply(Throwable throwable) throws Exception {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }

    /**
     * 请求成功后：同步保存
     * @param rxCache RxCache
     * @param apiName String
     * @param requestData String
     * @param source Observable
     * @param needEmpty boolean
     * @param <T> T
     * @return T
     */
    <T> Observable<CacheResult<T>> loadRemote(final RxCache rxCache, final String apiName, final String requestData, Observable<T> source, final boolean needEmpty) {

        Observable<CacheResult<T>> observable = source
                .flatMap(new Function<T, ObservableSource<CacheResult<T>>>() {

                    @Override
                    public ObservableSource<CacheResult<T>> apply(final T t) {
                        return rxCache.save(apiName + requestData, t).map(new Function<Boolean, CacheResult<T>>() {
                            @Override
                            public CacheResult<T> apply(@NonNull Boolean aBoolean) throws Exception {
                                return new CacheResult<T>(false,apiName,requestData, t);
                            }
                        }).onErrorReturn(new Function<Throwable, CacheResult<T>>() {
                            @Override
                            public CacheResult<T> apply(@NonNull Throwable throwable) throws Exception {

                                return new CacheResult<T>(false, apiName, requestData, t);
                            }
                        });
                    }
                });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult<T>>>() {
                        @Override
                        public ObservableSource<? extends CacheResult<T>> apply(Throwable throwable) throws Exception {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }
}
