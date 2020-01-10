package com.tgcity.function.network.retrofit;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.tgcity.function.network.bean.HttpResult;
import com.tgcity.function.network.cache.ErrorMode;
import com.tgcity.function.network.cache.ServiceErrorCode;
import com.tgcity.utils.LogUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


/**
 * @author TGCity
 * 异常处理类
 */
public class ApiException extends RuntimeException {
    public ErrorMode errorMode;

    /**
     * 请求错误
     *
     * @param errorMode 错误模型
     */
    public ApiException(ErrorMode errorMode) {
        super(errorMode.getErrorTitle());
        this.errorMode = errorMode;
        LogUtils.e("ApiException", "错误详情" + errorMode.getErrorTitle());
    }

    /**
     * 请求错误
     *
     * @param errorMode 错误模型
     */
    public ApiException(String message, ErrorMode errorMode) {
        super(message);
        LogUtils.e("ApiException", "错误详情" + message);

        this.errorMode = errorMode.setErrorContent(message);
    }

    /**
     * 对服务器接口传过来的错误信息进行统一处理
     * 免除在Activity的过多的错误判断
     * 错误代码：暂未设置
     */
    public static ApiException handleException(HttpResult httpResult) {
        ApiException apiException;

        //此处不了解uzerme的后台错误码含义，暂时屏蔽
        if (ErrorMode.SERVER_NULL.getErrorCode() == httpResult.getCode()) {
            apiException = new ApiException(ErrorMode.SERVER_NULL.getErrorTitle(), ErrorMode.SERVER_NULL);
        } else {
            switch (httpResult.getCode()) {
                case ServiceErrorCode.CODE_SEND_SMS_EXCEED_LIMIT:
                    //短信验证码发送次数受限
                    apiException = new ApiException(httpResult.getMessage(), ErrorMode.API_VISUALIZATION_MESSAGE.setErrorContent(httpResult.getMessage()).setErrorCode(ServiceErrorCode.CODE_SEND_SMS_EXCEED_LIMIT));
                    break;

                default:
                    apiException = new ApiException(httpResult.getMessage(), ErrorMode.HTTP_OTHER_ERROR);
            }

        }

        return apiException;
    }

    /**
     * 对请求错误进行统一处理
     *
     * @param e 错误
     * @return ApiException
     */
    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            ex = new ApiException(e.getMessage(), ErrorMode.HTTP_OTHER_ERROR.setErrorCode(((HttpException)e).code()));
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            //数据解析出错
            ex = new ApiException(e.getMessage(), ErrorMode.DATA_FORMAT_ERROR);
            return ex;
        } else if (e instanceof ClassCastException) {
            //类型转换错误
            ex = new ApiException(e.getMessage(), ErrorMode.TYPE_CAST_ERROR);
            return ex;
        } else if (e instanceof ConnectException) {
            //连接失败
            ex = new ApiException(ErrorMode.CONNECT_TIME_OUT);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            //证书验证失败
            ex = new ApiException(ErrorMode.SIGNATURE_FAILURE_SSL);
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            //连接超时
            ex = new ApiException(ErrorMode.CONNECT_TIME_OUT);
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(ErrorMode.CONNECT_TIME_OUT);
            return ex;
        } else if (e instanceof UnknownHostException) {
            //无法解析该域名
            ex = new ApiException(ErrorMode.UNKNOWN_HOST);
            return ex;
        } else {
            ex = new ApiException(e.getMessage(), ErrorMode.HTTP_OTHER_ERROR);
            return ex;
        }
    }
}

