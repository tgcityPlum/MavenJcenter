package com.tgcity.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.tgcity.widget.gradientbutton.R;


/**
 * @author TGCity
 * copy了StrongButton，扩展了渐变色
 */
public class GradientButton extends AppCompatButton {

    /**
     * 边框宽度
     */
    private float mStrokeDashWidth = 0;
    /**
     * 与边框之间的间隙
     */
    private float mStrokeDashGap = 0;
    /**
     * 用来存放各种交互动作
     */
    private int[][] states = new int[4][];
    /**
     * 字体颜色集合
     */
    private ColorStateList mTextColorStateList;
    /**
     * 各种状态集合背景
     */
    private StateListDrawable mStateBackground;
    /**
     * 正常状态下背景
     */
    private GradientDrawable mNormalBackground;
    /**
     * 触摸状态下背景
     */
    private GradientDrawable mPressedBackground;
    /**
     * 禁用状态下背景
     */
    private GradientDrawable mUnableBackground;
    /**
     * 左上角半径
     */
    private float mTopLeftRadius;
    /**
     * 右上角半径
     */
    private float mTopRightRadius;
    /**
     * 左下角半径
     */
    private float mBottomLeftRadius;
    /**
     * 右下角半径
     */
    private float mBottomRightRadius;
    /**
     * 正常状态下渐变开始背景颜色
     */
    private int mNormalStartBackgroundColor = getResources().getColor(R.color.gb_normal_start_background_color);
    /**
     * 正常状态下渐变结束背景颜色
     */
    private int mNormalEndBackgroundColor = getResources().getColor(R.color.gb_normal_end_background_color);
    /**
     * 触摸状态下渐变开始背景颜色
     */
    private int mPressedStartBackgroundColor = getResources().getColor(R.color.gb_press_start_background_color);
    /**
     * 触摸状态下渐变结束背景颜色
     */
    private int mPressedEndBackgroundColor = getResources().getColor(R.color.gb_press_end_background_color);
    /**
     * 禁用状态下渐变开始背景颜色
     */
    private int mUnableStartBackgroundColor = getResources().getColor(R.color.gb_unable_start_background_color);
    /**
     * 禁用状态下渐变结束背景颜色
     */
    private int mUnableEndBackgroundColor = getResources().getColor(R.color.gb_unable_end_background_color);
    /**
     * 正常状态下边框宽度
     */
    private float mNormalStrokeWidth = 0;
    /**
     * 触摸状态下边框宽度
     */
    private float mPressedStrokeWidth = 0;
    /**
     * 禁止状态下边框宽度
     */
    private float mUnableStrokeWidth = 0;
    /**
     * 正常状态下边框颜色
     */
    private int mNormalStrokeColor = getResources().getColor(R.color.gb_normal_stroke_color);
    /**
     * 触摸状态下边框颜色
     */
    private int mPressedStrokeColor = getResources().getColor(R.color.gb_press_stroke_color);
    /**
     * 禁止状态下边框颜色
     */
    private int mUnableStrokeColor = getResources().getColor(R.color.gb_unable_stroke_color);
    /**
     * 触摸状态下文字颜色
     */
    private int mPressedTextColor = getResources().getColor(R.color.gb_normal_text_color);
    /**
     * 正常状态下文字颜色
     */
    private int mNormalTextColor = getResources().getColor(R.color.gb_press_text_color);
    /**
     * 禁止状态下文字颜色
     */
    private int mUnableTextColor = getResources().getColor(R.color.gb_unable_text_color);

    public GradientButton(Context context) {
        super(context);
    }

    public GradientButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GradientButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void clear() {
        mStrokeDashWidth = 0;
        mStrokeDashGap = 0;
        if (states != null) {
            for (int i = 0; i < states.length; i++) {
                states[i] = null;
            }
        }
        states = null;
        mTextColorStateList = null;
        mStateBackground = null;
        mNormalBackground = null;
        mPressedBackground = null;
        mUnableBackground = null;
        mTopLeftRadius = 0;
        mTopRightRadius = 0;
        mBottomLeftRadius = 0;
        mBottomRightRadius = 0;
        mNormalStartBackgroundColor = 0;
        mNormalEndBackgroundColor = 0;
        mPressedStartBackgroundColor = 0;
        mPressedEndBackgroundColor = 0;
        mUnableStartBackgroundColor = 0;
        mUnableEndBackgroundColor = 0;
        mNormalStrokeWidth = 0;
        mPressedStrokeWidth = 0;
        mUnableStrokeWidth = 0;
        mNormalStrokeColor = 0;
        mPressedStrokeColor = 0;
        mUnableStrokeColor = 0;
        mPressedTextColor = 0;
        mNormalTextColor = 0;
        mUnableTextColor = 0;
    }

