package com.tgcity.profession.network.base;

import com.tgcity.function.application.BaseApplication;
import com.tgcity.profession.network.greendao.helper.GreenDaoHelper;
import com.tgcity.utils.SharedPreferencesUtils;

/**
 * @author TGCity
 */
public class NetworkApplication extends BaseApplication {
    /**
     * 静态单例
     */
    private static NetworkApplication instances;

    private static SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instances == null) {
            instances = this;
        }
        //数据库初始化
        GreenDaoHelper.getInstance();
    }

    public static NetworkApplication getInstances() {
        if (instances == null) {
            instances = new NetworkApplication();
        }
        return instances;
    }

    /**
     * 获取缓存的utils
     * @return SharedPreferencesUtils
     */
    public static SharedPreferencesUtils getSharedPreferencesUtils(){
        if (sharedPreferencesUtils == null){
            sharedPreferencesUtils = new SharedPreferencesUtils(getInstances(), "network_cfg");
        }
        return sharedPreferencesUtils;
    }

}
