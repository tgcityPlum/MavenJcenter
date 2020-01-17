package com.tgcity.function.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.tgcity.utils.DigitalUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author TGCity
 * @date 2020/1/17
 * @description
 */
public class PermissionsManager {
    /**
     * 权限集合
     */
    private final Set<String> mPermissions = new HashSet<>(DigitalUtils.LEVEL_1);

    private static volatile PermissionsManager mInstance = null;

    private PermissionBuild permissionBuild;

    public static PermissionsManager getInstance() {
        if (mInstance == null) {
            synchronized (PermissionsManager.class) {
                if (mInstance == null) {
                    mInstance = new PermissionsManager();
                }
            }
        }
        return mInstance;
    }

    private PermissionsManager() {
        initPermissionsMap();
    }

    /**
     * 此方法使用反射来读取Manifest类中的所有权限。
     * 这是必要的，因为旧版Android不存在某些权限，
     * 由于它们不存在，因此当您检查您是否具有权限时，它们将被拒绝，
     * 这是有问题的，因为通常在不需要先前许可的地方添加了新许可。
     * 我们初始化一组可用的权限，并在检查是否有权限时检查该组权限，因为我们想知道何时拒绝权限，因为该权限尚不存在。
     */
    private void initPermissionsMap() {
        Field[] fields = Manifest.permission.class.getFields();
        for (Field field : fields) {
            String name;
            try {
                name = (String) field.get("");
                mPermissions.add(name);
            } catch (IllegalAccessException ignored) {

            }
        }
    }

    /**
     * 是否具有特定权限
     *
     * @param context    Context
     * @param permission String
     * @return boolean
     */
    public boolean hasPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED || !mPermissions.contains(permission);
    }

    /**
     * 是否具有特定权限
     */
    public boolean hasAllPermissions(Context context, String[] permissions) {
        boolean hasAllPermissions = true;
        for (String perm : permissions) {
            hasAllPermissions &= hasPermission(context, perm);
        }
        return hasAllPermissions;
    }

    /**
     * 获取PermissionBuild
     */
    public PermissionBuild getBuild() {
        if (permissionBuild == null) {
            permissionBuild = new PermissionBuild();
        }
        return permissionBuild;
    }

}
