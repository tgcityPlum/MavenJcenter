package com.tgcity.profession.network.retrofit;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tgcity.function.network.bean.HttpResult;
import com.tgcity.function.network.cache.ErrorMode;
import com.tgcity.function.network.retrofit.ApiException;
import com.tgcity.profession.network.base.DownloadFileApiService;
import com.tgcity.profession.network.base.NetworkConstant;
import com.tgcity.profession.network.bean.result.HttpCommonResult;
import com.tgcity.profession.network.cache.RxCache;
import com.tgcity.profession.network.cache.model.CacheMode;
import com.tgcity.profession.network.cache.model.CacheResult;
import com.tgcity.profession.network.callback.AbstractCallBackPrototypeProxy;
import com.tgcity.profession.network.callback.AbstractCallBackProxy;
import com.tgcity.profession.network.callback.AbstractSimpleCallBack;
import com.tgcity.profession.network.callback.AbstractSimpleCallBackProxy;
import com.tgcity.profession.network.callback.AbstractSimpleType;
import com.tgcity.profession.network.greendao.helper.HttpKeyOperationHelper;
import com.tgcity.profession.network.subsciber.CallBackSubsciber;
import com.tgcity.profession.network.utils.CommonUtils;
import com.tgcity.utils.FileUtils;
import com.tgcity.utils.LogUtils;
import com.tgcity.utils.StringUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author TGCity
 * @create 2018/11/16
 * @Describe 网络请求基类
 * HttpKeyOperationHelper设置规则 请看类注释
 */
public class HttpRetrofitUtils extends AbstractRetrofitUtils {

    public RxCache.Builder rxCacheBuilder;
    public Context context;


    /**
     * 初始化
     *
     * @param mContext        你懂的
     * @param getCacheVersion 是否需要获取版本信息
     * @param server          要初始化的服务器地址，详见AppConstant中的API地址初始化控制部分
     */
    @CallSuper
    public void init(Context mContext, boolean getCacheVersion, int... server) {
        this.context = mContext;
        rxCacheBuilder = new RxCache.Builder().init(mContext);
        HttpKeyOperationHelper.getInstance()
                .setApiAndRequsEffective(1000, 2)
                .setApiEffective(1000 * 10, 50)
                .setAllEffective(1000 * 60, 500);

    }

    /**
     * 用于多数据链式或并发请求
     */
    public <T> Observable toObservable(Builder builder, AbstractSimpleType<T> abstractSimpleType) {
        RxCache rxCache = rxCacheBuilder
                .apiName(builder.apiName)
                .requestData(builder.requestData)
                .cacheTime(builder.cacheTime)
                .build();
        Observable observable;

        AbstractCallBackPrototypeProxy proxy;
        if (NetworkConstant.API_SERVICE_TZY.equals(builder.extraRemark)) {
            /**
             * 因为#{@link com.eagersoft.youzy.youzy.constants.AppConstant.TZY_URL}地址下的接口返回参数是由isSuccess来判断，
             * 所以新增一个判断分支，与原来的互不影响，区分的时候只需要在#{@link Builder.extraRemark}参数填入#{@link com.eagersoft.youzy.youzy.constants.AppConstant.API_SERVICE_TZY}即可
             */
            if (builder.httpResultFormatting) {

                HttpResultFuncTwo httpResultFuncTwo;

                if (builder.responseDataCode == -1) {
                    httpResultFuncTwo = new HttpResultFuncTwo<T>();
                } else {
                    httpResultFuncTwo = new HttpResultFuncTwo<T>(builder.responseDataCode);
                }
                observable = builder.observable.map(httpResultFuncTwo);

                proxy = new AbstractSimpleCallBackProxy<HttpCommonResult<T>, T>(abstractSimpleType) {
                };
            } else {
                observable = builder.observable;
                proxy = new AbstractCallBackPrototypeProxy<T, T>(abstractSimpleType) {
                };
            }
        } else {
            if (builder.httpResultFormatting) {
                HttpResultFuncOne httpResultFuncOne;
                if (builder.responseDataCode == -1) {
                    httpResultFuncOne = new HttpResultFuncOne<T>();
                } else {
                    httpResultFuncOne = new HttpResultFuncOne<T>(builder.responseDataCode);
                }
                observable = builder.observable.map(httpResultFuncOne);

                proxy = new AbstractCallBackProxy<HttpResult<T>, T>(abstractSimpleType) {
                };
            } else {
                HttpResultFunThree httpResultFunThree;
                if (builder.responseDataCode == -1) {
                    httpResultFunThree = new HttpResultFunThree<String>();
                } else {
                    httpResultFunThree = new HttpResultFunThree<String>(builder.responseDataCode);
                }
                observable = builder.observable.map(httpResultFunThree);

                proxy = new AbstractCallBackPrototypeProxy<T, T>(abstractSimpleType) {
                };
            }
        }

        return observable.compose(rxCache.transformer(builder.cacheMode, proxy.getCallBack().getType()));

    }

