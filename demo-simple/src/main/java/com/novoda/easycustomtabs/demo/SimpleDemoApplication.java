package com.novoda.easycustomtabs.demo;

import android.app.Application;

import com.novoda.easycustomtabs.SimpleChromeCustomTabs;

public class SimpleDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SimpleChromeCustomTabs.initialize(this);
    }
}
