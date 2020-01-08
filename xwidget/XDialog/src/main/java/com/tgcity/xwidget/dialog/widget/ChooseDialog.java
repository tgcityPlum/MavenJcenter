package com.tgcity.xwidget.dialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tgcity.utils.StringUtils;
import com.tgcity.xwidget.dialog.R;
import com.tgcity.xwidget.dialog.interfaces.OnDialogConfirmCancelCallBack;

/**
 * @author TGCity
 * @describe 选择对话框
 *
 * <pre><code>
 * if (chooseDialog == null) {
 *             chooseDialog =new ChooseDialog.Builder(getContext())
 *                     .setTitle("xx")
 *                     .setMessage("xx，确定退出吗？")
 *                     .setNegativeButton("取消")
 *                     .setPositiveButton("退出")
 *                     .setCallBack(new OnDialogConfirmCancelCallBack() {
 *                         @Override
 *                         public void onConfirm() {
 *                             //todo
 *                         }
 *
 *                         @Override
 *                         public void onCancel() {
 *                             //todo
 *                         }
 *                     }).create();
 *         }
 *
 *         chooseDialog.showLruCache();
 * <pre><code>
 */
public class ChooseDialog extends Dialog {

    public ChooseDialog(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private TextView tvContext;
        private TextView tvRightChoose;
        private OnDialogConfirmCancelCallBack callBack;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 更新内容的描述文案
         * @param message String
         * @return Builder
         */
        public Builder updateMessage(String message) {
            this.message = message;
            if (tvContext != null) {
                tvContext.setText(message);
            }
            return this;
        }

        /**
         * 更新“确认”按钮文案
         * @param positiveButtonText String
         * @return Builder
         */
        public Builder updatePositiveButton(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            if (tvRightChoose != null) {
                tvRightChoose.setText(positiveButtonText);
            }
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public Builder setCallBack(OnDialogConfirmCancelCallBack callBack) {
            this.callBack = callBack;

            return this;
        }

        public ChooseDialog create() {
            // instantiate the dialog with the custom Theme
            final ChooseDialog dialog = new ChooseDialog(context);
            View layout = LayoutInflater.from(context).inflate(R.layout.xd_dialog_choose, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView tvTitle = layout.findViewById(R.id.dialog_tv_title);
            tvContext = layout.findViewById(R.id.dialog_tv_context);
            tvRightChoose = layout.findViewById(R.id.dialog_right_choose);
            TextView tvLeftChoose = layout.findViewById(R.id.dialoh_left_choose);

            if (StringUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
            }
            tvContext.setText(message);
            tvRightChoose.setText(positiveButtonText);
            tvLeftChoose.setText(negativeButtonText);

            tvLeftChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (callBack != null) {
                        callBack.onCancel();
                    }
                }
            });

            tvRightChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (callBack != null) {
                        callBack.onConfirm();
                    }
                }
            });

            dialog.setContentView(layout);
            return dialog;
        }
    }

}
