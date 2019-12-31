package com.tgcity.profession.network.retrofit;


import com.tgcity.profession.network.base.NetworkConstant;
import com.tgcity.profession.network.bean.NetworkResponseEntity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import static okhttp3.internal.Util.UTF_8;

//import com.xietong.network.utils.HttpSSLUtils;

/**
 * @author TGCity
 * okHttp的配置
 * 以及公共请求头配置地址
 */

public class OkHttp3Utils {
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            HttpSSLUtils.SSLParams sslParams = HttpSSLUtils.getSslSocketFactory();
            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //添加拦截器
                    .addInterceptor(mTokenInterceptor)
                    .hostnameVerifier(HttpSSLUtils.getHostnameVerifier())
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 云端响应头拦截器
     * 用于添加统一请求头  请按照自己的需求添加
     */
    private static final Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request authorised = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
//                    .addHeader("User-Agent", "Android-XT-UZ-PHONE")
                    .build();
            if (NetworkConstant.Switch.IS_PRINT_NETWORK_LOG) {
                return response(chain.proceed(authorised));
            } else {
                return chain.proceed(authorised);
            }
        }
    };

    /**
     * Response处理
     *
     * @param response
     * @return
     */
    private static Response response(Response response) throws IOException {
        NetworkResponseEntity networkResponseInfo = new NetworkResponseEntity();
        networkResponseInfo.setSendTime(response.sentRequestAtMillis());
        networkResponseInfo.setReceiveTime(response.receivedResponseAtMillis());
        networkResponseInfo.setCode(response.code());
        networkResponseInfo.setMessage(response.message());
        networkResponseInfo.setRedirect(response.isRedirect());
        networkResponseInfo.setProtocol(response.protocol().toString());
        networkResponseInfo.setSuccessful(response.isSuccessful());
        networkResponseInfo.setUrl(response.request().url().toString());
        networkResponseInfo.setHttps(response.request().isHttps());
        networkResponseInfo.setMethod(response.request().method());

        if (response.headers() != null) {
            networkResponseInfo.setHeaders(response.headers().toString());
        } else {
            networkResponseInfo.setHeaders("");
        }
        if (response.request().body() != null) {
            try {
                Buffer buffer = new Buffer();
                response.request().body().writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = response.request().body().contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF_8);
                }
                networkResponseInfo.setSend(buffer.readString(charset));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (response.body() != null) {
            String temp = response.body().string();
            networkResponseInfo.setContent(temp == null ? "" : temp);

        }
        networkResponseInfo.print("---", NetworkConstant.Switch.IS_JSON_FORMAT);
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), networkResponseInfo.getContent()))
                .build();
    }
}
