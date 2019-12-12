package com.tgcity.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author TGCity
 * @date 2019/11/19
 * @describe 时间转化工具类
 */
public class DateUtils {

    public static final String PATTERN_STYLE_1 = "yyyy年MM月dd日";
    public static final String PATTERN_STYLE_2 = "HH:mm:ss";
    public static final String PATTERN_STYLE_3 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将时间戳转化成 pattern 样式
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSimpleDateFormatDate(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

}
