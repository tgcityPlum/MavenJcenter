package com.tgcity.profession.imageselector;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.tgcity.profession.multiimageselector.R;

import java.util.ArrayList;

/**
 * 图片选择器
 *
 * @author nereo
 * @date 16/3/17
 */
public class MultiImageSelector {

    private boolean mShowCamera = true;
    private int mMaxCount = 9;
    private int mMode = MultiImageSelectorActivity.MODE_MULTI;
    /**
     * 0 显示照相机  1 显示图片  2 显示照相机和图片
     */
    private int openStyle = 2;
    private ArrayList<String> mOriginData;
    private static MultiImageSelector sSelector;

    /**
     * @param context Context
     * @deprecated Use {@link #MultiImageSelector()} instead
     */
    @Deprecated
    private MultiImageSelector(Context context) {

    }

    private MultiImageSelector() {
    }

    /**
     *
     * @param context Context
     * @return MultiImageSelector
     * @deprecated Use {@link #create()} instead
     */
    @Deprecated
    public static MultiImageSelector create(Context context) {
        if (sSelector == null) {
            sSelector = new MultiImageSelector(context);
        }
        return sSelector;
    }

    public static MultiImageSelector create() {
        if (sSelector == null) {
            sSelector = new MultiImageSelector();
        }
        return sSelector;
    }

    public MultiImageSelector setMaxCount(int count) {
        mMaxCount = count;
        return sSelector;
    }


    public MultiImageSelector showCamera(boolean show) {
        mShowCamera = show;
        return sSelector;
    }

    /**
     * 界面展示的方式
     *
     * @param style 0 显示照相机  1 显示图片  2 显示照相机和图片
     * @return MultiImageSelector
     */
    public MultiImageSelector showStyle(int style) {
        openStyle = style;
        if (style == 1) {
            mShowCamera = false;
        }
        return sSelector;
    }

    public MultiImageSelector count(int count) {
        mMaxCount = count;
        return sSelector;
    }

    /**
     * 单张图片
     */
    public MultiImageSelector single() {
        mMode = MultiImageSelectorActivity.MODE_SINGLE;
        return sSelector;
    }

    /**
     * 多张图片
     */
    public MultiImageSelector multi() {
        mMode = MultiImageSelectorActivity.MODE_MULTI;
        return sSelector;
    }

    public MultiImageSelector origin(ArrayList<String> images) {
        mOriginData = images;
        return sSelector;
    }

    public void start(Activity activity, int requestCode) {
        final Context context = activity;
        if (hasPermission(context)) {
            activity.startActivityForResult(createIntent(context), requestCode);
        } else {
            Toast.makeText(context, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    public void start(Fragment fragment, int requestCode) {
        final Context context = fragment.getContext();
        if (hasPermission(context)) {
            fragment.startActivityForResult(createIntent(context), requestCode);
        } else {
            Toast.makeText(context, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Permission was added in API Level 16
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    /**
     * 跳转界面
     */
    private Intent createIntent(Context context) {
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, mShowCamera);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, mMaxCount);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_OPEN_STYLE, openStyle);
        if (mOriginData != null) {
            intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mOriginData);
        }
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, mMode);
        return intent;
    }
}
