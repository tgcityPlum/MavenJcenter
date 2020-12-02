package com.tgcity.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author TGCity
 * 字符串相关工具类
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true;
        } else {
            if (s.length() == 0) {
                return true;
            } else {
                if ("".equals(s) || "null".equals(s) || "NULL".equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断字符串是否不为n空
     *
     * @param s 待校验字符串
     * @return {@code true}: 非空<br> {@code false}: 空
     */
    public static boolean isNotEmpty(CharSequence s) {
        if (s == null) {
            return false;
        } else {
            if (s.length() == 0) {
                return false;
            } else {
                if ("".equals(s) || "null".equals(s) || "NULL".equals(s)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断控件中的文案是否为空
     *
     * @param view 控件
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(TextView view) {
        return isEmpty(getText(view));
    }

    /**
     * 判断控件中的文案是否不为空
     *
     * @param view 控件
     * @return {@code true}: 非空<br> {@code false}: 空
     */
    public static boolean isNotEmpty(TextView view) {
        return isNotEmpty(getText(view));
    }

    /**
     * 格式化string
     *
     * @param s String
     * @return String
     */
    public static String dislodgeEmptyToEmpty(String s) {
        if (isEmpty(s)) {
            return "";
        }
        return s;
    }

    /**
     * 格式化string
     *
     * @param s String
     * @return String
     */
    public static String dislodgeEmptyToZero(String s) {
        if (isEmpty(s)) {
            return "0";
        }
        return s;
    }

    /**
     * 自定义格式化string的输出样式
     *
     * @param message 字符串
     * @param format  为空时的返回样式
     * @return String
     */
    public static String dislodgeEmptyToCustomize(String message, String format) {
        if (isEmpty(message)) {
            return format;
        }
        return message;
    }

    /**
     * 格式化前缀string
     */
    public static String dislodgePrefixToEmpty(String message, String prefix) {
        if (isEmpty(message)) {
            return prefix;
        }
        return prefix + message;
    }

    /**
     * 自定义格式化前缀string
     */
    public static String dislodgePrefixToCustomize(String message, String prefix, String format) {
        if (isEmpty(message)) {
            return prefix + format;
        }
        return prefix + message;
    }

    /**
     * 将double转为数值，并最多保留num位小数。例如当num为2时，1.268为1.27，1.2仍为1.2；1仍为1，而非1.00;100.00则返回100。
     *
     * @param d   数值
     * @param num 小数位数
     * @return 数值
     */
    public static String double2String(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        //保留两位小数
        nf.setMaximumFractionDigits(num);
        //去掉数值中的千位分隔符
        nf.setGroupingUsed(false);

        String temp = nf.format(d);
        if (temp.contains(".")) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!s2.substring(i - 1, i).equals("0")) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }

    /**
     * 获取表情限制
     *
     * @return InputFilter
     */
    public static InputFilter getEmojiFilter() {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                if (containsEmoji(source.toString())) {
                    return "";
                }
                return null;
            }

            /**
             * 检测是否有emoji表情
             */
            private boolean containsEmoji(String source) {
                int len = source.length();
                for (int i = 0; i < len; i++) {
                    char codePoint = source.charAt(i);
                    if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                        return true;
                    }
                }
                return false;
            }

            /**
             * 判断是否是Emoji
             *
             * @param codePoint 比较的单个字符
             */
            private boolean isEmojiCharacter(char codePoint) {
                return (codePoint == 0x0)
                        || (codePoint == 0x9)
                        || (codePoint == 0xA)
                        || (codePoint == 0xD)
                        || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                        || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                        || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)
                );
            }

        };
        return emojiFilter;
    }

    /**
     * 获取控件上的文案
     *
     * @param tv TextView
     * @return String
     */
    @Deprecated
    public static String getStringFromView(TextView tv) {
        return getText(tv);
    }

    /**
     * 获取控件上的文案
     *
     * @param view TextView
     * @return String
     */
    public static String getText(TextView view) {
        if (view != null && view.getText().length() != 0) {
            return view.getText().toString().trim();
        }
        return "";
    }

    /**
     * 获取本地json文件
     *
     * @param fileName 文件名
     * @param context  Context
     * @return String
     */
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}