package com.novoda.simplechromecustomtabs;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsSession;

import com.novoda.simplechromecustomtabs.connection.Connection;
import com.novoda.simplechromecustomtabs.connection.EasyCustomTabsConnection;
import com.novoda.simplechromecustomtabs.navigation.EasyCustomTabsWebNavigator;
import com.novoda.simplechromecustomtabs.navigation.IntentCustomizer;
import com.novoda.simplechromecustomtabs.navigation.NavigationFallback;
import com.novoda.simplechromecustomtabs.navigation.WebNavigator;
import com.novoda.simplechromecustomtabs.provider.AvailableAppProvider;
import com.novoda.simplechromecustomtabs.provider.SimpleChromeCustomTabsAvailableAppProvider;
import com.novoda.notils.exception.DeveloperError;

import java.util.List;

public final class SimpleChromeCustomTabs implements WebNavigator, Connection, AvailableAppProvider {

    private static Context applicationContext;
    private Connection connection;
    private WebNavigator webNavigator;
    private AvailableAppProvider availableAppProvider;

    private SimpleChromeCustomTabs() {
        //no-op
    }

    private static class LazyHolder {
        private static final SimpleChromeCustomTabs INSTANCE = new SimpleChromeCustomTabs();
    }

    public static SimpleChromeCustomTabs getInstance() {
        if (applicationContext == null) {
            throw new DeveloperError("SimpleChromeCustomTabs must be initialized. Use SimpleChromeCustomTabs.initialize(context)");
        }

        return LazyHolder.INSTANCE;
    }

    public static void initialize(Context context) {
        applicationContext = context.getApplicationContext();
        LazyHolder.INSTANCE.connection = EasyCustomTabsConnection.newInstance();
        LazyHolder.INSTANCE.webNavigator = EasyCustomTabsWebNavigator.newInstance();
        LazyHolder.INSTANCE.availableAppProvider = SimpleChromeCustomTabsAvailableAppProvider.newInstance();
    }

    public Context getContext() {
        return applicationContext;
    }

    /**
     * Provides a {@link NavigationFallback} to specify navigation mechanism in case of no Chrome Custom Tabs support found.
     *
     * @param navigationFallback
     * @return WebNavigator with navigation fallback.
     */
    @Override
    public WebNavigator withFallback(NavigationFallback navigationFallback) {
        return webNavigator.withFallback(navigationFallback);
    }

    /**
     * Provides a {@link IntentCustomizer} to be used to customize the Chrome Custom Tabs by attacking directly to
     * {@link com.novoda.simplechromecustomtabs.navigation.EasyCustomTabsIntentBuilder}
     *
     * @param intentCustomizer
     * @return WebNavigator with customized Chrome Custom Tabs.
     */
    @Override
    public WebNavigator withIntentCustomizer(IntentCustomizer intentCustomizer) {
        return webNavigator.withIntentCustomizer(intentCustomizer);
    }

    /**
     * Navigates to the given url using Chrome Custom Tabs if available.
     * If there is no application supporting Chrome Custom Tabs and {@link NavigationFallback}
     * is provided it will be used to redirect navigation.
     *
     * @param url
     * @param activityContext
     */
    @Override
    public void navigateTo(Uri url, Activity activityContext) {
        webNavigator.navigateTo(url, activityContext);
    }

    /**
     * Connects given activity to {@link android.support.customtabs.CustomTabsService}
     *
     * @param activity
     */
    @Override
    public void connectTo(@NonNull Activity activity) {
        connection.connectTo(activity);
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected();
    }

    /**
     * Starts a new session for Chrome Custom Tabs usage. Can be used to warmup particular Urls.
     * {@see {@link CustomTabsSession#mayLaunchUrl(Uri, Bundle, List)}}
     *
     * @return a new {@link CustomTabsSession} or null if not connected to service.
     */
    @Override
    @Nullable
    public CustomTabsSession newSession() {
        return connection.newSession();
    }

    @Override
    public void disconnectFrom(@NonNull Activity activity) {
        connection.disconnectFrom(activity);
    }

    /**
     * Asynchronous search for the best package with support for Chrome Custom Tabs.
     *
     * @param packageFoundCallback
     */
    @Override
    public void findBestPackage(@NonNull SimpleChromeCustomTabsAvailableAppProvider.PackageFoundCallback packageFoundCallback) {
        availableAppProvider.findBestPackage(packageFoundCallback);
    }
}