    protected <T> void toObservable(Builder builder, AbstractSimpleCallBack<T> observer) {
        this.toObservable(null, builder, observer);
    }

    public <T> void toObservable(LifecycleTransformer lifecycleTransformer, Builder builder, AbstractSimpleCallBack<T> observer) {

        RxCache rxCache = rxCacheBuilder
                .apiName(builder.apiName)
                .requestData(builder.requestData)
                .cacheTime(builder.cacheTime)
                .build();

        Observable observable;
        AbstractCallBackPrototypeProxy proxy;
        if (NetworkConstant.API_SERVICE_TZY.equals(builder.extraRemark)) {
            /**
             * 因为#{@link com.eagersoft.youzy.youzy.constants.AppConstant.TZY_URL}地址下的接口返回参数是由isSuccess来判断，
             * 所以新增一个判断分支，与原来的互不影响，区分的时候只需要在#{@link Builder.extraRemark}参数填入#{@link com.eagersoft.youzy.youzy.constants.AppConstant.API_SERVICE_TZY}即可
             */
            if (builder.httpResultFormatting) {
                HttpResultFuncTwo httpResultFuncTwo;

                if (builder.responseDataCode == -1) {
                    httpResultFuncTwo = new HttpResultFuncTwo<T>();
                } else {
                    httpResultFuncTwo = new HttpResultFuncTwo<T>(builder.responseDataCode);
                }
                observable = builder.observable.map(httpResultFuncTwo);

                proxy = new AbstractSimpleCallBackProxy<HttpCommonResult<T>, T>(observer) {
                };
            } else {
                observable = builder.observable;
                proxy = new AbstractCallBackPrototypeProxy<T, T>(observer) {
                };
            }
        } else {
            if (builder.httpResultFormatting) {
                HttpResultFuncOne httpResultFuncOne;
                if (builder.responseDataCode == -1) {
                    httpResultFuncOne = new HttpResultFuncOne<T>();
                } else {
                    httpResultFuncOne = new HttpResultFuncOne<T>(builder.responseDataCode);
                }
                observable = builder.observable.map(httpResultFuncOne);

                proxy = new AbstractCallBackProxy<HttpResult<T>, T>(observer) {
                };
            } else {
                HttpResultFunThree httpResultFunThree;
                if (builder.responseDataCode == -1) {
                    httpResultFunThree = new HttpResultFunThree<String>();
                } else {
                    httpResultFunThree = new HttpResultFunThree<String>(builder.responseDataCode);
                }
                observable = builder.observable.map(httpResultFunThree);

                proxy = new AbstractCallBackProxy<HttpResult<T>, T>(observer) {
//                proxy = new AbstractCallBackPrototypeProxy<T, T>(observer) {
                };
            }
        }

        Observable observableCache = observable.compose(rxCache.transformer(builder.cacheMode, proxy.getCallBack().getType()));
        setSubscribe(lifecycleTransformer, observableCache, new CallBackSubsciber<T>(context, proxy.getCallBack()));

    }

    /**
     * 插入观察者
     *
     * @param lifecycleTransformer LifecycleTransformer
     * @param observable           Observable
     * @param observer             DisposableObserver
     * @param <T>                  T
     */
    public <T> void setSubscribe(LifecycleTransformer<CacheResult<T>> lifecycleTransformer, Observable<CacheResult<T>> observable, DisposableObserver<T> observer) {

        if (lifecycleTransformer != null) {
            observable
                    .compose(lifecycleTransformer)
                    .subscribeOn(Schedulers.io())
                    //子线程访问网络
                    .subscribeOn(Schedulers.newThread())
                    //回调到主线程
                    .observeOn(AndroidSchedulers.mainThread())
                    //回到主线后在map  多线程写人数据库有风险
                    .map(new HttpResultFuncCache<T>())
                    .subscribe(observer);
        } else {
            observable
                    .subscribeOn(Schedulers.io())
                    //子线程访问网络
                    .subscribeOn(Schedulers.newThread())
                    //回调到主线程
                    .observeOn(AndroidSchedulers.mainThread())
                    //回到主线后在map  多线程写人数据库有风险
                    .map(new HttpResultFuncCache<T>())
                    .subscribe(observer);
        }

    }

