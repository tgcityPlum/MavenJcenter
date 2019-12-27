package com.tgcity.function.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tgcity.function.baseactivity.R;
import com.tgcity.utils.LogUtils;

/**
 * @author TGCity
 * 基础的activity
 * --处理Activity的启动时间
 */
public abstract class BaseLauncherTimeActivity extends BaseFragmentNavigatorActivity {
    /**
     * time
     */
    private long currentTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        currentTime = System.currentTimeMillis();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            LogUtils.d(getString(R.string.base_memory_activity_current_page_name, getCurrentPage(), getLocalClassName(), transformTime(System.currentTimeMillis() - currentTime)));
        }
    }

    /**
     * 转换当前时间
     * --long 转换成 xx分xx秒xx毫秒
     */
    private String transformTime(long timeMillis) {
        String time;

        if (timeMillis >= 1000) {
            //大于或等于1s
            if (timeMillis >= 60000) {
                time = timeMillis / 60000 + "min";
            } else {
                time = timeMillis / 1000 + "s";
            }
        } else {
            time = timeMillis + "ms";
        }
        return "启动时间是" + time;
    }
}
