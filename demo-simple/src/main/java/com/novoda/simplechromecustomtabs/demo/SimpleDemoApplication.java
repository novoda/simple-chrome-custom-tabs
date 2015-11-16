package com.novoda.simplechromecustomtabs.demo;

import android.app.Application;

import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;

public class SimpleDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SimpleChromeCustomTabs.initialize(this);
    }
}
