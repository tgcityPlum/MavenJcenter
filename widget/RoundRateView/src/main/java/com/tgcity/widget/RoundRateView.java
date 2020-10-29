package com.tgcity.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tgcity.widget.roundrateview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TGCity
 * @date 2020/9/30
 * @description 圆环比例表控件
 */
public class RoundRateView extends View {

    private Paint mPaint;
    private int mCircleWidth; //圆环宽度
    private float intervalAngle = 4f;//间隔角度
    private int intervalColor = Color.GRAY;//间隔颜色 默认灰色
    private int aboveTextColor = Color.GRAY;//上面的文字 默认灰色
    private int belowTextColor = Color.GRAY;//下面的文字 默认灰色
    private int aboveTextSize = 60;//上面的文字字体大小
    private int belowTextSize = 40;//下面的文字字体大小
    private boolean isShowText = false; //是否显示中间文字 默认显示
    /**
     * 画文字的画笔
     */
    private Paint textPaint;

    private int colors[] = {Color.parseColor("#fc4032")
            , Color.parseColor("#07a1fb")
            , Color.parseColor("#FF3839")};


    private List<Float> angleList = new ArrayList<>(); //所有的角度 集合
    private List<Integer> colorList = new ArrayList<>(); //所有的色值 集合
    private RectF oval;
    private double allMoney;
    private boolean isShowMoney = true; //是否是明文显示钱数,默认是明文显示
    //    private Paint p;

    public RoundRateView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundRateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RoundRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.rrv_RoundRateView, defStyleAttr, 0);
        int n = array.getIndexCount();//自定义属性个数
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);//通过索引获取具体属性
            if (attr == R.styleable.rrv_RoundRateView_rrv_circleWidth) {
                mCircleWidth = array.getDimensionPixelSize(attr, (int) dip2px(20f));
                if (mCircleWidth < 2) {
                    mCircleWidth = 2;//最小宽度是2
                }
            } else if (attr == R.styleable.rrv_RoundRateView_rrv_intervalAngle) {
                intervalAngle = array.getDimensionPixelSize(attr, (int) dip2px(4f));
            } else if (attr == R.styleable.rrv_RoundRateView_rrv_aboveTextSize) {
                aboveTextSize = array.getDimensionPixelSize(attr, (int) dip2px(60));
            } else if (attr == R.styleable.rrv_RoundRateView_rrv_belowTextSize) {
                belowTextSize = array.getDimensionPixelSize(attr, (int) dip2px(40));
            } else if (attr == R.styleable.rrv_RoundRateView_rrv_intervalColor) {
                intervalColor = array.getColor(attr, Color.GRAY);
            } else if (attr == R.styleable.rrv_RoundRateView_rrv_aboveTextColor) {
                aboveTextColor = array.getColor(attr, Color.GRAY);
            } else if (attr == R.styleable.rrv_RoundRateView_rrv_belowTextColor) {
                belowTextColor = array.getColor(attr, Color.GRAY);
            } else if (attr == R.styleable.rrv_RoundRateView_rrv_isShowText) {
                isShowText = array.getBoolean(attr, true);
            }
        }
        array.recycle();//定义完后属性对象回


        mPaint = new Paint();  //创建画笔
        mPaint.setAntiAlias(true);  //设置绘制时抗锯齿
        mPaint.setStyle(Paint.Style.STROKE); //设置绘画空心（比如画圆时画的是空心圆而不是实心圆）
        mPaint.setStrokeWidth(mCircleWidth);//设置画笔线宽

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);

