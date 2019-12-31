package com.tgcity.profession.network.callback;

import retrofit2.Callback;

/**
 * @author TGCity
 * @date 2019/9/23
 * @describe retrofit原始的callback  用来兼容一些非通用型接口返回数据
 */
public interface RetrofitCommonCallback<T> extends Callback<T> {

}