    protected void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.gb_GradientButton);
        float radius = typedArray.getDimension(R.styleable.gb_GradientButton_gb_radius_Gradient, 0);
        if (radius != 0) {
            mTopLeftRadius = mTopRightRadius = mBottomLeftRadius = mBottomRightRadius = radius;
        } else {
            mTopLeftRadius = typedArray.getDimension(R.styleable.gb_GradientButton_gb_leftTopRadius_Gradient, 0);
            mTopRightRadius = typedArray.getDimension(R.styleable.gb_GradientButton_gb_rightTopRadius_Gradient, 0);
            mBottomLeftRadius = typedArray.getDimension(R.styleable.gb_GradientButton_gb_leftBottomRadius_Gradient, 0);
            mBottomRightRadius = typedArray.getDimension(R.styleable.gb_GradientButton_gb_rightBottomRadius_Gradient, 0);
        }
        mNormalTextColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_normalTextColor_Gradient, mNormalTextColor);
        mPressedTextColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_pressTextColor_Gradient, mPressedTextColor);
        mUnableTextColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_unableTextColor_Gradient, mUnableTextColor);
        mNormalStartBackgroundColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_normalBackgroundColor_start_Gradient, mNormalStartBackgroundColor);
        mNormalEndBackgroundColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_normalBackgroundColor_end_Gradient, mNormalEndBackgroundColor);
        mPressedStartBackgroundColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_pressBackgroundColor_start_Gradient, mPressedStartBackgroundColor);
        mPressedEndBackgroundColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_pressBackgroundColor_end_Gradient, mPressedEndBackgroundColor);
        mUnableStartBackgroundColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_unableBackgroundColor_start_Gradient, mUnableStartBackgroundColor);
        mUnableEndBackgroundColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_unableBackgroundColor_end_Gradient, mUnableEndBackgroundColor);
        mNormalStrokeColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_normalStrokeColor_Gradient, mNormalStrokeColor);
        mPressedStrokeColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_pressStrokeColor_Gradient, mPressedStrokeColor);
        mUnableStrokeColor = typedArray.getColor(R.styleable.gb_GradientButton_gb_unableStrokeColor_Gradient, mUnableStrokeColor);
        mNormalStrokeWidth = typedArray.getDimension(R.styleable.gb_GradientButton_gb_normalStrokeWidth_Gradient, mNormalStrokeWidth);
        mPressedStrokeWidth = typedArray.getDimension(R.styleable.gb_GradientButton_gb_pressStrokeWidth_Gradient, mPressedStrokeWidth);
        mUnableStrokeWidth = typedArray.getDimension(R.styleable.gb_GradientButton_gb_unableStrokeWidth_Gradient, mUnableStrokeWidth);
        typedArray.recycle();
        prepareStyle()
                .setCornerRadii()
                .setStroke()
                .setTexColor()
                .go();
    }


    /**
     * 初始化一些样式必须的东西
     */
    private GradientButton prepareStyle() {
        //可用且触摸状态
        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
        //可用且已获取焦点状态
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        //可用且正常状态
        states[2] = new int[]{android.R.attr.state_enabled};
        //禁止状态
        states[3] = new int[]{-android.R.attr.state_enabled};
        Drawable drawable = getBackground();
        if (drawable instanceof StateListDrawable) {
            mStateBackground = (StateListDrawable) drawable;
        } else {
            mStateBackground = new StateListDrawable();
        }
        mNormalBackground = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{mNormalStartBackgroundColor, mNormalEndBackgroundColor});
        mPressedBackground = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{mPressedStartBackgroundColor, mPressedEndBackgroundColor});
        mUnableBackground = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{mUnableStartBackgroundColor, mUnableEndBackgroundColor});
        return this;
    }

    /**
     * 设置圆角
     */
    private GradientButton setCornerRadii() {
        float[] radii = {
                mTopLeftRadius, mTopLeftRadius,
                mTopRightRadius, mTopRightRadius,
                mBottomRightRadius, mBottomRightRadius,
                mBottomLeftRadius, mBottomLeftRadius,};
        mNormalBackground.setCornerRadii(radii);
        mPressedBackground.setCornerRadii(radii);
        mUnableBackground.setCornerRadii(radii);
        return this;
    }


    /**
     * 设置边框
     */
    private GradientButton setStroke() {
        mNormalBackground.setStroke((int) mNormalStrokeWidth, mNormalStrokeColor, mStrokeDashWidth, mStrokeDashGap);
        mPressedBackground.setStroke((int) mPressedStrokeWidth, mPressedStrokeColor, mStrokeDashWidth, mStrokeDashGap);
        mUnableBackground.setStroke((int) mUnableStrokeWidth, mUnableStrokeColor, mStrokeDashWidth, mStrokeDashGap);
        return this;
    }


    /**
     * 设置文字颜色
     */
    private GradientButton setTexColor() {
        int[] colors = new int[]{mPressedTextColor, mPressedTextColor, mNormalTextColor, mUnableTextColor};
        mTextColorStateList = new ColorStateList(states, colors);
        setTextColor(mTextColorStateList);
        return this;
    }

    /**
     * 召唤神龙
     */
    private void go() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            //消除阴影
            setStateListAnimator(null);
        }
        mStateBackground.addState(states[0], mPressedBackground);
        mStateBackground.addState(states[1], mPressedBackground);
        mStateBackground.addState(states[2], mNormalBackground);
        mStateBackground.addState(states[3], mUnableBackground);
        setBackground(mStateBackground);
    }

    /**
     * 打扫卫生
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        states = null;
        mTextColorStateList = null;
        mStateBackground = null;
        mNormalBackground = null;
        mPressedBackground = null;
        mUnableBackground = null;
    }
}
