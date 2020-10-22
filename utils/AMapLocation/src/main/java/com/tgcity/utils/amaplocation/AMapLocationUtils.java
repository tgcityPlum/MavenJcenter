package com.tgcity.utils.amaplocation;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tgcity.utils.DigitalUtils;

/**
 * @author TGCity
 * @date 2019/12/28
 * @description 高德获取定位信息工具类
 */
public class AMapLocationUtils {

    /**
     * 双重判断加锁单例
     * 保证异步处理安全
     */
    private volatile static AMapLocationUtils AMapLocationUtils = null;

    /**
     * 引入定位
     */
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private AMapLocation mLocation;

    /**
     * 获取单例
     *
     * @return EditShowRetrofitUtils
     */
    public static AMapLocationUtils getInstance() {

        if (AMapLocationUtils == null) {
            synchronized (AMapLocationUtils.class) {
                if (AMapLocationUtils == null) {
                    AMapLocationUtils = new AMapLocationUtils();
                }
            }
        }
        return AMapLocationUtils;
    }

    /**
     * 初始化定位控件
     *
     * @param context Context
     */
    public AMapLocationUtils initLocation(Context context) {
        locationClient = new AMapLocationClient(context);
        locationOption = getDefaultOption();
        locationClient.setLocationOption(locationOption);

        return this;
    }

    /**
     * 设置回调
     */
    public AMapLocationUtils setLocationListener(final MapLocationListener mapLocationListener) {
        //设置定位监听

        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == DigitalUtils.LEVEL_0) {
                    mLocation = aMapLocation;

                    if (mapLocationListener != null) {
                        mapLocationListener.onMapLocationSucceed(AMapLocationUtils, aMapLocation);
                    }
                } else {
                    if (mapLocationListener != null) {
                        mapLocationListener.onMapLocationFailed("location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });

        return this;
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(false);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        mOption.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(false);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(true);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(true);
        return mOption;
    }

    public AMapLocation getLocation() {
        return mLocation;
    }

    public AMapLocationClient getLocationClient() {
        return locationClient;
    }

    /**
     * 开始定位
     */
    public void onStart() {
        // 设置定位参数
        if (locationClient != null) {
            locationClient.setLocationOption(locationOption);
            locationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    public void onStop() {
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }

    public interface MapLocationListener {
        void onMapLocationSucceed(AMapLocationUtils AMapLocationUtils, AMapLocation location);

        void onMapLocationFailed(String message);
    }

    /**
     * 销毁定位
     */
    public void onDestroy() {
        if (null != locationClient) {
            // 如果AMapLocationClient是在当前Activity实例化的，
            // 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }

        if (AMapLocationUtils != null) {
            AMapLocationUtils = null;
        }
    }

    /**
     * 经纬度的转换
     * @param d double
     * @return string
     */
    public static String DtoDMS(Double d) {

        String[] array = d.toString().split("[.]");
        String degrees = array[0];//得到度

        Double m = Double.parseDouble("0." + array[1]) * 60;
        String[] array1 = m.toString().split("[.]");
        String minutes = array1[0];//得到分

        double s = Double.parseDouble("0." + array1[1]) * 60;
        String seconds = (int)s + "";//得到秒
        return degrees + "°" + minutes + "'" + seconds + "\"";
    }

}