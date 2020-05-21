package com.tgcity.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * 屏幕工具类
 *
 * @author tgcity
 */
public class ScreenUtils {

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidthPixels(Activity context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenWidthPixels(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        DisplayMetrics metric = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeightPixels(Activity context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenHeightPixels(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        DisplayMetrics metric = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @return int
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        if (result == 0) {
            result = (int) Math.ceil(20 * context.getResources().getDisplayMetrics().density);
        }
        return result;
    }

    public static Point getScreenSize(Context context) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point out = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(out);
        } else {
            int width = display.getWidth();
            int height = display.getHeight();
            out.set(width, height);
        }
        return out;
    }

    /**
     * 显示蒙版
     */
    public static void showWindowMask(Window window) {
        if (window == null) {
            throw new RuntimeException("window is null");
        }
        WindowManager.LayoutParams params = window.getAttributes();
        // 此行代码为了兼容华为系列机型
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        params.alpha = 0.3f;
        window.setAttributes(params);
    }

    /**
     * 隐藏蒙版
     */
    public static void hideWindowMask(Window window) {
        if (window == null) {
            throw new RuntimeException("window is null");
        }
        WindowManager.LayoutParams params = window.getAttributes();
        // 此行代码为了兼容华为系列机型
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        params.alpha = 1f;
        //params.dimAmount = 0f;
        window.setAttributes(params);
    }

}
