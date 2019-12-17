package com.tgcity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tgcity.widget.doubletextview.R;

/**
 * @author TGCity
 * @date 2019/11/14
 * @description 一行有两个textView控件的组件
 * <pre>
 *     <code>
 *         1.xml file
 *         <com.tgcity.base.widget.TwoTextViewLayout
 *                         android:id="@+id/ttv_riverName"
 *                         android:layout_width="match_parent"
 *                         android:layout_height="@dimen/dp_45"
 *                         android:layout_marginLeft="@dimen/dp_6"
 *                         app:isShowLine="true"
 *                         app:leftTitleCol="@color/color_ff000000"
 *                         app:leftTitleStr="问题河道"
 *                         app:rightTitleCol="@color/color_a6000000" />
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

        leftTitleStr = typedArray.getString(R.styleable.dt_DoubleTextViewStyle_leftTitleStr);
        leftTitleCol = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_leftTitleCol, context.getResources().getColor(R.color.color_666666));
        rightTitleCol = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_rightTitleCol, context.getResources().getColor(R.color.color_666666));
        isShowLine = typedArray.getBoolean(R.styleable.dt_DoubleTextViewStyle_isShowLine, false);
        viewBackground = typedArray.getColor(R.styleable.dt_DoubleTextViewStyle_viewBackground, context.getResources().getColor(R.color.color_white));

        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.dt_double_text_view, this);

        clRoot = findViewById(R.id.cl_root);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        vLine = findViewById(R.id.v_line);

        setLeftTitle();
        setTitleCol();
        setContentCol();
        setShowLine();
        setViewBackground();
    }

    public void setContentSize(int sp) {
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    public void setTitleSize(int sp) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    public void setLeftTitle() {
        tvTitle.setText(leftTitleStr);
    }

    public void setRightContent(String message) {
        tvContent.setText(message);
    }


    public void setTitleCol() {
        tvTitle.setTextColor(leftTitleCol);
    }

    public void setContentCol() {
        tvContent.setTextColor(rightTitleCol);
    }

    public void setShowLine() {
        vLine.setVisibility(isShowLine ? View.VISIBLE : View.GONE);
    }

    public void setViewBackground() {
        this.setBackgroundColor(viewBackground);
    }

    public void setPaddingWidth(int paddingWidth) {
        clRoot.setPadding(paddingWidth, paddingWidth, paddingWidth, paddingWidth);
    }
}
