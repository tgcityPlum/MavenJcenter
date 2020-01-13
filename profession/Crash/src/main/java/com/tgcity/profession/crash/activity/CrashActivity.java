package com.tgcity.profession.crash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tgcity.profession.crash.R;
import com.tgcity.utils.SystemUtils;

/**
 * @author tgcity
 */
public final class CrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cr_activity_crash);

        setToolbar();
    }

    /**
     * 设置Toolbar
     */
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(SystemUtils.getAppName(this));
    }

    /**
     * 重启app
     *
     * @param view View
     */
    public void onRestartApp(View view) {
        Intent launchIntent = getApplication().getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(launchIntent);
            overridePendingTransition(0, 0);
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        //销毁进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

}
