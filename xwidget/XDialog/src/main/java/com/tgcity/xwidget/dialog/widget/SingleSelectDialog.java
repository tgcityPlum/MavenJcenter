package com.tgcity.xwidget.dialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tgcity.utils.DensityUtils;
import com.tgcity.xwidget.dialog.R;

import java.util.List;

/**
 * @author TGCit
 * description: 多条单选框单选框
 */
public class SingleSelectDialog extends Dialog {

    public SingleSelectDialog(Context context) {
        super(context);
    }

    public SingleSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowManager windowManager = getWindow() == null ? null : getWindow().getWindowManager();
        if (windowManager == null) {
            return;
        }
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.7f);
        getWindow().setAttributes(lp);
    }

    public static class Builder {
        private Context context;
        private String titleText;
        private int titleSize = -1;
        private int titleColor = -1;
        private Integer disablePosition;
        private View.OnClickListener onItemClickListener;
        private List<String> datas;

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                if (datas != null) {
                    return datas.size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                if (datas != null) {
                    return datas.get(position);
                }
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(context);
                }
                TextView textView = (TextView) convertView;
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dpToPx(45));
                textView.setLayoutParams(params);
                textView.setBackgroundResource(R.drawable.selector_ffffff_select_11000000);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(context.getResources().getColor(R.color.color_0F73EE));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                textView.setText(datas.get(position));

                if (disablePosition != null && disablePosition == position) {
                    textView.setTextColor(Color.parseColor("#E9EBF0"));
                }

                return convertView;
            }
        };

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public Builder setTitleSize(int titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setDisableItem(Integer disablePosition) {
            this.disablePosition = disablePosition;
            return this;
        }

        public Builder setOnItemClickListener(View.OnClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setDatas(List<String> datas) {
            this.datas = datas;
            return this;
        }


        public SingleSelectDialog create() {
            final SingleSelectDialog ssDialog = new SingleSelectDialog(context, R.style.xd_dialog_single);
            ViewGroup layout = (ViewGroup) View.inflate(context, R.layout.xd_dialog_single_select, null);
            TextView title = layout.findViewById(R.id.tv_title);
            View titleLine = layout.findViewById(R.id.v_title_line);
            ListView listView = layout.findViewById(R.id.list_view);
            LinearLayout.LayoutParams lp;
            if (datas.size() >= 5) {
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dpToPx(220f));
            } else {
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            listView.setLayoutParams(lp);
            listView.setAdapter(adapter);

            if (TextUtils.isEmpty(titleText)) {
                title.setVisibility(View.GONE);
                titleLine.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(titleText);
                titleLine.setVisibility(View.VISIBLE);
                if (titleSize != -1) {
                    title.setTextSize(titleSize);
                }
                if (titleColor != -1) {
                    title.setTextColor(titleColor);
                }
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (disablePosition != null && disablePosition == position) {
                        return;
                    }
                    ssDialog.dismiss();
                    if (onItemClickListener != null) {
                        view.setTag(position);
                        onItemClickListener.onClick(view);
                    }
                }
            });

            ssDialog.setCanceledOnTouchOutside(true);
            ssDialog.setContentView(layout);
            return ssDialog;
        }
    }

}
