package com.tgcity.function.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.tgcity.utils.LogUtils;
import com.tgcity.widget.BaseLoadingDialog;

/**
 * @author TGCity
 * 基础的activity
 * --处理加载指示器
 */
public class BaseLoadingActivity extends AppCompatActivity {
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
    protected void onDestroy() {
        super.onDestroy();
        //destroy loading
        dismissLoadingDialog();
        if (mBaseLoadingDialog != null) {
            mBaseLoadingDialog.clear();
        }
    }
}
