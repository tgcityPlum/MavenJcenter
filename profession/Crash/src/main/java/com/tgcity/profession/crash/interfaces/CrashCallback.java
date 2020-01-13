package com.tgcity.profession.crash.interfaces;

/**
 * @author tgcity
 */
public interface CrashCallback {
    /**
     * crash树形文案回调方法
     *
     * @param stackTrace 文案
     */
    void stackTrace(String stackTrace);

    /**
     * crash详细信息回调方法，用户可通过此方法提交后台或自行处理
     *
     * @param throwExceptionType crash类型
     * @param cause              crash原因
     * @param throwClassName     crash类名
     * @param throwMethodName    crash方法名
     * @param throwLineNumber    crash出错行数
     */
    void exception(String throwExceptionType, String cause, String throwClassName, String throwMethodName, int throwLineNumber);

    /**
     * crash处理的回调
     *
     * @param throwable Throwable
     */
    void throwable(Throwable throwable);
}