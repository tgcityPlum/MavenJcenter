package com.tgcity.function.permission;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.tgcity.utils.DigitalUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.tgcity.function.permission.PermissionEnum.ALL_DENIED;
import static com.tgcity.function.permission.PermissionEnum.DENIED;
import static com.tgcity.function.permission.PermissionEnum.GRANTED;
import static com.tgcity.function.permission.PermissionEnum.NOT_FOUND;

/**
 * @author TGCity
 * @date 2020/1/17
 * @description 权限的处理
 */
public class PermissionBuild {

    private final Set<String> mPermissions = new HashSet<>(1);

    private PermissionAction permissionAction;

    /**
     * 待处理的请求
     */
    private final Set<String> mPendingRequests = new HashSet<>(DigitalUtils.LEVEL_1);

    public PermissionBuild setCallback(PermissionAction permissionAction) {
        this.permissionAction = permissionAction;

        return this;
    }

    public void setPermissions(Activity activity, String[] permissions) {

        registerPermissions(permissions);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onPermissionWorkBeforeAndroidMarshmallow(activity, permissions);
        } else {
            List<String> permList = getPermissionsListToRequest(activity, permissions);
            if (!permList.isEmpty()) {
                String[] permsToRequest = permList.toArray(new String[permList.size()]);
                mPendingRequests.addAll(permList);
                ActivityCompat.requestPermissions(activity, permsToRequest, DigitalUtils.REQUEST_CODE_PERMISSION);
            }
        }
    }

    public void setManifestPermissions(Activity activity) {
        String[] perms = getManifestPermissions(activity);
        setPermissions(activity, perms);
    }

    public void onRequestPermissionsResult(Activity activity, String[] permissions, int[] results) {
        //处理用户的选择结果
        int result = DigitalUtils.LEVEL_0;
        for (int i = DigitalUtils.LEVEL_0; i < permissions.length; i++) {
            //选择了“始终允许”
            if (results[i] == PERMISSION_GRANTED) {
                result = DigitalUtils.LEVEL_0;
            } else {
                //用户选择了禁止不再询问
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    result = DigitalUtils.LEVEL_1;
                } else {//选择禁止
                    result = DigitalUtils.LEVEL_2;
                }
            }
        }

        for (String permission : permissions) {
            if (onResult(permission, result)) {
                break;
            }
        }

        for (String permission : permissions) {
            mPendingRequests.remove(permission);
        }
    }

    private boolean onResult(String permission, int result) {
        PermissionEnum permissionEnum;

        if (result == DigitalUtils.LEVEL_0) {
            permissionEnum = GRANTED;
        } else if (result == DigitalUtils.LEVEL_2) {
            permissionEnum = DENIED;
        } else {
            permissionEnum = ALL_DENIED;
        }
        return onResult(permission, permissionEnum);
    }

    private boolean onResult(String permission, PermissionEnum result) {
        mPermissions.remove(permission);

        if (result == GRANTED) {
            if (mPermissions.isEmpty()) {
                if (permissionAction != null) {
                    permissionAction.onGranted();
                }
                return true;
            }
        } else if (result == DENIED) {
            if (permissionAction != null) {
                permissionAction.onDenied(permission);
            }
            return true;
        } else if (result == ALL_DENIED) {
            if (permissionAction != null) {
                permissionAction.onAllDenied(permission);
            }
            return true;
        } else if (result == NOT_FOUND) {
            if (permissionAction != null) {
                permissionAction.onNotFound(permission);
            }
            return true;
        }
        return false;
    }

    private void registerPermissions(String[] permissions) {
        Collections.addAll(mPermissions, permissions);
    }

    /**
     * 安卓系统M前的版本权限申请
     */
    private void onPermissionWorkBeforeAndroidMarshmallow(Activity activity, String[] permissions) {
        for (String perm : permissions) {
            if (!mPermissions.contains(perm)) {
                onResult(perm, NOT_FOUND);
            } else if (ActivityCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                onResult(perm, DENIED);
            } else {
                onResult(perm, GRANTED);
            }
        }
    }

    /**
     * 获取权限请求列表
     */
    private List<String> getPermissionsListToRequest(Activity activity, String[] permissions) {
        List<String> permList = new ArrayList<>(permissions.length);
        for (String perm : permissions) {
            if (!mPermissions.contains(perm)) {
                onResult(perm, NOT_FOUND);
            } else if (ActivityCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                if (!mPendingRequests.contains(perm)) {
                    permList.add(perm);
                }
            } else {
                onResult(perm, GRANTED);
            }
        }
        return permList;
    }

    /**
     * 此方法检索在应用程序清单中声明的所有权限。
     * 它返回可以声明的非null权限数组。
     *
     * @param activity Activity
     * @return String[]
     */
    private String[] getManifestPermissions(Activity activity) {
        PackageInfo packageInfo = null;
        List<String> list = new ArrayList<>(DigitalUtils.LEVEL_1);
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException ignored) {

        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                Collections.addAll(list, permissions);
            }
        }
        return list.toArray(new String[0]);
    }

}
