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
 *                         app:dt_rightContentCol="@color/color_a6000000" />
 *
 *         2.class file
 *         tv.setRightContent("xxx");
 *         tv.setContentSize(14);
 *         tv.setPaddingWidth(0);
 *     </code>
 *
 *     <code>
 *         dt_leftTitleStr ：左侧标题文案
 *         dt_leftTitleCol ：左侧标题颜色
 *         dt_leftTitleSize ：左侧标题尺寸
 *         dt_rightContentCol ：右侧文案颜色
 *         dt_rightContentSize ：右侧文案尺寸
 *         dt_isShowLine ：是否显示底部下划线
 *         dt_isShowRightArrow ：是否显示右侧箭头
 *         dt_viewBackground ：控件背景色
 *         dt_viewHeight ：控件高度
 *     </code>
 * </pre>
 */
public class DoubleTextView extends ConstraintLayout {

    private Context context;
    private String leftTitleStr;
    private int leftTitleCol;
    private int rightContentCol;
    private boolean isShowLine;
    private boolean isShowRightArrow;
    private int viewBackground;
    /**
     * 最终的值是px
     */
    private float viewHeight = 0f;

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
        rightContentCol = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_dt_rightContentCol, context.getResources().getColor(R.color.dt_color_666666));
        isShowLine = typedArray.getBoolean(R.styleable.dt_DoubleTextViewStyle_dt_isShowLine, false);
        isShowRightArrow = typedArray.getBoolean(R.styleable.dt_DoubleTextViewStyle_dt_isShowRightArrow, false);
        viewBackground = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_dt_viewBackground, context.getResources().getColor(R.color.dt_color_ffffff));
        viewHeight = typedArray.getDimension(R.styleable.dt_DoubleTextViewStyle_dt_viewHeight, viewHeight);

        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.dt_double_text_view, this);

        clRoot = findViewById(R.id.cl_root);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        vLine = findViewById(R.id.v_line);
        //设置左侧标题
        setTitle();
        //设置左侧标题颜色
        setTitleCol();
        //设置右侧文案颜色
        setContentCol();
        //是否显示右侧的箭头
        updateShowRightArrow(isShowRightArrow);
        //是否显示底部下划线
        setShowLine();
        //设置自定义控件背景色
        setViewBackground();
        //设置自定义控件高度
        if (viewHeight > 0) {
            setViewHeight();
        }
    }

    /**
     * 设置自定义控件高度
     */
    private void setViewHeight() {
        LayoutParams layoutParams = (LayoutParams) clRoot.getLayoutParams();
        layoutParams.height = (int) viewHeight;
        clRoot.setLayoutParams(layoutParams);
    }

    /**
     * 设置右侧文案尺寸
     * @param sp int
     */
    public void setContentSize(int sp) {
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    /**
     * 设置左侧标题尺寸
     * @param sp int
     */
    public void setTitleSize(int sp) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    /**
     * 设置左侧标题
     */
    private void setTitle() {
        tvTitle.setText(leftTitleStr);
    }

    /**
     * 设置右侧文案
     * @param message String
     */
    public void setRightContent(String message) {
        tvContent.setText(message);
    }

    /**
     * 设置左侧标题颜色
     */
    private void setTitleCol() {
        tvTitle.setTextColor(leftTitleCol);
    }

    /**
     * 设置右侧文案颜色
     */
    private void setContentCol() {
        tvContent.setTextColor(rightContentCol);
    }

    /**
     * 更新文本的颜色
     *
     * @param color int
     */
    public void updateContentCol(int color) {
        rightContentCol = getResources().getColor(color);
        setContentCol();
    }

    /**
     * 更新文本控件的箭头显示状态
     *
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

    /**
     * 是否显示底部下划线
     */
    private void setShowLine() {
        vLine.setVisibility(isShowLine ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置自定义控件背景色
     */
    private void setViewBackground() {
        this.setBackgroundColor(viewBackground);
    }

    /**
     * 设置自定义控件的内部间距
     * @param paddingWidth int
     */
    public void setPaddingWidth(int paddingWidth) {
        clRoot.setPadding(paddingWidth, paddingWidth, paddingWidth, paddingWidth);
    }
}
