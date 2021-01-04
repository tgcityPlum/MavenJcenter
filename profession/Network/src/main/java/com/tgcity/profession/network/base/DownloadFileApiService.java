package com.tgcity.profession.network.base;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 文件下载请求基本接口类
 *
 * @author tgcity
 */

public interface DownloadFileApiService {

    /**
     * 下载文件（图片或者视频）
     * <p>
     * 使用Streaming 大文件时要加不然会OOM
     *
     * @param fileUrl 下载链接地址
     * @return Call<ResponseBody>
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
