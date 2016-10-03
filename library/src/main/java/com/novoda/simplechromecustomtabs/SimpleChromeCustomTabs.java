package com.novoda.simplechromecustomtabs;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsSession;

import com.novoda.notils.exception.DeveloperError;
import com.novoda.simplechromecustomtabs.connection.Connection;
import com.novoda.simplechromecustomtabs.connection.Session;
import com.novoda.simplechromecustomtabs.connection.SimpleChromeCustomTabsConnection;
import com.novoda.simplechromecustomtabs.navigation.IntentCustomizer;
import com.novoda.simplechromecustomtabs.navigation.NavigationFallback;
import com.novoda.simplechromecustomtabs.navigation.SimpleChromeCustomTabsIntentBuilder;
import com.novoda.simplechromecustomtabs.navigation.SimpleChromeCustomTabsWebNavigator;
import com.novoda.simplechromecustomtabs.navigation.WebNavigator;
import com.novoda.simplechromecustomtabs.provider.AvailableAppProvider;
import com.novoda.simplechromecustomtabs.provider.SimpleChromeCustomTabsAvailableAppProvider;

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
        LazyHolder.INSTANCE.connection = SimpleChromeCustomTabsConnection.newInstance();
        LazyHolder.INSTANCE.webNavigator = SimpleChromeCustomTabsWebNavigator.newInstance();
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
     * {@link SimpleChromeCustomTabsIntentBuilder}
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

    @Override
    public void releaseCallbacks() {
        webNavigator.releaseCallbacks();
    }

    /**
     * Connects given activity to {@link android.support.customtabs.CustomTabsService}
     *
     * @param activity
     */
    @Override
    public void connectTo(@NonNull Activity activity) {
        if (isDisconnected()) {
            connection.connectTo(activity);
        }
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected();
    }

    /**
     * Tells SimpleChromeCustomTabs that a potential Url might be launched. This will do pre DNS resolution that will speed things up
     * but it will as well require network usage which can affect batter performance.
     *
     * @param uri
     */
    @Override
    public void mayLaunch(Uri uri) {
        connection.mayLaunch(uri);
    }

    /**
     * Get current active session for Chrome Custom Tabs usage. Can be used to warmup particular Urls.
     * {@see {@link CustomTabsSession#mayLaunchUrl(Uri, Bundle, List)}}
     *
     * @return a new {@link CustomTabsSession} or null if not connected to service.
     */
    @Override
    public Session getSession() {
        return connection.getSession();
    }

    @Override
    public void disconnectFrom(@NonNull Activity activity) {
        if (isConnected()) {
            connection.disconnectFrom(activity);
        }
        releaseCallbacks();
    }

    @Override
    public boolean isDisconnected() {
        return !isConnected();
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
