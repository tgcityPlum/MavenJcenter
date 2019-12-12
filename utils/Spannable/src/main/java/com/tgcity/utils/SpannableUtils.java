package com.tgcity.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * @author TGCity
 * @date 2019/12/6
 * @describe 富文本的工具类
 * <pre><code>
 *     //设置不同颜色
 *      SpannableUtils.getInstance()
 *                 .setDefaultColor()
 *                 .setSpannableForColor();
 *    //设置不同字体
 *     SpannableUtils.getInstance()
 *                 .setProportion()
 *                 .setSpannableSize();
 * </code></pre>
 */
public class SpannableUtils {

    private static volatile SpannableUtils spannableUtils;
    private static int mDefaultColor;
    private static float mProportion;

    public static SpannableUtils getInstance() {
        if (spannableUtils == null) {
            synchronized (SpannableUtils.class) {
                if (spannableUtils == null) {
                    spannableUtils = new SpannableUtils();
                }
            }
        }
        return spannableUtils;
    }

    public static SpannableUtils setDefaultColor(int defaultColor) {
        mDefaultColor = defaultColor;

        return spannableUtils;
    }

    public static SpannableUtils setProportion(float proportion) {
        mProportion = proportion;

        return spannableUtils;
    }

    public static SpannableString setSpannableForColor(Context context, String message, int partLength, boolean isFromStart) {
        return setSpannableForColor(message, context, mDefaultColor, isFromStart ? 0 : partLength, isFromStart ? partLength : message.length());
    }

    public static SpannableString setSpannableForColor(String message, Context context, int color, int startLength, int endLength) {
        SpannableString spannableString = new SpannableString(message);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), startLength, endLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setSpannableSize(String message, int partLength, boolean isFromStart) {
        return setSpannableSize(message, mProportion, isFromStart ? 0 : partLength, isFromStart ? partLength : message.length());
    }

    public static SpannableString setSpannableSize(String message, float proportion, int startLength, int endLength) {
        SpannableString spannableString = new SpannableString(message);
        spannableString.setSpan(new RelativeSizeSpan(proportion), startLength, endLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
