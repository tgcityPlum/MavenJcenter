package com.tgcity.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

/**
 * @author TGCity
 * @date 2019/12/11
 * @describe 跳转activity工具类
 *
 * <pre><code>
 *     StartActivityUtils.getInstance()
 *         .setContext(context)
 *         .setClass(XXX.class)
 *         .putExtra(name, value)
 *         .startActivity();
 * </code></pre>
 */
public class StartActivityUtils {
    /**
     * 全局静态变量
     */
    @SuppressLint("StaticFieldLeak")
    private volatile static StartActivityUtils activityUtils = null;
    /**
     * Context
     */
    private Context context;
    private Activity activity;
    private Fragment fragment;

    /**
     * Intent
     */
    private Intent intent;

    /**
     * 创建或使用对象
     *
     * @return StartActivityUtils
     */
    public static StartActivityUtils getInstance() {

        if (activityUtils == null) {
            synchronized (StartActivityUtils.class) {
                if (activityUtils == null) {
                    activityUtils = new StartActivityUtils();
                }
            }
        }
        return activityUtils;
    }

    /**
     * 设置 Context
     *
     * @param fragment fragment
     */
    public StartActivityUtils setFragment(Fragment fragment) {
        this.fragment = fragment;
        if (fragment != null){
            context = fragment.getContext();
        }
        return this;
    }

    /**
     * 设置 Context
     *
     * @param context Context
     */
    public StartActivityUtils setContext(Context context) {
        this.context = context;
        return this;
    }

    /**
     * 设置 Activity
     *
     * @param activity Activity
     */
    public StartActivityUtils setActivity(Activity activity) {
        this.activity = activity;
        if (activity!= null){
            this.context = activity.getBaseContext();
        }
        return this;
    }

    /**
     * 设置 Class
     *
     * @param cls Class
     * @return StartActivityUtils
     */
    public StartActivityUtils setClass(Class<?> cls) {
        intent = new Intent(context, cls);
        return this;
    }

    /**
     * 设置 Class
     *
     * @param className Class
     * @return StartActivityUtils
     */
    public StartActivityUtils setClassName(String className) {
        intent = new Intent();
        intent.setClassName(context,className);
        return this;
    }

    /**
     * 设置 Flag
     *
     * @param flags int
     * @return StartActivityUtils
     */
    public StartActivityUtils setFlags(int flags) {
        if (intent != null) {
            intent.setFlags(flags);
        } else {
            throw new RuntimeException("intent is null");
        }
        return this;
    }

    /**
     * 设置传参
     *
     * @param name  String
     * @param value String
     * @return StartActivityUtils
     */
    public StartActivityUtils putExtra(String name, String value) {
        if (intent != null) {
            intent.putExtra(name, value);
        } else {
            throw new RuntimeException("intent is null");
        }
        return this;
    }

    public StartActivityUtils putExtra(String name, int value) {
        if (intent != null) {
            intent.putExtra(name, value);
        } else {
            throw new RuntimeException("intent is null");
        }
        return this;
    }

    public StartActivityUtils putExtra(String name, Parcelable value) {
        if (intent != null) {
            intent.putExtra(name, value);
        } else {
            throw new RuntimeException("intent is null");
        }
        return this;
    }

    public StartActivityUtils putExtra(String name, Serializable value) {
        if (intent != null) {
            intent.putExtra(name, value);
        } else {
            throw new RuntimeException("intent is null");
        }
        return this;
    }

    /**
     * 开启跳转
     */
    public void startActivity() {
        if (context != null) {
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                throw new RuntimeException("intent is null");
            }
        } else {
            throw new RuntimeException("context is null");
        }
    }

    public void startActivityForResult(int requestCode) {
        if (activity != null) {
            if (intent != null) {
                activity.startActivityForResult(intent, requestCode);
            } else {
                throw new RuntimeException("intent is null");
            }
        } else {
            throw new RuntimeException("activity is null");
        }
    }

    public void startFragmentForResult(int requestCode) {
        if (fragment != null) {
            if (intent != null) {
                fragment.startActivityForResult(intent, requestCode);
            } else {
                throw new RuntimeException("intent is null");
            }
        } else {
            throw new RuntimeException("context is null");
        }
    }

}
