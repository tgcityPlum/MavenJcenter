package com.tgcity.profession.crash.engine;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author tgcity
 */
public final class CrashStore {

    private volatile static CrashStore sInstance;

    private List<WeakReference<Activity>> mRunningActivities;

    private CrashStore() {
        mRunningActivities = new CopyOnWriteArrayList<>();
    }

    public static CrashStore getInstance() {
        if (sInstance == null) {
            synchronized (CrashStore.class) {
                if (sInstance == null) {
                    sInstance = new CrashStore();
                }
            }
        }
        return sInstance;
    }

    /**
     * 存储activity
     *
     * @param activity Activity
     */
    public void putActivity(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        mRunningActivities.add(weakReference);
    }

    /**
     * 是否activity已存在
     *
     * @param activity Activity
     * @return boolean
     */
    public boolean contains(Activity activity) {
        if (activity == null) {
            return false;
        }
        int size = mRunningActivities.size();
        for (int i = 0; i < size; i++) {
            WeakReference<Activity> refer = mRunningActivities.get(i);
            if (refer == null) {
                continue;
            }
            Activity tmp = refer.get();
            if (tmp == null) {
                continue;
            }
            if (activity == tmp) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除activity
     *
     * @param activity Activity
     */
    public void removeActivity(Activity activity) {
        for (WeakReference<Activity> activityWeakReference : mRunningActivities) {
            if (activityWeakReference == null){
                continue;
            }
            Activity tmpActivity = activityWeakReference.get();
            if (tmpActivity == null){
                continue;
            }
            if (tmpActivity == activity) {
                mRunningActivities.remove(activityWeakReference);
                break;
            }
        }
    }

}
