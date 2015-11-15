package com.novoda.simplechromecustomtabs.navigation;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;
import com.novoda.simplechromecustomtabs.connection.Connection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class SimpleChromeCustomTabsNavigatorTest {

    private static final Uri ANY_URL = Uri.EMPTY;
    private static final CustomTabsIntent ANY_INTENT = new CustomTabsIntent.Builder().build();

    @Mock
    private Connection mockConnection;
    @Mock
    private IntentCustomizer mockIntentCustomizer;
    @Mock
    private NavigationFallback mockNavigationFallback;
    @Mock
    private Activity mockActivity;
    @Mock
    private SimpleChromeCustomTabsIntentBuilder mockSimpleChromeCustomTabsIntentBuilder;

    private WebNavigator webNavigator;

    @Before
    public void setUp() {
        initMocks(this);

        SimpleChromeCustomTabs.initialize(Robolectric.application);
        when(mockIntentCustomizer.onCustomiseIntent(any(SimpleChromeCustomTabsIntentBuilder.class))).thenReturn(mockSimpleChromeCustomTabsIntentBuilder);
        when(mockSimpleChromeCustomTabsIntentBuilder.createIntent()).thenReturn(ANY_INTENT);

        webNavigator = new SimpleChromeCustomTabsWebNavigator(mockConnection);
    }

    @Test
    public void navigateToWillFallbackIfHasFallbackAndNotConnected() {
        when(mockConnection.isConnected()).thenReturn(false);
        webNavigator.withFallback(mockNavigationFallback);

        webNavigator.navigateTo(ANY_URL, mockActivity);

        verify(mockNavigationFallback).onFallbackNavigateTo(ANY_URL);
    }

    @Test
    public void navigateToDoesNothingIfHasNotFallbackAndNotConnected() {
        when(mockConnection.isConnected()).thenReturn(false);

        webNavigator.navigateTo(ANY_URL, mockActivity);

        verifyZeroInteractions(mockNavigationFallback);
    }

    @Test
    public void intentBuilderIsCustomizedIfConnectedAndHasCustomizer() {
        when(mockConnection.isConnected()).thenReturn(true);
        webNavigator.withIntentCustomizer(mockIntentCustomizer);

        webNavigator.navigateTo(ANY_URL, mockActivity);

        verify(mockIntentCustomizer).onCustomiseIntent(any(SimpleChromeCustomTabsIntentBuilder.class));
    }

}
