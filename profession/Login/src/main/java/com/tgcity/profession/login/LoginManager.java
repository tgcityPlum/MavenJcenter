package com.tgcity.profession.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tgcity.utils.DigitalUtils;
import com.tgcity.utils.StartActivityUtils;

/**
 * @author TGCity
 * @date 2020/1/16
 * @description 登录的管理类
 *
 * <pre>
 *     <code>
 *         //跳转登录页--不需要返回 type：0，4，8
 *         LoginManager.getInstance()
 *                     .setType(0)
 *                     .setContext(context)
 *                     .setLoginClassName(class)
 *                     .setCallBack(...)
 *                     .gotoLogin()
 *
 *         //跳转登录页--需要返回 type：1，5
 *         LoginManager.getInstance()
 *                     .setType(1)
 *                     .setActivity(activity)
 *                     .setLoginClassName(class)
 *                     .setCallBack(...)
 *                     .gotoLoginForResult(requestCode)
 *
 *         //登录成功--跳转其他界面 type：0，4，8
 *         LoginManager.getInstance()
 *                     .setContext(context)
 *                     .setGotoClassName(class)
 *                     .setCallBack(...)
 *                     .create()
 *
 *         //登录成功--返回之前界面 type：1，5
 *         LoginManager.getInstance()
 *                     .setActivity(activity)
 *                     .setCallBack(...)
 *                     .create()
 *
 *     </code>
 * </pre>
 */
public class LoginManager {
    /**
     * 跳转登录的类型
     * 1、token未null
     * 0 从启动页进入登录页
     * 1 允许游客浏览模式，当涉及用户信息时触发登录界面
     * 2、token失效
     * 4 在启动页调用校验token接口发现token失效
     * 5 调用某个接口发现用户登录失效
     * 3、退出登录
     * 8 点击退出登录按钮
     */
    private int type = DigitalUtils.LEVEL_0;

    /**
     * 传入的context
     */
    private Context context;

    /**
     * 传入的activity
     */
    private Activity activity;

    /**
     * 登录的类名
     */
    private Class<?> loginClassName;
    /**
     * 跳转的类名
     */
    private Class<?> gotoClassName;

    /**
     * 回调接口
     */
    private LoginAction loginAction;

    private static volatile LoginManager loginManager;

    private boolean isUseNewTaskFlag = false;

    /**
     * 初始化方法
     *
     * @return LoginManager
     */
    public static LoginManager getInstance() {

        if (loginManager == null) {
            synchronized (LoginManager.class) {
                if (loginManager == null) {
                    loginManager = new LoginManager();
                }
            }
        }

        return loginManager;
    }

    /**
     * 设置登录类型
     *
     * @param type int
     */
    public LoginManager setType(int type) {
        this.type = type;

        return loginManager;
    }

    /**
     * 处理方法
     */
    public void create() {
        //先判断是哪种情况
        if (type == DigitalUtils.LEVEL_0) {
            gotoLoginFromSplash();
        } else if (type == DigitalUtils.LEVEL_1) {
            gotoLoginFromUserInfo();
        } else if (type == DigitalUtils.LEVEL_4) {
            tokenInvalidFromSplash();
        } else if (type == DigitalUtils.LEVEL_5) {
            tokenInvalidFromUserInfo();
        } else if (type == DigitalUtils.LEVEL_8) {
            exitLogin();
        }

        if (loginAction != null) {
            loginAction.onFinish();
        }
    }

    /**
     * 点击退出登录按钮
     */
    private void exitLogin() {
        gotoClass();
    }

    /**
     * 调用某个接口发现用户登录失效
     */
    private void tokenInvalidFromUserInfo() {
        setResult();
    }

    /**
     * 在启动页调用校验token接口发现token失效
     */
    private void tokenInvalidFromSplash() {
        gotoClass();
    }

    /**
     * 允许游客浏览模式，当涉及用户信息时触发登录界面
     * 需求：跳转登录界面，登录成功后刷新原界面
     * 需要的数据：context和loginClassName
     */
    private void gotoLoginFromUserInfo() {
        setResult();
    }

    /**
     * 处理返回
     */
    private void setResult() {
        getActivity().setResult(Activity.RESULT_OK);
    }

    /**
     * 从启动页进入登录页
     * 需要的数据：跳转登录页需要的context和class
     * 结果：
     */
    private void gotoLoginFromSplash() {
        gotoClass();
    }

    /**
     * 跳转新界面
     */
    private void gotoClass() {
        StartActivityUtils startActivityUtils = StartActivityUtils.getInstance()
                .setContext(getContext())
                .setClass(getGotoClassName());

        if (isUseNewTaskFlag) {
            startActivityUtils.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivityUtils.startActivity();
    }

    private Class<?> getLoginClass() {
        return loginClassName;
    }

    /**
     * 设置登录类名称
     *
     * @param loginClassName Class
     * @return LoginManager
     */
    public LoginManager setLoginClassName(Class<?> loginClassName) {
        this.loginClassName = loginClassName;

        return this;
    }

    private Class<?> getGotoClassName() {
        return gotoClassName;
    }

    /**
     * 设置跳转的类
     *
     * @param gotoClassName Class
     * @return LoginManager
     */
    public LoginManager setGotoClassName(Class<?> gotoClassName) {
        this.gotoClassName = gotoClassName;

        return loginManager;
    }

    private Context getContext() {
        return context;
    }

    /**
     * 设置context
     *
     * @param context Context
     * @return LoginManager
     */
    public LoginManager setContext(Context context) {
        this.context = context;

        return loginManager;
    }

    private Activity getActivity() {
        return activity;
    }

    public LoginManager setActivity(Activity activity) {
        this.activity = activity;

        return loginManager;
    }

    /**
     * 设置完成回调
     *
     * @param loginAction LoginAction
     * @return LoginManager
     */
    public LoginManager setCallBack(LoginAction loginAction) {
        this.loginAction = loginAction;

        return loginManager;
    }

    /**
     * 跳转登录界面
     */
    public void gotoLogin() {

        StartActivityUtils startActivityUtils = StartActivityUtils.getInstance()
                .setContext(getContext())
                .setClass(getLoginClass());

        if (isUseNewTaskFlag) {
            startActivityUtils.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivityUtils.startActivity();

        if (loginAction != null) {
            loginAction.onFinish();
        }
    }

    /**
     * 跳转登录界面
     */
    public void gotoLoginForResult(int requestCode) {

        StartActivityUtils.getInstance()
                .setActivity(getActivity())
                .setClass(getLoginClass())
                .startActivityForResult(requestCode);

        if (loginAction != null) {
            loginAction.onFinish();
        }
    }

    public int getType() {
        return type;
    }

    public LoginManager setUseNewTaskFlag(boolean useNewTaskFlag) {
        isUseNewTaskFlag = useNewTaskFlag;
        return loginManager;
    }

}
