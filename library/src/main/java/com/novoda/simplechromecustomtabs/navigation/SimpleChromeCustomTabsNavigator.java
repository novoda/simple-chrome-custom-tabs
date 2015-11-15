package com.novoda.simplechromecustomtabs.navigation;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;
import com.novoda.simplechromecustomtabs.connection.Connection;

public class SimpleChromeCustomTabsNavigator implements Navigator {

    private final Connection connection;
    private NavigationFallback navigationFallback;
    private IntentCustomizer intentCustomizer;

    SimpleChromeCustomTabsNavigator(Connection connection) {
        this.connection = connection;
    }

    public static SimpleChromeCustomTabsNavigator newInstance() {
        Connection connection = SimpleChromeCustomTabs.getInstance();
        return new SimpleChromeCustomTabsNavigator(connection);
    }

    public SimpleChromeCustomTabsNavigator withFallback(NavigationFallback navigationFallback) {
        this.navigationFallback = navigationFallback;
        return this;
    }

    public SimpleChromeCustomTabsNavigator withIntentCustomizer(IntentCustomizer intentCustomizer) {
        this.intentCustomizer = intentCustomizer;
        return this;
    }

    public void navigateTo(Uri url, Activity activityContext) {
        if (connection.isConnected()) {
            buildIntent().launchUrl(activityContext, url);
        } else if (hasNavigationFallback()) {
            navigationFallback.onFallbackNavigateTo(url);
        }
    }

    private CustomTabsIntent buildIntent() {
        SimpleChromeCustomTabsIntentBuilder basicIntentBuilder = SimpleChromeCustomTabsIntentBuilder.newInstance();

        if (intentCustomizer == null) {
            return basicIntentBuilder.createIntent();
        }

        return intentCustomizer.onCustomiseIntent(basicIntentBuilder).createIntent();
    }

    private boolean hasNavigationFallback() {
        return navigationFallback != null;
    }

}
