package com.tgcity.profession.network.cache.core;




import com.tgcity.profession.network.utils.CallBackUtils;

import java.lang.reflect.Type;

import okio.ByteString;

/**
 * @author TGCity
 * 缓存核心管理类
 * 1.采用LruDiskCache
 */
public class CacheCore {

    private LruDiskCache disk;

    public CacheCore(LruDiskCache disk) {
        this.disk = CallBackUtils.checkNotNull(disk, "disk==null");
    }

    /**
     * 读取
     */
    public synchronized <T> T load(Type type, String key, long time) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        if (disk != null) {
            T result = disk.load(type, cacheKey, time);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * 保存
     */
    public synchronized <T> boolean save(String key, T value) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        return disk.save(cacheKey, value);
    }

    /**
     * 是否包含
     *
     * @param key String
     * @return boolean
     */
    public synchronized boolean containsKey(String key) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        if (disk != null) {
            return disk.containsKey(cacheKey);
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param key String
     */
    public synchronized boolean remove(String key) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        if (disk != null) {
            return disk.remove(cacheKey);
        }
        return true;
    }

    /**
     * 清空缓存
     */
    public synchronized boolean clear() {
        if (disk != null) {
            return disk.clear();
        }
        return false;
    }

}