    /**
     * @param url                            下载地址路径
     * @param apiService                     请求Api
     * @param subscriberDownloadFileListener 回调
     */
    public void toObservableDownloadFile(String url, DownloadFileApiService apiService, SubscriberDownloadFileListener<String> subscriberDownloadFileListener) {
        toObservableDownloadFile(url, 1, "downloadFile", apiService, subscriberDownloadFileListener);
    }

    /**
     * @param url                            下载地址路径
     * @param type                           0  图片  1  视频
     * @param apiService                     请求Api
     * @param subscriberDownloadFileListener 回调
     */
    public void toObservableDownloadFile(String url, int type, DownloadFileApiService apiService, SubscriberDownloadFileListener<String> subscriberDownloadFileListener) {
        toObservableDownloadFile(url, type, "download", apiService, subscriberDownloadFileListener);
    }

    /**
     * @param url                            下载地址
     * @param type                           0  图片  1  视频
     * @param fileDirName                    文件名
     * @param apiService                     请求Api
     * @param subscriberDownloadFileListener 回调
     */
    public void toObservableDownloadFile(String url, int type, String fileDirName, DownloadFileApiService apiService, SubscriberDownloadFileListener<String> subscriberDownloadFileListener) {
        //校验外部元素是否为空
        if (checkDownloadFileElement(url, fileDirName, apiService, subscriberDownloadFileListener)) {
            return;
        }
        //校验文件路径是否为空
        String filePath = onDownloadFilePath(url, type, fileDirName);
        if (TextUtils.isEmpty(filePath)) {
            LogUtils.e("downloadFile: 存储路径为空");
            return;
        }
        //校验文件是否存在，不存在走下载流程；已存在直接返回文件路径
        File tempFile = new File(filePath);

        if (tempFile.exists()) {
            subscriberDownloadFileListener.onNext(filePath);
        } else {
            downloadFile(url, apiService, filePath, tempFile, subscriberDownloadFileListener);
        }
    }

