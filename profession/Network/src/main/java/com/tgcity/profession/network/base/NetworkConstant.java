package com.tgcity.profession.network.base;

/**
 * @author TGCity
 * 静态常量类
 */

public class NetworkConstant {
    /*********************************************服务器相关*********************************************/
    /**
     * 开关
     */
    public class Switch {
        //是否为调试模式
        private final static boolean IS_DEBUG = true;
        //是否解析服务器返回的json
        public final static boolean IS_JSON_FORMAT = IS_DEBUG && true;
        //是否打印网络请求日志
        public final static boolean IS_PRINT_NETWORK_LOG = IS_DEBUG && true;
    }

    /**
     * 服务器域名
     */
    public class Service {
        //开发 测试 运营域名
        public final static String SERVICE_ALPHA = "http://192.168.15.66:8080";
        public final static String SERVICE_BETA = "http://113.143.100.157:18095";
        public final static String SERVICE_RELEASE = "https://uzer.me";

        public final static String SERVICE_DEFAULT = SERVICE_BETA;
    }

    /**
     * 服务器初始化标识
     */
    public class ServiceFlag {
        //服务器API分支 alpha、beta、Release

        //开发版本API地址
        public static final int SERVER_ALPHA = 0;
        //测试版本API地址
        public static final int SERVER_BETA = 1;
        //运营版本API地址
        public static final int SERVER_RELEASE = 2;

        public static final int SERVER_DEFAULT = SERVER_BETA;

    }

    public final static String SYSTEM_TAG = "系统逻辑跟踪标签";
    //本字段出现的原因是在于区分TZY_URL这个地址
    public final static String API_SERVICE_TZY = "service_tzy";

    /**
     * 缓存有效期 单位秒
     */
    public static long cache_a_second = 1;
    public static long cache_a_minute = 60;
    public static long cache_half_a_hour = 60 * 60 / 2;
    public static long cache_a_hour = 60 * 60;
    public static long cache_a_day = 24 * 60 * 60;
    public static long cache_a_week = 24 * 60 * 60 * 7;
    public static long cache_a_month = 30 * 24 * 60 * 60;
    public static long cache_three_month = 30 * 24 * 60 * 60 * 3;
    public static long cache_six_month = 30 * 24 * 60 * 60 * 6;
    public static long cache_a_year = 30 * 24 * 60 * 60 * 12;

    /*********************************************程序内部通讯消息相关*********************************************/
    //跳转xxx
    public static final int ACTION_HOME_ZYDW = -0x0000A;
    /*********************************************SharedPreferences配置相关*********************************************/
    //缓存配置读取相关

    /**
     * Disk Cache Dir name
     */
    public static String UNIQUE_NAME = "uzerme-cache";

    /**
     * green dao name
     */
    public static String GREEN_DAO_NAME = "uzerme-db";

    /**
     * 用户信息
     */
    public static int CACHE_USER = 0;

    /**
     * 其他
     */
    public static int CACHE_OTHER = 1;

    /**
     * 巡河
     */
    public static int CACHE_PATROL = 2;


    /**
     * 异常的标准code码
     */
    public static int SUCCEED_CODE = 200;
    public static String LOGIN_COOKIE = "LOGIN_COOKIE";

}
