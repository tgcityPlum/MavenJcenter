package com.tgcity.function.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;


/**
 * @author necer
 * @date 2018/11/28
 */
public class AttrsUtil {


    public static Attrs getAttrs(Context context, AttributeSet attributeSet) {
        Attrs attrs = new Attrs();
        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.nc_NCalendar);

        attrs.todayCheckedBackground = ta.getResourceId(R.styleable.nc_NCalendar_nc_todayCheckedBackground, R.drawable.shape_solid_39af81_oval);
        attrs.defaultCheckedBackground = ta.getResourceId(R.styleable.nc_NCalendar_nc_defaultCheckedBackground, R.drawable.shape_stroke_b2b2b2_oval);

        attrs.todayCheckedSolarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayCheckedSolarTextColor, ContextCompat.getColor(context, R.color.color_white));
        attrs.todayUnCheckedSolarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayUnCheckedSolarTextColor, ContextCompat.getColor(context, R.color.color_39AF81));
        attrs.defaultCheckedSolarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultCheckedSolarTextColor, ContextCompat.getColor(context, R.color.color_444444));
        attrs.defaultUnCheckedSolarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultUnCheckedSolarTextColor, ContextCompat.getColor(context, R.color.color_444444));
        attrs.solarTextSize = ta.getDimension(R.styleable.nc_NCalendar_nc_solarTextSize, context.getResources().getDimension(R.dimen.sp_18));
        attrs.solarTextBold = ta.getBoolean(R.styleable.nc_NCalendar_nc_solarTextBold, context.getResources().getBoolean(R.bool.nc_textBold));

        attrs.showLunar = ta.getBoolean(R.styleable.nc_NCalendar_nc_showLunar, context.getResources().getBoolean(R.bool.nc_showLunar));
        attrs.todayCheckedLunarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayCheckedLunarTextColor, ContextCompat.getColor(context, R.color.color_white));
        attrs.todayUnCheckedLunarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayUnCheckedLunarTextColor, ContextCompat.getColor(context, R.color.color_39AF81));
        attrs.defaultCheckedLunarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultCheckedLunarTextColor, ContextCompat.getColor(context, R.color.color_AEAEAE));
        attrs.defaultUnCheckedLunarTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultUnCheckedLunarTextColor, ContextCompat.getColor(context, R.color.color_AEAEAE));
        attrs.lunarTextSize = ta.getDimension(R.styleable.nc_NCalendar_nc_lunarTextSize, context.getResources().getDimension(R.dimen.sp_10));
        attrs.lunarTextBold = ta.getBoolean(R.styleable.nc_NCalendar_nc_lunarTextBold, context.getResources().getBoolean(R.bool.nc_textBold));
        attrs.lunarDistance = ta.getDimension(R.styleable.nc_NCalendar_nc_lunarDistance, context.getResources().getDimension(R.dimen.sp_15));

        attrs.pointLocation = ta.getInt(R.styleable.nc_NCalendar_nc_pointLocation, Attrs.UP);
        attrs.pointDistance = ta.getDimension(R.styleable.nc_NCalendar_nc_pointDistance, context.getResources().getDimension(R.dimen.dp_18));
        attrs.todayCheckedPoint = ta.getResourceId(R.styleable.nc_NCalendar_nc_todayCheckedPoint, R.drawable.shape_solid_ffffff_oval);
        attrs.todayUnCheckedPoint = ta.getResourceId(R.styleable.nc_NCalendar_nc_todayUnCheckedPoint, R.drawable.shape_solid_b2b2b2_oval);
        attrs.defaultCheckedPoint = ta.getResourceId(R.styleable.nc_NCalendar_nc_defaultCheckedPoint, R.drawable.shape_stroke_b2b2b2_oval);
        attrs.defaultUnCheckedPoint = ta.getResourceId(R.styleable.nc_NCalendar_nc_defaultUnCheckedPoint, R.drawable.shape_solid_b2b2b2_oval);

        attrs.showHolidayWorkday = ta.getBoolean(R.styleable.nc_NCalendar_nc_showHoliday, context.getResources().getBoolean(R.bool.nc_showHolidayWorkday));

        attrs.todayCheckedHoliday = ta.getDrawable(R.styleable.nc_NCalendar_nc_todayCheckedHoliday);
        attrs.todayUnCheckedHoliday = ta.getDrawable(R.styleable.nc_NCalendar_nc_todayUnCheckedHoliday);
        attrs.defaultCheckedHoliday = ta.getDrawable(R.styleable.nc_NCalendar_nc_defaultCheckedHoliday);
        attrs.defaultUnCheckedHoliday = ta.getDrawable(R.styleable.nc_NCalendar_nc_defaultUnCheckedHoliday);
        attrs.todayCheckedWorkday = ta.getDrawable(R.styleable.nc_NCalendar_nc_todayCheckedWorkday);
        attrs.todayUnCheckedWorkday = ta.getDrawable(R.styleable.nc_NCalendar_nc_todayUnCheckedWorkday);
        attrs.defaultCheckedWorkday = ta.getDrawable(R.styleable.nc_NCalendar_nc_defaultCheckedWorkday);
        attrs.defaultUnCheckedWorkday = ta.getDrawable(R.styleable.nc_NCalendar_nc_defaultUnCheckedWorkday);

        attrs.holidayWorkdayTextSize = ta.getDimension(R.styleable.nc_NCalendar_nc_holidayWorkdayTextSize, context.getResources().getDimension(R.dimen.sp_10));
        attrs.holidayWorkdayTextBold = ta.getBoolean(R.styleable.nc_NCalendar_nc_holidayWorkdayTextBold, context.getResources().getBoolean(R.bool.nc_textBold));
        attrs.holidayWorkdayDistance = ta.getDimension(R.styleable.nc_NCalendar_nc_holidayWorkdayDistance, context.getResources().getDimension(R.dimen.dp_15));
        attrs.holidayWorkdayLocation = ta.getInt(R.styleable.nc_NCalendar_nc_holidayWorkdayLocation, Attrs.TOP_RIGHT);
        attrs.holidayText = ta.getString(R.styleable.nc_NCalendar_nc_holidayText);
        attrs.workdayText = ta.getString(R.styleable.nc_NCalendar_nc_workdayText);
        attrs.todayCheckedHolidayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayCheckedHolidayTextColor, ContextCompat.getColor(context, R.color.color_white));
        attrs.todayUnCheckedHolidayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayUnCheckedHolidayTextColor, ContextCompat.getColor(context, R.color.color_39AF81));
        attrs.defaultCheckedHolidayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultCheckedHolidayTextColor, ContextCompat.getColor(context, R.color.color_39AF81));
        attrs.defaultUnCheckedHolidayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultUnCheckedHolidayTextColor, ContextCompat.getColor(context, R.color.color_39AF81));
        attrs.todayCheckedWorkdayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayCheckedWorkdayTextColor, ContextCompat.getColor(context, R.color.color_white));
        attrs.todayUnCheckedWorkdayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_todayUnCheckedWorkdayTextColor, ContextCompat.getColor(context, R.color.color_EC7230));
        attrs.defaultCheckedWorkdayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultCheckedWorkdayTextColor, ContextCompat.getColor(context, R.color.color_EC7230));
        attrs.defaultUnCheckedWorkdayTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_defaultUnCheckedWorkdayTextColor, ContextCompat.getColor(context, R.color.color_EC7230));

        attrs.showNumberBackground = ta.getBoolean(R.styleable.nc_NCalendar_nc_showNumberBackground, context.getResources().getBoolean(R.bool.nc_showNumberBackground));
        attrs.numberBackgroundTextSize = ta.getDimension(R.styleable.nc_NCalendar_nc_numberBackgroundTextSize, context.getResources().getDimension(R.dimen.sp_260));
        attrs.numberBackgroundTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_numberBackgroundTextColor, ContextCompat.getColor(context, R.color.color_39AF81));
        attrs.numberBackgroundAlphaColor = ta.getInt(R.styleable.nc_NCalendar_nc_numberBackgroundAlphaColor, context.getResources().getInteger(R.integer.nc_numberBackgroundAlphaColor));

        attrs.firstDayOfWeek = ta.getInt(R.styleable.nc_NCalendar_nc_firstDayOfWeek, Attrs.SUNDAY);
        attrs.allMonthSixLine = ta.getBoolean(R.styleable.nc_NCalendar_nc_allMonthSixLine, context.getResources().getBoolean(R.bool.nc_allMonthSixLine));
        attrs.lastNextMonthClickEnable = ta.getBoolean(R.styleable.nc_NCalendar_nc_lastNextMonthClickEnable, context.getResources().getBoolean(R.bool.nc_lastNextMonthClickEnable));
        attrs.calendarBackground = ta.getDrawable(R.styleable.nc_NCalendar_nc_calendarBackground);
        attrs.lastNextMothAlphaColor = ta.getInt(R.styleable.nc_NCalendar_nc_lastNextMothAlphaColor, context.getResources().getInteger(R.integer.nc_lastNextMothAlphaColor));
        attrs.disabledAlphaColor = ta.getInt(R.styleable.nc_NCalendar_nc_disabledAlphaColor, context.getResources().getInteger(R.integer.nc_disabledAlphaColor));
        attrs.disabledString = ta.getString(R.styleable.nc_NCalendar_nc_disabledString);

        attrs.defaultCalendar = ta.getInt(R.styleable.nc_NCalendar_nc_defaultCalendar, CalendarState.MONTH.getValue());
        attrs.calendarHeight = (int) ta.getDimension(R.styleable.nc_NCalendar_nc_calendarHeight, context.getResources().getDimension(R.dimen.dp_300));
        attrs.animationDuration = ta.getInt(R.styleable.nc_NCalendar_nc_animationDuration, context.getResources().getInteger(R.integer.nc_animationDuration));

        attrs.stretchCalendarEnable =ta.getBoolean(R.styleable.nc_NCalendar_nc_stretchCalendarEnable, context.getResources().getBoolean(R.bool.nc_stretchCalendarEnable));
        attrs.stretchCalendarHeight = (int) ta.getDimension(R.styleable.nc_NCalendar_nc_stretchCalendarHeight, context.getResources().getDimension(R.dimen.dp_450));
        attrs.stretchTextSize = ta.getDimension(R.styleable.nc_NCalendar_nc_stretchTextSize, context.getResources().getDimension(R.dimen.sp_10));
        attrs.stretchTextBold = ta.getBoolean(R.styleable.nc_NCalendar_nc_stretchTextBold, context.getResources().getBoolean(R.bool.nc_textBold));
        attrs.stretchTextColor = ta.getColor(R.styleable.nc_NCalendar_nc_stretchTextColor, ContextCompat.getColor(context, R.color.color_666666));
        attrs.stretchTextDistance = ta.getDimension(R.styleable.nc_NCalendar_nc_stretchTextDistance, context.getResources().getDimension(R.dimen.dp_32));

        ta.recycle();
        return attrs;

    }

}
