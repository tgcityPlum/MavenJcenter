package com.tgcity.function.permission;

/**
 * @author TGCity
 * @date 2020/1/17
 * @description 枚举文件,设置三种场景
 */
public enum PermissionEnum {
    /**
     * 授权
     */
    GRANTED,

    //拒绝
    DENIED,

    //始终拒绝
    ALL_DENIED,

    //未找到
    NOT_FOUND
}
