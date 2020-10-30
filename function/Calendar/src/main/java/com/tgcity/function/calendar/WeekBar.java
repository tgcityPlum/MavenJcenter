package com.tgcity.function.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by necer on 2018/12/24.
 */
public class WeekBar extends AppCompatTextView {


    public String[] days = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    private int type;//一周的第一天是周几
    private TextPaint textPaint;

    public WeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.nc_NCalendar);
        type = ta.getInt(R.styleable.nc_NCalendar_nc_firstDayOfWeek, Attrs.SUNDAY);
        ta.recycle();

        textPaint = getPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getMeasuredWidth() - paddingRight - paddingLeft;
        int height = getMeasuredHeight() - paddingTop - paddingBottom;
        for (int i = 0; i < days.length; i++) {
            Rect rect = new Rect(paddingLeft + (i * width / days.length), paddingTop, paddingLeft + ((i + 1) * width / days.length), paddingTop + height);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);
            String day;
            if (type == Attrs.MONDAY) {
                int j = i + 1;
                day = days[j > days.length - 1 ? 0 : j];
            } else {
                day = days[i];
            }
            canvas.drawText(day, rect.centerX(), baseLineY, textPaint);
        }
    }

}
