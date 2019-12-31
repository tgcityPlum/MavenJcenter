package com.tgcity.profession.network.base;

import com.tgcity.function.application.BaseApplication;
import com.tgcity.profession.network.greendao.helper.GreenDaoHelper;

/**
 * @author TGCity
 */
public class NetworkApplication extends BaseApplication {
    /**
     * 静态单例
     */
    private static NetworkApplication instances;

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

}
