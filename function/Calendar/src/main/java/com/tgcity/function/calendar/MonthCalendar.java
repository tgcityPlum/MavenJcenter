package com.tgcity.function.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import org.joda.time.LocalDate;

/**
 *
 * @author necer
 * @date 2018/9/11
 * qq群：127278900
 */
public class MonthCalendar extends BaseCalendar {

    public MonthCalendar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected BasePagerAdapter getPagerAdapter(Context context, BaseCalendar baseCalendar) {
        return new MonthPagerAdapter(context,baseCalendar);
    }

    @Override
    protected int getTwoDateCount(LocalDate startDate, LocalDate endDate, int type) {
        return CalendarUtil.getIntervalMonths(startDate, endDate);
    }

    @Override
    protected LocalDate getIntervalDate(LocalDate localDate, int count) {
        return localDate.plusMonths(count);
    }

}
