package com.tgcity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tgcity.utils.DensityUtils;
import com.tgcity.widget.doubletextview.R;

/**
 * @author TGCity
 * @date 2019/11/14
 * @description 一行有两个textView控件的组件
 * <pre>
 *     <code>
 *         1.xml file
 *         <com.tgcity.widget.TwoTextViewLayout
 *                         android:id="@+id/ttv_riverName"
 *                         android:layout_width="match_parent"
 *                         android:layout_height="@dimen/dp_45"
 *                         android:layout_marginLeft="@dimen/dp_6"
 *                         app:dt_isShowLine="true"
 *                         app:dt_leftTitleCol="@color/color_ff000000"
 *                         app:dt_leftTitleStr="问题河道"
 *                         app:dt_rightTitleCol="@color/color_a6000000" />
 *
 *         2.class file
 *         tv.setRightContent("xxx");
 *         tv.setContentSize(14);
 *         tv.setPaddingWidth(0);
 *     </code>
 * </pre>
 */
public class DoubleTextView extends ConstraintLayout {

    private Context context;
    private String leftTitleStr;
    private int leftTitleCol;
    private int rightTitleCol;
    private boolean isShowLine;
    private boolean isShowRightArrow;
    private int viewBackground;

    private ConstraintLayout clRoot;
    private TextView tvTitle;
    private TextView tvContent;
    private View vLine;

    public DoubleTextView(Context context) {
        this(context, null);
    }

    public DoubleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.dt_DoubleTextViewStyle);

        leftTitleStr = typedArray.getString(R.styleable.dt_DoubleTextViewStyle_dt_leftTitleStr);
        leftTitleCol = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_dt_leftTitleCol, context.getResources().getColor(R.color.dt_color_666666));
        rightTitleCol = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_dt_rightTitleCol, context.getResources().getColor(R.color.dt_color_666666));
        isShowLine = typedArray.getBoolean(R.styleable.dt_DoubleTextViewStyle_dt_isShowLine, false);
        isShowRightArrow = typedArray.getBoolean(R.styleable.dt_DoubleTextViewStyle_dt_isShowRightArrow, false);
        viewBackground = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_dt_viewBackground, context.getResources().getColor(R.color.dt_color_ffffff));

        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.dt_double_text_view, this);

        clRoot = findViewById(R.id.cl_root);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        vLine = findViewById(R.id.v_line);

        setTitle();
        setTitleCol();
        setContentCol();
        updateShowRightArrow(isShowRightArrow);
        setShowLine();
        setViewBackground();
    }

    public void setContentSize(int sp) {
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    public void setTitleSize(int sp) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    private void setTitle() {
        tvTitle.setText(leftTitleStr);
    }

    public void setRightContent(String message) {
        tvContent.setText(message);
    }


    private void setTitleCol() {
        tvTitle.setTextColor(leftTitleCol);
    }

    private void setContentCol() {
        tvContent.setTextColor(rightTitleCol);
    }

    /**
     * 更新文本的颜色
     * @param color int
     */
    public void updateContentCol(int color) {
        rightTitleCol = getResources().getColor(color);
        setContentCol();
    }

    /**
     * 更新文本控件的箭头显示状态
     * @param flag boolean
     */
    public void updateShowRightArrow(boolean flag) {
        if (flag) {
            Drawable drawable = getResources().getDrawable(R.mipmap.dt_arrow_right);
            drawable.setBounds(0, 0, DensityUtils.dpToPx(20), DensityUtils.dpToPx(20));
            tvContent.setCompoundDrawables(null, null, drawable, null);
        } else {
            tvContent.setCompoundDrawables(null, null, null, null);
        }
    }

    private void setShowLine() {
        vLine.setVisibility(isShowLine ? View.VISIBLE : View.GONE);
    }

    private void setViewBackground() {
        this.setBackgroundColor(viewBackground);
    }

    public void setPaddingWidth(int paddingWidth) {
        clRoot.setPadding(paddingWidth, paddingWidth, paddingWidth, paddingWidth);
    }
}
