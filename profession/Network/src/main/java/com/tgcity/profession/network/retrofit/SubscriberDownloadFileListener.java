package com.tgcity.profession.network.retrofit;

/**
 * 下载文件的回调接口
 *
 * @author tgcity
 */

public interface SubscriberDownloadFileListener<T> extends SubscriberOnNextListener<T> {

    /**
     * 开始方法
     */
    void onStart();

    /**
     * 进度条
     * @param currentLength 进度 0~100
     */
    void onProgress(int currentLength);

}
