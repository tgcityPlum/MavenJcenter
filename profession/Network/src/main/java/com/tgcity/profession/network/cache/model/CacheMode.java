/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tgcity.profession.network.cache.model;

/**
 * 网络请求策略
 *
 * @author TGCity
 */
public enum CacheMode {
    /**
     * 不使用缓存,该模式下,cacheKey,cacheMaxAge 参数均无效
     **/
    NO_CACHE("NoStrategy"),
    /**
     * 先请求网络，当请求网络失败后再加载缓存
     */
    FIRST_REMOTE("FirstRemoteStrategy"),

    /**
     * 先加载缓存，当缓存没有再去请求网络
     */
    FIRST_CACHE("FirstCacheStrategy"),

    /**
     * 先显示缓存，再请求网络，不进行数据的查重处理
     */
    CACHE_AND_REMOTE("CacheAndRemoteStrategy"),

    /**
     * 先显示缓存，再请求网络，处理数据的重复逻辑
     * 使用过程中需要重写返回实体类的toString方法
     */
    CACHE_AND_REMOTE_DISTINCT("CacheAndRemoteDistinctStrategy");

    private final String className;

    CacheMode(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
