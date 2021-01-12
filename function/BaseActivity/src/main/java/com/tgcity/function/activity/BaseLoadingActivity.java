package com.tgcity.function.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.tgcity.utils.LogUtils;
import com.tgcity.widget.BaseLoadingDialog;

/**
 * @author TGCity
 * 基础的activity
 * --处理加载指示器
 */
public abstract class BaseLoadingActivity extends AppCompatActivity {

    /**
     * 加载方式
     * 0 gif
     * 1 加载图片
     */
    private int loadStyle = 0;

    /**
     * BaseLoadingDialog
     */
    private BaseLoadingDialog mBaseLoadingDialog;

    /**
     * showLruCache loading
     */
    public void showLoadingDialog() {

        if (loadStyle == 0) {
            if (mBaseLoadingDialog == null) {
                mBaseLoadingDialog = new BaseLoadingDialog(this);
            }

            if (!mBaseLoadingDialog.isShowing()) {
                mBaseLoadingDialog.show();
                LogUtils.i("BaseLoadingDialog is showLruCache");
            }
        }

    }

    /**
     * dismiss loading
     */
    public void dismissLoadingDialog() {
        if (loadStyle == 0) {
            if (mBaseLoadingDialog != null) {
                mBaseLoadingDialog.dismiss();
            }
        }

    }

    public void setLoadStyle(int loadStyle) {
        this.loadStyle = loadStyle;
    }

    /**
     * 输出当前界面调用方法的日志
     */
    protected void logActivity(String message) {
        LogUtils.d(message);
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
