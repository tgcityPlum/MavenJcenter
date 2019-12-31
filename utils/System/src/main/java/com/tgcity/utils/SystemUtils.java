package com.tgcity.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * @author TGCity
 * @date 2019/12/31
 * @description 系统工具类
 */
public class SystemUtils {

    /**
     * 判断App是否是debug环境
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isApkDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}
