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

    private OkHttpClient mOkHttpClient;

    private boolean isReadCookie = false;
    private boolean isSaveCookie = false;
    private boolean isHostname = false;
    private String token;
    private String authorization;

    public AbstractRetrofitUtils setReadCookie(boolean readCookie) {
        isReadCookie = readCookie;

        return this;
    }

    public AbstractRetrofitUtils setSaveCookie(boolean saveCookie) {
        isSaveCookie = saveCookie;

        return this;
    }

    public AbstractRetrofitUtils setHostname(boolean hostname) {
        isHostname = hostname;

        return this;
    }

    public AbstractRetrofitUtils setToken(String token) {
        this.token = token;

        return this;
    }

    public AbstractRetrofitUtils setAuthorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    /**
     * 获取Retrofit对象
     *
     * @return Retrofit
     */
    public Retrofit getRetrofit(String url) {
        Retrofit retrofit;
        /*if (null == mOkHttpClient) {
            mOkHttpClient = OkHttp3Utils.getInstance()
                    .setReadCookie(isReadCookie)
                    .setSaveCookie(isSaveCookie)
                    .setHostname(isHostname)
                    .setToken(token)
                    .getOkHttpClient();
        }*/
        mOkHttpClient = OkHttp3Utils.getInstance()
                .setReadCookie(isReadCookie)
                .setSaveCookie(isSaveCookie)
                .setHostname(isHostname)
                .setToken(token)
                .setAuthorization(authorization)
                .getOkHttpClient();
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
