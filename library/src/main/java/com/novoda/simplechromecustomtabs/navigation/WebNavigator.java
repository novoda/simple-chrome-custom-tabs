package com.novoda.simplechromecustomtabs.navigation;

import android.app.Activity;
import android.net.Uri;

public interface WebNavigator {

    WebNavigator withFallback(NavigationFallback navigationFallback);

    WebNavigator withIntentCustomizer(IntentCustomizer intentCustomizer);

    void navigateTo(Uri url, Activity activityContext);

}
