package com.tgcity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.tgcity.widget.verificationcode.R;

/**
 * @author TGCity
 * @date 2020/1/7
 * @describe 验证码组件
 * <pre>
 *     <code>
 *         vc_un_click_msg : 初始化的文案
 *         vc_un_click_col : 初始化的颜色
 *         vc_click_msg    : 倒计时的文案
 *         vc_click_col    : 倒计时的颜色
 *         vc_duration     : 倒计时的时长
 *     </code>
 * </pre>
 */
public class VerificationCodeView extends AppCompatTextView {

    /**
     * 按钮初始化的文案
     */
    private String unClickMsg;
    /**
     * 按钮初始化的颜色
     */
    private int unClickColor;
    /**
     * 按钮倒计时的文案
     */
    private String clickMsg;
    /**
     * 按钮倒计时的颜色
     */
    private int clickColor;
    /**
     * 倒计时的时长，单位为秒，暂定为60s
     */
    private int duration = 60;
    /**
     * 组件的状态
     * 0 未点击状态； 1 点击状态
     */
    private int status = 0;
    /**
     * 监听回调
     */
    private CountdownInterface countdownInterface;

    private CountDownTimer countDownTimer = new CountDownTimer(duration * 1000 + 100, 1000) {

        @Override
        public void onTick(long l) {

            Log.e("TGCity", "onTick:" + l);
            setClickable(false);
            if (status != 1) {
                status = 1;
            }
            setClickMsgAndColor(l);
        }

        @Override
        public void onFinish() {
            setClickable(true);
            status = 0;
            setUnClickMsgAndColor();
            if (countdownInterface != null) {
                countdownInterface.onCountdownFinish();
            }
        }
    };

    public VerificationCodeView(Context context) {
        this(context, null);
    }

    public VerificationCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.vc_verification_code);

        unClickMsg = TextUtils.isEmpty(typedArray.getString(R.styleable.vc_verification_code_vc_un_click_msg)) ? getResources().getString(R.string.vc_un_click_msg) : typedArray.getString(R.styleable.vc_verification_code_vc_un_click_msg);
        unClickColor = typedArray.getColor(R.styleable.vc_verification_code_vc_un_click_col, getResources().getColor(R.color.vc_un_click));
        clickMsg = TextUtils.isEmpty(typedArray.getString(R.styleable.vc_verification_code_vc_click_msg)) ? getResources().getString(R.string.vc_click_msg) : typedArray.getString(R.styleable.vc_verification_code_vc_click_msg);
        clickColor = typedArray.getColor(R.styleable.vc_verification_code_vc_click_col, getResources().getColor(R.color.vc_click));
        duration = typedArray.getInteger(R.styleable.vc_verification_code_vc_duration, duration);

        setUnClickMsgAndColor();
    }

    /**
     * 设置组件初始的文案和颜色
     */
    private void setUnClickMsgAndColor() {
        setText(unClickMsg);
        setTextColor(unClickColor);
    }

    /**
     * 设置组件点击后的文案和颜色
     */
    private void setClickMsgAndColor(long time) {
        setText((time / 1000) + clickMsg);
        setTextColor(clickColor);
    }

    /**
     * 获取组件状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置组件状态
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 设置倒计时时长
     */
    public void setDuration(int duration) {
        if (this.duration != duration) {
            this.duration = duration;
            countDownTimer.setMillisInFuture(duration * 1000);
        }
        //暂停倒计时，设置时长
        countDownTimer.cancel();
        countDownTimer.onTick(duration * 1000);
    }

    /**
     * 启动倒计时
     */
    public void startCountdown() {
        if (duration != 60) {
            countDownTimer.setMillisInFuture(duration * 1000 + 100);
        }
        countDownTimer.start();
    }

    /**
     * 结束倒计时的方法
     */
    private void stopCountdown() {
        countDownTimer.cancel();
    }

    /**
     * 设置监听
     */
    public void setListener(CountdownInterface countdownInterface) {
        this.countdownInterface = countdownInterface;
    }

    /**
     * 回调接口
     */
    public interface CountdownInterface {
        /**
         * 倒计时结束
         */
        void onCountdownFinish();
    }

    /**
     * 回收对象
     */
    public void onDestroy() {
        //回收监听对象
        if (countdownInterface != null) {
            countdownInterface = null;
        }
        //结束倒计时
        stopCountdown();
    }
}
