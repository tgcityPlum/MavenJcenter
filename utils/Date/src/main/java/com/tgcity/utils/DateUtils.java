package com.tgcity.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
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
    public static final String PATTERN_STYLE_4 = "yyyy-MM-dd";
    public static final String PATTERN_STYLE_5 = "HH:mm";

    /**
     * 将时间戳转化成 pattern 样式
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSimpleDateFormatDate(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


    /**
     * 将时间样式转化成时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static long getLongFormatPattern(String time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        //设置要读取的时间字符串格式
        Date date;
        try {
            date = format.parse(time);
            //转换为Date类
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将字符串 转化成 pattern 样式
     */
    public static String getSimpleDateFormatDate(String time, String pattern1, String pattern2) {
        return getSimpleDateFormatDate(getLongFormatPattern(time, pattern1), pattern2);
    }

}
