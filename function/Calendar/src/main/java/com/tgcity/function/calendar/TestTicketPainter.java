package com.tgcity.function.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 票务绘制类
 */
public class TestTicketPainter implements CalendarPainter {


    protected Paint mTextPaint;
    protected Paint mLinePaint;
    protected Paint mBgPaint;
    private int mCircleRadius;
    private Context mContext;
    private ICalendar mICalendar;

    private Map<LocalDate, String> mPriceMap;

    protected List<LocalDate> mHolidayList;
    protected List<LocalDate> mWorkdayList;

    public TestTicketPainter(Context context, ICalendar iCalendar) {

        mContext = context;
        mICalendar = iCalendar;
        mTextPaint = getPaint();
        mBgPaint = getPaint();
        mLinePaint = getPaint();

        mBgPaint.setColor(Color.parseColor("#7D7DFF"));
        mCircleRadius = (int) DensityUtil.dp2px(context, 20);

        mPriceMap = new HashMap<>();
        mHolidayList = new ArrayList<>();
        mWorkdayList = new ArrayList<>();

        List<String> holidayList = CalendarUtil.getHolidayList();
        for (int i = 0; i < holidayList.size(); i++) {
            mHolidayList.add(new LocalDate(holidayList.get(i)));
        }
        List<String> workdayList = CalendarUtil.getWorkdayList();
        for (int i = 0; i < workdayList.size(); i++) {
            mWorkdayList.add(new LocalDate(workdayList.get(i)));
        }

    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }


    @Override
    public void onDrawToday(Canvas canvas, RectF rectF, LocalDate localDate, List<LocalDate> selectedDateList) {
        drawSelectBg(canvas, rectF, selectedDateList.contains(localDate), true);
        drawSolar(canvas, rectF, localDate, selectedDateList.contains(localDate), true);
        drawLine(canvas, rectF, localDate);
//        drawPrice(canvas, rectF, localDate, selectedDateList.contains(localDate), true);
    }

    @Override
    public void onDrawCurrentMonthOrWeek(Canvas canvas, RectF rectF, LocalDate localDate, List<LocalDate> selectedDateList) {
        drawSelectBg(canvas, rectF, selectedDateList.contains(localDate), true);
        drawSolar(canvas, rectF, localDate, selectedDateList.contains(localDate), true);
        drawLine(canvas, rectF, localDate);
//        drawPrice(canvas, rectF, localDate, selectedDateList.contains(localDate), true);
    }

    @Override
    public void onDrawLastOrNextMonth(Canvas canvas, RectF rectF, LocalDate localDate, List<LocalDate> selectedDateList) {
        drawSelectBg(canvas, rectF, selectedDateList.contains(localDate), false);
        drawSolar(canvas, rectF, localDate, selectedDateList.contains(localDate), false);
        drawLine(canvas, rectF, localDate);
//        drawPrice(canvas, rectF, localDate, selectedDateList.contains(localDate), false);
    }

    @Override
    public void onDrawDisableDate(Canvas canvas, RectF rectF, LocalDate localDate) {

    }

    //绘制选中背景
    private void drawSelectBg(Canvas canvas, RectF rectF, boolean isisSelected, boolean isCurrectMonthOrWeek) {
        if (isisSelected) {
            mBgPaint.setAlpha(isCurrectMonthOrWeek ? 255 : 100);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), mCircleRadius, mBgPaint);
        }
    }

    //绘制公历
    private void drawSolar(Canvas canvas, RectF rectF, LocalDate localDate, boolean isSelected, boolean isCurrectMonthOrWeek) {
        mTextPaint.setTextSize(DensityUtil.dp2px(mContext, 16));
        mTextPaint.setColor(isSelected ? Color.WHITE : Color.BLACK);
        mTextPaint.setAlpha(isCurrectMonthOrWeek ? 255 : 100);
//        canvas.drawText(localDate.getDayOfMonth() + "", rectF.centerX(), TextUtils.isEmpty(mPriceMap.get(localDate)) ? getBaseLineY(rectF) : rectF.centerY(), mTextPaint);
        canvas.drawText(localDate.getDayOfMonth() + "", rectF.centerX(), getBaseLineY(rectF), mTextPaint);
    }

    //绘制下划线
    private void drawLine(Canvas canvas, RectF rectF, LocalDate localDate) {
        String type = mPriceMap.get(localDate);
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "0":
                    mLinePaint.setColor(Color.parseColor("#FF3839"));
                    break;
                case "1":
                    mLinePaint.setColor(Color.parseColor("#FFCC33"));
                    break;
                case "2":
                    mLinePaint.setColor(Color.parseColor("#00CC34"));
                    break;
                default:
                    break;
            }
            mLinePaint.setStrokeWidth(DensityUtil.dp2px(mContext, 2));
            canvas.drawLine(rectF.centerX() - DensityUtil.dp2px(mContext, 12), rectF.centerY() + DensityUtil.dp2px(mContext, 12), rectF.centerX() + DensityUtil.dp2px(mContext, 12), rectF.centerY() + DensityUtil.dp2px(mContext, 12), mLinePaint);
        }
    }

    //canvas.drawText的基准线
    private int getBaseLineY(RectF rectF) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);
        return baseLineY;
    }


    public void setPriceMap(Map<LocalDate, String> priceMap) {
        if (priceMap != null) {
            mPriceMap.clear();
            mPriceMap.putAll(priceMap);
            mICalendar.notifyCalendar();
        }
    }
}
