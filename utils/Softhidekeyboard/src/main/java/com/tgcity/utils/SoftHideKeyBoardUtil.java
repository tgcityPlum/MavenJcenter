package com.tgcity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

/**
 * @author TGCity
 * @date 2020/5/18
 * @description 解决键盘档住输入框
 */
public class SoftHideKeyBoardUtil {

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private int contentHeight;
    private boolean isFirst = true;
    private Activity activity;
    private int statusBarHeight;

    @Deprecated
    public static void assistActivity(Activity activity) {
        new SoftHideKeyBoardUtil(activity);
    }

    public static void getInstall(Activity activity) {
        new SoftHideKeyBoardUtil(activity);
    }

    private SoftHideKeyBoardUtil(Activity activity) {
        //获取状态栏的高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        this.activity = activity;
        FrameLayout content = activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        //界面出现变动都会调用这个监听事件
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isFirst) {
                    contentHeight = mChildOfContent.getHeight();//兼容华为等机型
                    isFirst = false;
                }
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    /**
     * 重新调整跟布局的高度
     */
    private void possiblyResizeChildOfContent() {

        int usableHeightNow = computeUsableHeight();

        //当前可见高度和上一次可见高度不一致 布局变动
        if (usableHeightNow != usableHeightPrevious) {
            //int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;

            Intent intent = new Intent("ACTION_KEYBOARD_STATE");
            String state = "state";
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight;
                    intent.putExtra(state, "visible");
                } else {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                }
            } else {
                frameLayoutParams.height = contentHeight;
                intent.putExtra(state, "hidden");
            }

            activity.sendBroadcast(intent);

            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算mChildOfContent可见高度     ** @return
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    /**
     * 隐藏输入法键盘
     */
    public static void hideInputKeyboard(Activity activity) {
        if (activity == null) {
            throw new RuntimeException("activity is null");
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            IBinder ibinder = view.getWindowToken();
            if (ibinder != null) {
                imm.hideSoftInputFromWindow(ibinder, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 显示输入法键盘
     */
    public static void showInputKeyboard(Activity instance) {
        if (instance == null) {
            throw new RuntimeException("activity is null");
        }
        InputMethodManager imm = (InputMethodManager) instance.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null ) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
