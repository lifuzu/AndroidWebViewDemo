package com.example.rlee.webviewdemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Provides empty implementation of methods in Application.ActivityLifecycleCallbacks so that clients can extend
 * this class and override only the methods they are interested in.
 */
public abstract class AbstractActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
