package com.tgcity.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tgcity.widget.loaddialog.R;

/**
 * @author TGCity
 * @description 加载指示器组件
 *
 * <pre>
 *     <code>
 *         1.class file
 *           private BaseLoadingDialog mBaseLoadingDialog;
 *
 *          if (mBaseLoadingDialog == null) {
 *             mBaseLoadingDialog = new BaseLoadingDialog(this);
 *         }
 *
 *          if (!mBaseLoadingDialog.isShowing()) {
 *             mBaseLoadingDialog.show();
 *         }
 *
 *          if (mBaseLoadingDialog != null) {
 *             mBaseLoadingDialog.dismiss();
 *         }
 *
 *         if (mBaseLoadingDialog != null) {
 *             mBaseLoadingDialog.clear();
 *         }
 *     </code>
 * </pre>
 */
public class BaseLoadingDialog extends Dialog {

    /**
     * loading image
     */
    private ImageView loadingProgress;
    /**
     * loading context
     */
    private TextView loadingText;
    /**
     * context
     */
    private Context mContext;

    public BaseLoadingDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseLoadingDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public BaseLoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ld_dialog_base_loading);
        loadingProgress = findViewById(R.id.loading_pic);
        loadingText = findViewById(R.id.loading_text);

        showGIFLoading();

    }

    /**
     * 显示GIF的进度条
     */
    private void showGIFLoading() {
        try {
            Glide.with(mContext)
                    .load(R.drawable.ld_loading_dh)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .thumbnail(1.0f)
                    .into(loadingProgress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示多张图片的进度条
     */
    private void showPicturesLoading() {
//        loadingProgress.setImageResource(R.drawable.loading_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingProgress.getDrawable();
        animationDrawable.start();
    }

    /**
     * showLruCache dialog
     */
    @Override
    public void show() {
        if (!isShowing()) {
            try {
                super.show();
                setCanceledOnTouchOutside(false);
            } catch (Exception e) {
                dismiss();
            }
        }
    }

    /**
     * upData context
     *
     * @param info context
     */
    public void updateText(String info) {
        if (loadingText != null) {
            loadingText.setText(info);
        }
    }

    /**
     * clear view
     */
    public void clear() {
        mContext = null;
        loadingText = null;
        loadingProgress = null;
    }

}