    /**
     * @param url                            下载地址
     * @param apiService                     请求Api
     * @param subscriberDownloadFileListener 回调
     */
    private void downloadFile(@NonNull String url, @NonNull DownloadFileApiService apiService, @NonNull String filePath, @NonNull final File tempFile, @NonNull final SubscriberDownloadFileListener<String> subscriberDownloadFileListener) {
        Call<ResponseBody> mCall = apiService.downloadFile(url);
        final String finalFilePath = filePath;
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                //下载文件放在子线程
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //保存到本地
                        downloadFileToDisk(response, tempFile, finalFilePath, subscriberDownloadFileListener);
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                subscriberDownloadFileListener.onError(throwable);
            }
        });
    }

    /**
     * 校验字段
     *
     * @param url                            下载地址
     * @param fileDirName                    文件目录
     * @param apiService                     接口
     * @param subscriberDownloadFileListener 回调
     * @return 是否为空
     */
    private boolean checkDownloadFileElement(String url, String fileDirName, DownloadFileApiService apiService, SubscriberDownloadFileListener<String> subscriberDownloadFileListener) {
        if (url == null) {
            LogUtils.e("url is null in toObservableDownloadFile");
            return true;
        }
        if (fileDirName == null) {
            LogUtils.e("fileDirName is null in toObservableDownloadFile");
            return true;
        }
        if (apiService == null) {
            LogUtils.e("BaseApiService is null in toObservableDownloadFile");
            return true;
        }
        if (subscriberDownloadFileListener == null) {
            LogUtils.e("subscriberDownloadFileListener is null in toObservableDownloadFile");
            return true;
        }
        return false;
    }

    /**
     * 处理文件路径
     *
     * @param url         下载地址
     * @param type        类型
     * @param fileDirName 文件夹名称
     * @return 文件路径
     */
    private String onDownloadFilePath(String url, int type, String fileDirName) {
        String fileDir = Environment.getExternalStorageDirectory() + "/" + fileDirName;
        String filePath = "";
        //通过Url得到保存到本地的文件名
        if (FileUtils.createOrExistsDir(fileDir)) {
            String decodeUrl = URLDecoder.decode(url);
            //一定是找最后一个'/'出现的位置
            int i = decodeUrl.lastIndexOf('/');
            if (i != -1) {
                decodeUrl = decodeUrl.substring(i);
                if (type == 1) {
                    if (decodeUrl.contains(".mp4")) {
                        String[] temp = decodeUrl.split(".mp4");
                        decodeUrl = temp[0];
                    }
                    decodeUrl = decodeUrl + ".mp4";
                }
                filePath = fileDir + decodeUrl;
            }
        }
        return filePath;
    }

    /**
     * 保存文件到本地
     *
     * @param response         返回的数据response
     * @param file             文件
     * @param filePath         文件路径
     * @param downloadListener 回调
     */
    private void downloadFileToDisk(Response<ResponseBody> response, File file, String filePath, SubscriberDownloadFileListener<String> downloadListener) {
        downloadListener.onStart();
        long currentLength = 0;
        OutputStream os = null;

        if (response.body() == null) {
            ApiException apiException = new ApiException("资源错误！", ErrorMode.DATA_FORMAT_ERROR);
            downloadListener.onError(apiException);
            return;
        }
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();
        LogUtils.d("totalLength: " + totalLength);
        //下载装态  0 未开始  1 下载中  2 下载完成
        int loadStatus = 0;
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                if (loadStatus != 1) {
                    loadStatus = 1;
                }
                os.write(buff, 0, len);
                currentLength += len;

                int temp = (int) (100 * currentLength / totalLength);
                LogUtils.d("当前进度: " + currentLength + "====进度比例：" + temp);
                downloadListener.onProgress(temp);
                if (temp == 100) {
                    loadStatus = 2;
                }
            }
            if (loadStatus != 2) {
                loadStatus = 2;
            }
        } catch (FileNotFoundException e) {
            LogUtils.e(e.toString());
            downloadListener.onError(e);
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.e(e.toString());
            downloadListener.onError(e);
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    if (loadStatus != 2) {
                        loadStatus = 2;
                    }
                    os.close();
                    LogUtils.d("os资源关闭");
                } catch (IOException e) {
                    LogUtils.e(e.toString());
                    downloadListener.onError(e);
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    if (loadStatus != 2) {
                        loadStatus = 2;
                    }
                    is.close();
                    LogUtils.d("is资源关闭");
                } catch (IOException e) {
                    LogUtils.e(e.toString());
                    downloadListener.onError(e);
                    e.printStackTrace();
                }
            }
            if (loadStatus == 2) {
                LogUtils.d("下载完成: " + filePath);
                downloadListener.onNext(filePath);
            }
        }
    }

    public static final class Builder {

        private Observable observable;
        private String apiName;
        private String requestData;
        private long cacheTime;
        private CacheMode cacheMode;
        private boolean httpResultFormatting;
        private String extraRemark;
        private int responseDataCode;

        /**
         * 初始化
         * 默认值
         *
         * @param observable //回到主线后在map  多线程写人数据库有风险
         */
        public Builder(Observable observable) {
            this.observable = observable;
            this.apiName = "apiname";
            this.requestData = "requestData";
            this.cacheTime = -1;
            this.cacheMode = CacheMode.NO_CACHE;
            this.httpResultFormatting = true;
            this.extraRemark = "";
            this.responseDataCode = -1;
        }

        /**
         * @param apiName 接口名称
         * @return Builder
         */
        public Builder setApiName(String apiName) {
            this.apiName = apiName;
            return this;
        }


        /**
         * @param requestDatas 传入参数
         * @return Builder
         */
        public Builder setRequestData(Object... requestDatas) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object o : requestDatas) {
                stringBuilder.append(o == null ? "" : o.toString());
            }
            this.requestData = stringBuilder.toString();
            return this;
        }

        /**
         * @param cacheTime 缓存有效时间  -1 永久  默认
         * @return Builder
         */
        public Builder setCacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        /**
         * @param cacheMode 缓存策略
         * @return Builder
         */
        public Builder setCacheMode(CacheMode cacheMode) {
            this.cacheMode = cacheMode;
            return this;
        }

        /**
         * @param httpResultFormatting 是否需要格式化
         * @return Builder
         */
        public Builder setHttpResultFormatting(boolean httpResultFormatting) {
            this.httpResultFormatting = httpResultFormatting;
            return this;
        }

        /**
         * 设置额外备注
         *
         * @param extraRemark String
         * @return Builder
         */
        public Builder setExtraRemark(String extraRemark) {
            this.extraRemark = extraRemark;
            return this;
        }

        /**
         * 设置数据返回的标准code
         *
         * @param code int
         * @return Builder
         */
        public Builder setResponseDataCode(int code) {
            this.responseDataCode = code;
            return this;
        }
    }

    /**
     * 用来统一处理接口返回模型的code码字段status,并将HttpResult的Data部分剥离出来返回给subscriber
     * 针对HttpResult<T>模型进行处理
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class HttpResultFuncOne<T> implements Function<HttpResult<T>, T> {

        private int responseDataCode = NetworkConstant.SUCCEED_CODE_200;

        public HttpResultFuncOne() {

        }

        public HttpResultFuncOne(int code) {
            this.responseDataCode = code;
        }

        @Override
        public T apply(HttpResult<T> httpResult) {
            //项目抛错根据getCode值进行处理
            if (httpResult.getCode() != responseDataCode) {
                throw ApiException.handleException(httpResult);
            } else {
                if (httpResult.getResults() instanceof List) {
                    if (CommonUtils.isEmptyResult((List) httpResult.getResults())) {
//                        httpResult.setCode(ErrorMode.SERVER_NULL.getErrorCode());
                        throw ApiException.handleException(httpResult);
                    }
                } else {
                    if (httpResult.getResults() == null) {
//                        httpResult.setCode(ErrorMode.SERVER_NULL.getErrorCode());
                        throw ApiException.handleException(httpResult);
                    }
                }
            }
            return httpResult.getResults();
        }
    }

    /**
     * 用来统一处理接口返回模型的code码字段code,并将HttpCommonResult的Data部分剥离出来返回给subscriber
     * 针对HttpCommonResult<T>模型进行处理
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class HttpResultFuncTwo<T> implements Function<HttpCommonResult<T>, T> {

        private int responseDataCode = NetworkConstant.SUCCEED_CODE_0;

        public HttpResultFuncTwo() {

        }

        public HttpResultFuncTwo(int code) {
            this.responseDataCode = code;
        }

        @Override
        public T apply(HttpCommonResult<T> httpResult) {
            //待校验的code码
            int tempCode = httpResult.getCode();
            //待使用的信息
            String tempContent;
            if (!StringUtils.isEmpty(httpResult.getMessage())) {
                tempContent = httpResult.getMessage();
            } else if (!StringUtils.isEmpty(httpResult.getMsg())) {
                tempContent = httpResult.getMsg();
            } else {
                tempContent = "接口未提供默认信息";
            }

            if (tempCode != responseDataCode) {
                throw new ApiException(tempContent, ErrorMode.API_VISUALIZATION_MESSAGE.setErrorCode(tempCode));
            }

            if (httpResult.getResult() instanceof List) {
                if (CommonUtils.isEmptyResult((List) httpResult.getResult())) {
                    throw new ApiException(ErrorMode.SERVER_NULL.getErrorTitle(), ErrorMode.SERVER_NULL.setErrorContent(tempContent));
                }
            } else {
                if (httpResult.getResult() == null) {
                    throw new ApiException(ErrorMode.SERVER_NULL.getErrorTitle(), ErrorMode.SERVER_NULL.setErrorContent(tempContent));
                }
            }
            return httpResult.getResult();
        }
    }

    /**
     * 用来统一处理接口返回模型的code码字段status,并将HttpResult的Data部分剥离出来返回给subscriber
     * 针对HttpResult<T>模型进行处理
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class HttpResultFunThree<T> implements Function<HttpResult<String>, String> {

        private int responseDataCode = NetworkConstant.SUCCEED_CODE_200;

        public HttpResultFunThree() {

        }

        public HttpResultFunThree(int code) {
            this.responseDataCode = code;
        }

        @Override
        public String apply(HttpResult<String> httpResult) {
            //项目抛错根据getCode值进行处理
            if (httpResult.getCode() != responseDataCode) {
                throw ApiException.handleException(httpResult);
            } else {
                if (httpResult.getResults() == null) {
                    return "data is null";
                }
            }
            return httpResult.getResults();
        }

    }

    /**
     * 缓存的拦截处理
     */
    public static class HttpResultFuncCache<T> implements Function<CacheResult<T>, T> {
        @Override
        public T apply(CacheResult<T> httpResult) throws Exception {
            if (!httpResult.isFromCache) {
                //如果不是读取的缓存添加请求记录
                HttpKeyOperationHelper.getInstance().addKey(httpResult.apiName, httpResult.requestData);
            }
            return httpResult.data;
        }
    }

}
