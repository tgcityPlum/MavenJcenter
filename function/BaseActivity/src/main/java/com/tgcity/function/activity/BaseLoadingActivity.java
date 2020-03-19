package com.tgcity.function.activity;

import androidx.lifecycle.LifecycleOwner;

import com.tgcity.utils.LogUtils;
import com.tgcity.widget.BaseLoadingDialog;

/**
 * @author TGCity
 * 基础的activity
 * --处理加载指示器
 */
public abstract class BaseLoadingActivity extends BaseLifecycleActivity {
    /**
     * BaseLoadingDialog
     */
    private BaseLoadingDialog mBaseLoadingDialog;

    /**
     * showLruCache loading
     */
    public void showLoadingDialog() {
        if (mBaseLoadingDialog == null) {
            mBaseLoadingDialog = new BaseLoadingDialog(this);
        }

        if (!mBaseLoadingDialog.isShowing()) {
            mBaseLoadingDialog.show();
            LogUtils.i("BaseLoadingDialog is showLruCache");
        }
    }

    /**
     * dismiss loading
     */
    public void dismissLoadingDialog() {
        if (mBaseLoadingDialog != null) {
            mBaseLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        super.onDestroy(owner);
        //destroy loading
        dismissLoadingDialog();
        if (mBaseLoadingDialog != null) {
            mBaseLoadingDialog.clear();
        }
    }

}
