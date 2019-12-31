package com.tgcity.profession.network.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author TGCity
 * 封装一个retrofit集成0kHttp3的抽象基类
 * 如果需多个baseUrl 可设置多个mRetrofit 并添加get方法
 */
public abstract class AbstractRetrofitUtils {

    private static OkHttpClient mOkHttpClient;

    /**
     * 获取Retrofit对象
     *
     * @return Retrofit
     */
    protected static Retrofit getRetrofit(String url) {
        Retrofit retrofit;

        if (null == mOkHttpClient) {
            mOkHttpClient = OkHttp3Utils.getOkHttpClient();
        }
        //Retrofit2后使用build设计模式
        retrofit = new Retrofit.Builder()
                //设置服务器路径
                .baseUrl(url)
                //添加转化库，默认是GSon
                .addConverterFactory(GsonConverterFactory.create())
                //添加回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //设置使用okHttp网络请求
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }

}
