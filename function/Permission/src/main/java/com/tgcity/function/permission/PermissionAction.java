package com.tgcity.function.permission;

/**
 * @author TGCity
 * @date 2020/1/17
 * @description 权限的处理
 */
public interface PermissionAction {

    /**
     * 权限申请同意
     */
    void onGranted();

    /**
     * 拒绝提供权限
     * @param permission String
     */
    void onDenied(String permission);

    /**
     * 拒绝权限后并不再提醒
     * @param permission String
     */
    void onAllDenied(String permission);

    /**
     * 未发现该权限
     * @param permission String
     */
    void onNotFound(String permission);

}
