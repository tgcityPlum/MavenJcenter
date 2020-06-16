package com.tgcity.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.List;

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
        if (context == null) {
            throw new RuntimeException("context is null");
        }

        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 判断App是否处于后台运行
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isAppInBackground(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return true;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    /**
     * 获取应用名称
     *
     * @param context Context
     * @return String
     */
    public static String getAppName(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            CharSequence charSequence = packageManager.getApplicationLabel(applicationInfo);
            return (String) charSequence;
        } else {
            return "应用名称获取失败";
        }
    }

    /**
     * 获取APP版本信息
     */
    public static String getAppVersionName(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        String version = null;
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取APP版本code
     */
    public static int getAppVersionCode(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        int version = 0;
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 是否是主线程
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isMainProcess(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        try {
            ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningAppProcessInfo> processInfo;
            if (am != null) {
                processInfo = am.getRunningAppProcesses();
                String mainProcessName = context.getPackageName();
                int myPid = android.os.Process.myPid();
                for (ActivityManager.RunningAppProcessInfo info : processInfo) {
                    if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a-");
        try {
            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint({"MissingPermission", "HardwareIds"})
            String imei = tm.getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                deviceId.append("imei-");
                deviceId.append(imei);
                return deviceId.toString();
            }
            //序列号（sn）
            @SuppressLint({"MissingPermission", "HardwareIds"})
            String sn = tm.getSimSerialNumber();
            if (!TextUtils.isEmpty(sn)) {
                deviceId.append("sn-");
                deviceId.append(sn);
                return deviceId.toString();
            }
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!TextUtils.isEmpty(uuid)) {
                deviceId.append("id-");
                deviceId.append(uuid);
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id-");
            deviceId.append(getUUID(context));
        }
        return deviceId.toString();
    }

    /**
     * 得到全局唯一UUID
     */
    private static String getUUID(Context context) {
        String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        return androidId;
    }

    /**
     * 获取设备厂商
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备名称
     */
    public static String getDeviceName() {
        return Build.MODEL;
    }

    /**
     * 获取卡1手机号
     */
    public static String getCard1Number(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"MissingPermission", "HardwareIds"})
        String phoneN = tm.getLine1Number();
        return phoneN;
    }

    /**
     * 获取设备android api 版本号
     */
    public static int getDeviceApiLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备android api 名称
     */
    public static String getDeviceApiName() {
        return android.os.Build.VERSION.RELEASE;
    }

}
