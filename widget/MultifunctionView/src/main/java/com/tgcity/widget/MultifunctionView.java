package com.tgcity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tgcity.utils.DensityUtils;
import com.tgcity.utils.DigitalUtils;
import com.tgcity.widget.multifunctionview.R;
import com.tgcity.widget.spinner.MaterialSpinner;

import java.util.List;

/**
 * @author TGCity
 * @descrip 多功能view样式
 */
public class MultifunctionView extends LinearLayout {

    private View rootView;

    private Context mContext;

    /**
     * 0  左侧TextView  中间是EditText
     * 1  左侧TextView  中间是EditText   右侧是图片
     * 2  左侧TextView  中间是TextView
     * 3  左侧TextView  中间是TextView   右侧是图片
     * 4  左侧TextView  中间是MaterialSpinner
     * 5  左侧是图片     中间是TextView   右侧是图片
     * 6  左侧TextView                   右侧是TextView
     * 7  左侧是图片    中间上部是TextView  中间下部是TextView
     */
    private int layoutType = DigitalUtils.LEVEL_0;
    private String leftText;
    private String centerHintText;
    private String centerText;
    private String centerBottomText;
    private String rightText;
    private int leftIcon;
    private int leftColor = DigitalUtils.LEVEL_N_1;
    private int leftSize = DigitalUtils.LEVEL_N_1;
    private int layoutHeight = DigitalUtils.LEVEL_N_1;
    private int rightIcon;
    private boolean lineVisibility = true;
    private boolean lineRightVisibility = true;
    private boolean requiredOptionsVisibility = false;

    private ConstraintLayout clView;
    private TextView tvLeft;
    private TextView tvCenter;
    private TextView tvCenterBottom;
    private EditText etCenter;
    private MaterialSpinner msItemType;
    private TextView tvRight;
    private ImageView ivLeft;
    private ImageView ivRight;
    private View vLineBottom;
    private View vLineRight;


    public MultifunctionView(Context context) {
        super(context);
        init(context, null);
    }

