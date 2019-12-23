package com.tgcity.function.network.cache;

/**
 * @author TGCity
 * @date 2019/10/16
 * @description 错误码
 */
public class ServiceErrorCode {

    /**
     * 服务器异常
     */
    public static final String MESSAGE_SERVICE_ERROR = "服务器异常,请稍后重试";

    /**
     * 发送手机验证码受限的错误码
     */
    public static final int CODE_SEND_SMS_EXCEED_LIMIT = 50185;
    public static final String MESSAGE_SEND_SMS_EXCEED_LIMIT = "该手机验证码发送次数达到上限";

    /**
     * 登录的账号或密码错误
     */
    public static final String MESSAGE_INVALID_USER_NAME_OR_PASSWORD = "账号或密码错误";

    /**
     * 微信注册
     */
    public static final int CODE_WE_CHAT_NOT_REGISTER = 50600;

    /**
     * 请求成功
     */
    public static final int CODE_SERVICE_SUCCEED = 200;

}