//        //这个是画矩形的画笔，方便大家理解这个圆弧
//        p = new Paint();
//        p.setStyle(Paint.Style.STROKE);
//        p.setColor(Color.RED);
    }

    /**
     * 设置数据列表
     *
     * @param list
     */
    public void setList(List<Double> list) {
        float allIntervalAngle = 0f;//所有间隔加起来的角度
        float allModuleAngle;  //所有模块加起来的角度  allModuleAngle + allIntervalAngle=360;
        if (list.size() > colors.length) {
            return;
        }
        angleList.clear();
        colorList.clear();
        allMoney = 0d;
        for (int i = 0; i < list.size(); i++) {
            allMoney = allMoney + list.get(i);
        }
        if (list.size() == 1) { //如果只有一条数据,就不要间隔
            angleList.add(360f);
            colorList.add(colors[0]);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != 0) {
                    allIntervalAngle += intervalAngle;
                }
            }
            if (allIntervalAngle == intervalAngle) {//如果只有一条数据不为0,就不要间隔颜色
                angleList.add(360f);
                colorList.add(colors[0]);
            } else {
                allModuleAngle = 360 - allIntervalAngle;
                float angle = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != 0) {
                        float e = (float) (list.get(i) / allMoney * allModuleAngle);
                        if (i == list.size() - 1) {//如果是最后一个色块,所占角度就是剩余全部的角度
                            this.angleList.add(allModuleAngle - angle);
                        } else {
                            angle += e;
                            this.angleList.add(e);
                        }
                        this.angleList.add(intervalAngle);
                        this.colorList.add(colors[i]);
                        this.colorList.add(intervalColor);
                    }

                }
            }

        }

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthPixels = this.getResources().getDisplayMetrics().widthPixels;//获取屏幕宽
        int heightPixels = this.getResources().getDisplayMetrics().heightPixels;//获取屏幕高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int hedight = MeasureSpec.getSize(heightMeasureSpec);
        int minWidth = Math.min(widthPixels, width);
        int minHedight = Math.min(heightPixels, hedight);
        setMeasuredDimension(Math.min(minWidth, minHedight), Math.min(minWidth, minHedight));

    }

    public void setIsShowText(boolean isShowText) {
        this.isShowText = isShowText;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        if (isShowText) {
            drawText(canvas);
        }
    }

    /**
     * 画出圆弧
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        if (oval == null) {
            int min = Math.min(getWidth() - mCircleWidth / 2, getHeight() - mCircleWidth / 2);
            oval = new RectF(mCircleWidth / 2, mCircleWidth / 2,
                    min, min);
        }
        float startAngle = -90f;//经过试验，-90这个值就是12点方向的位置

        //画一个底层圆环,颜色和间隙颜色一样,因为间隙的色块和其他色块之间会有小缝隙
        mPaint.setColor(intervalColor);
        mPaint.setStrokeWidth(mCircleWidth - 1); //宽度减1是防止底色溢出
        canvas.drawArc(oval, -90, 360, false, mPaint);


        mPaint.setStrokeWidth(mCircleWidth);
        for (int i = 0; i < angleList.size(); i++) {
            mPaint.setColor(colorList.get(i));//设置画笔颜色
            if (i > 0) {
                startAngle += angleList.get(i - 1);  //减1是因为总会有缝隙
            }
            canvas.drawArc(oval, startAngle, angleList.get(i), false, mPaint);
        }
    }

    /**
     * 画出中间的文字
     *
     * @param canvas 画布对象
     */
    private void drawText(Canvas canvas) {
        int center = getWidth() / 2;
        String percent;
        if (isShowMoney) {
            if (allMoney > 100000000) {
                double div = ((int) (allMoney / 1000000)) / 100;
                percent = div + "亿";
            }
//            else if (allMoney>10000) {
//                try {
//                    double div = Arith.div(allMoney, 10000d, 7);
//                    percent = div+"万";
//                } catch (IllegalAccessException e) {
//                    percent = "万";
//                    e.printStackTrace();
//                }
//
//            }
            else {
//                percent = CommonUtil.decimalFormatInt(allMoney);
                percent = "----";
            }
        } else {
            percent = "***";
        }
        textPaint.setTextSize(aboveTextSize);
        //防止文字边界超过内环边界  上面的文字大小减小 下面的文字大小也跟着减小
        while (textPaint.measureText(percent) > getWidth() - 2f * mCircleWidth) {
            aboveTextSize--;
            belowTextSize--;
            textPaint.setTextSize(aboveTextSize);
        }

        textPaint.setTextAlign(Paint.Align.CENTER); // 设置文字居中，文字的x坐标要注意
        textPaint.setColor(aboveTextColor); // 设置文字颜色
        textPaint.setStrokeWidth(0); // 注意此处一定要重新设置宽度为0,否则绘制的文字会重叠
        Rect bounds = new Rect(); // 文字边框
        textPaint.getTextBounds(percent, 0, percent.length(), bounds); // 获得绘制文字的边界矩形
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt(); // 获取绘制Text时的四条线
        int baseline = center + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom; // 计算文字的基线,方法见http://blog.csdn.net/harvic880925/article/details/50423762
        canvas.drawText(percent, center, baseline, textPaint); // 绘制文字

        percent = "总资产(元)";
        textPaint.setColor(belowTextColor); // 设置文字颜色
        textPaint.setTextSize(belowTextSize);
        //防止下面的文字超出内环边界
        while (textPaint.measureText(percent) > getWidth() - 2f * mCircleWidth) {
            belowTextSize--;
            textPaint.setTextSize(belowTextSize);
        }
        textPaint.getTextBounds(percent, 0, percent.length(), bounds); // 获得绘制文字的边界矩形
        Paint.FontMetricsInt fontMetrics1 = textPaint.getFontMetricsInt(); // 获取绘制Text时的四条线
        int baseline1 = center + (fontMetrics1.bottom - fontMetrics1.top) / 2 - fontMetrics.bottom + 38 * 2;
        canvas.drawText(percent, center, baseline1, textPaint); // 绘制文字
    }


    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

    public void setIsShowMoney(boolean isShowMoney) {
        this.isShowMoney = isShowMoney;
        invalidate();

    }
}