    public MultifunctionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultifunctionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.mv_MultifunctionView);

        layoutType = typedArray.getInt(R.styleable.mv_MultifunctionView_mv_layout_type, layoutType);
        layoutHeight = typedArray.getLayoutDimension(R.styleable.mv_MultifunctionView_mv_layout_height, layoutHeight);
        leftText = typedArray.getString(R.styleable.mv_MultifunctionView_mv_left_text);
        leftIcon = typedArray.getResourceId(R.styleable.mv_MultifunctionView_mv_left_icon, DigitalUtils.LEVEL_0);
        leftColor = typedArray.getColor(R.styleable.mv_MultifunctionView_mv_left_color, leftColor);
        leftSize = typedArray.getDimensionPixelSize(R.styleable.mv_MultifunctionView_mv_left_size, leftSize);
        centerHintText = typedArray.getString(R.styleable.mv_MultifunctionView_mv_center_hint_text);
        centerText = typedArray.getString(R.styleable.mv_MultifunctionView_mv_center_text);
        centerBottomText = typedArray.getString(R.styleable.mv_MultifunctionView_mv_center_bottom_text);
        rightText = typedArray.getString(R.styleable.mv_MultifunctionView_mv_right_text);
        rightIcon = typedArray.getResourceId(R.styleable.mv_MultifunctionView_mv_right_icon, DigitalUtils.LEVEL_0);
        lineVisibility = typedArray.getBoolean(R.styleable.mv_MultifunctionView_mv_line_visibility, lineVisibility);
        lineRightVisibility = typedArray.getBoolean(R.styleable.mv_MultifunctionView_mv_line_right_visibility, lineRightVisibility);
        requiredOptionsVisibility = typedArray.getBoolean(R.styleable.mv_MultifunctionView_mv_required_options_visibility, requiredOptionsVisibility);

        if (layoutType == DigitalUtils.LEVEL_1) {
            initViewData1();
        } else if (layoutType == DigitalUtils.LEVEL_2) {
            initViewData2();
        } else if (layoutType == DigitalUtils.LEVEL_3) {
            initViewData3();
        } else if (layoutType == DigitalUtils.LEVEL_4) {
            initViewData4();
        } else if (layoutType == DigitalUtils.LEVEL_5) {
            initViewData5();
        } else if (layoutType == DigitalUtils.LEVEL_6) {
            initViewData6();
        } else if (layoutType == 7) {
            initViewData7();
        } else {
            initViewData0();
        }
    }

    private void initViewData0() {
        initView(R.layout.mv_type_0);
        if (rootView == null) {
            return;
        }
        tvLeft = rootView.findViewById(R.id.tvLeft);
        etCenter = rootView.findViewById(R.id.etCenter);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);

        tvLeft.setText(leftText);

        if (leftColor != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextColor(leftColor);
        }
        if (leftSize != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextSize(DensityUtils.pxToDp(leftSize));
        }
        if (requiredOptionsVisibility) {
            tvLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_required_options), null, null, null);
        }
        etCenter.setHint(centerHintText);
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
    }

    private void initViewData1() {
        initView(R.layout.mv_type_1);
        if (rootView == null) {
            return;
        }

        tvLeft = rootView.findViewById(R.id.tvLeft);
        etCenter = rootView.findViewById(R.id.etCenter);
        ivRight = rootView.findViewById(R.id.ivRight);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);

        tvLeft.setText(leftText);

        if (leftColor != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextColor(leftColor);
        }
        if (leftSize != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextSize(DensityUtils.pxToDp(leftSize));
        }
        if (requiredOptionsVisibility) {
            tvLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_required_options), null, null, null);
        }
        etCenter.setHint(centerHintText);
        ivRight.setImageResource(rightIcon);
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
    }

    private void initViewData2() {
        initView(R.layout.mv_type_2);
        if (rootView == null) {
            return;
        }
        tvLeft = rootView.findViewById(R.id.tvLeft);
        tvCenter = rootView.findViewById(R.id.tvCenter);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);

        tvLeft.setText(leftText);

        if (leftColor != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextColor(leftColor);
        }
        if (leftSize != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextSize(DensityUtils.pxToDp(leftSize));
        }
        if (requiredOptionsVisibility) {
            tvLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_required_options), null, null, null);
        }
        tvCenter.setHint(centerHintText);
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
    }

    private void initViewData3() {
        initView(R.layout.mv_type_3);
        if (rootView == null) {
            return;
        }
        tvLeft = rootView.findViewById(R.id.tvLeft);
        tvCenter = rootView.findViewById(R.id.tvCenter);
        ivRight = rootView.findViewById(R.id.ivRight);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);

        tvLeft.setText(leftText);

        if (leftColor != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextColor(leftColor);
        }
        if (leftSize != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextSize(DensityUtils.pxToDp(leftSize));
        }
        if (requiredOptionsVisibility) {
            tvLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_required_options), null, null, null);
        }
        tvCenter.setHint(centerHintText);
        ivRight.setImageResource(rightIcon);
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
    }

    private void initViewData4() {
        initView(R.layout.mv_type_4);
        if (rootView == null) {
            return;
        }
        tvLeft = rootView.findViewById(R.id.tvLeft);
        msItemType = rootView.findViewById(R.id.msItemType);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);

        tvLeft.setText(leftText);

        if (leftColor != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextColor(leftColor);
        }
        if (leftSize != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextSize(DensityUtils.pxToDp(leftSize));
        }
        if (requiredOptionsVisibility) {
            tvLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_required_options), null, null, null);
        }
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
    }

    private void initViewData5() {
        initView(R.layout.mv_type_5);
        if (rootView == null) {
            return;
        }

        clView = rootView.findViewById(R.id.clView);
        ivLeft = rootView.findViewById(R.id.ivLeft);
        tvCenter = rootView.findViewById(R.id.tvCenter);
        ivRight = rootView.findViewById(R.id.ivRight);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);

        setLayoutHeight();

        ivLeft.setImageResource(leftIcon);
        tvCenter.setText(centerText);
        ivRight.setImageResource(rightIcon);
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
    }

    private void initViewData6() {
        initView(R.layout.mv_type_6);
        if (rootView == null) {
            return;
        }
        tvLeft = rootView.findViewById(R.id.tvLeft);
        tvRight = rootView.findViewById(R.id.tvRight);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);

        tvLeft.setText(leftText);
        if (leftColor != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextColor(leftColor);
        }
        if (leftSize != DigitalUtils.LEVEL_N_1) {
            tvLeft.setTextSize(DensityUtils.pxToDp(leftSize));
        }
        if (requiredOptionsVisibility) {
            tvLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_required_options), null, null, null);
        }
        tvRight.setText(rightText);
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
    }

    private void initViewData7() {
        initView(R.layout.mv_type_7);
        if (rootView == null) {
            return;
        }
        ivLeft = rootView.findViewById(R.id.ivLeft);
        tvCenter = rootView.findViewById(R.id.tvCenter);
        tvCenterBottom = rootView.findViewById(R.id.tvCenterBottom);
        vLineBottom = rootView.findViewById(R.id.vLineBottom);
        vLineRight = rootView.findViewById(R.id.vLineRight);

        ivLeft.setImageResource(leftIcon);
        tvCenter.setText(centerText);
        tvCenterBottom.setText(centerBottomText);
        vLineBottom.setVisibility(lineVisibility ? View.VISIBLE : View.GONE);
        vLineRight.setVisibility(lineRightVisibility ? View.VISIBLE : View.GONE);
    }

    private void initView(int layoutId) {
        rootView = LayoutInflater.from(mContext).inflate(layoutId, this, true);
    }

    private void setLayoutHeight() {
        if (clView != null && layoutHeight != DigitalUtils.LEVEL_N_1) {
            ViewGroup.LayoutParams linearParams = clView.getLayoutParams();
            if (linearParams != null) {
                linearParams.height = layoutHeight;
                clView.setLayoutParams(linearParams);
            }
        }
    }

    public void setLeftText(String message) {
        if (tvLeft != null) {
            tvLeft.setText(message);
        }
    }

    /**
     * 设置MaterialSpinner值
     */
    public <T> void setMaterialSpinnerValue(@NonNull List<T> items) {
        if (msItemType != null && items.size() > 0) {
            msItemType.setItems(items);
        }
    }

    /**
     * 设置MaterialSpinner值
     */
    public void setMaterialSpinnerValue(String item) {
        if (msItemType != null) {
            msItemType.setText(item);
        }
    }

    /**
     * 设置MaterialSpinner监听
     */
    public void setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener onItemSelectedListener) {
        if (msItemType != null && onItemSelectedListener != null) {
            msItemType.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    /**
     * 右侧图片点击事件
     *
     * @param onClickListener
     */
    public void setRightIconListener(OnClickListener onClickListener) {
        if (ivRight != null) {
            ivRight.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置底部的文案
     */
    public void setCenterBottomText(String message) {
        if (tvCenterBottom != null) {
            tvCenterBottom.setText(message);
        }
    }

    /**
     * 设置中间的文案
     */
    public void setCenterText(String message) {
        if (message != null) {
            if (tvCenter != null) {
                tvCenter.setText(message);
            } else if (etCenter != null) {
                etCenter.setText(message);
                etCenter.setSelection(message.length());
            }
        }
    }

    /**
     * 获取中间文案
     */
    public String getCenterText() {
        if (tvCenter != null) {
            return tvCenter.getText().toString();
        }
        if (etCenter != null) {
            return etCenter.getText().toString();
        }
        return "";
    }

    /**
     * 中间edittext的动态变换
     */
    public void afterTextChanged(ClearViewTextWatcher clearViewTextWatcher) {
        if (etCenter != null) {
            etCenter.addTextChangedListener(clearViewTextWatcher);
        }
    }

    /**
     * 设置右侧的文案
     */
    public void setRightText(String message) {
        if (tvRight != null) {
            tvRight.setText(message);
        }
    }

    /**
     * 隐藏箭头
     */
    public void setHideArrow(boolean isHideArrow) {
        if (msItemType != null) {
            msItemType.setHideArrow(isHideArrow);
        }
    }

}
