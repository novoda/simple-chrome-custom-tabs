package com.novoda.simplechromecustomtabs.navigation;

import android.content.Context;
import android.graphics.Bitmap;

import com.novoda.simplechromecustomtabs.connection.SimpleChromeCustomTabsConnection;
import com.novoda.notils.exception.DeveloperError;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class SimpleChromeCustomTabsIntentBuilderTest {

    private static final int ANY_ANIM_RES = 0;
    private static final int ANY_COLOR = 0;
    private static final android.app.PendingIntent ANY_PENDING_INTENT = null;
    private static final String ANY_LABEL = "anyLabel";
    private static final String ANY_DESCRIPTION = "any_description";
    private static final Bitmap ANY_ICON = null;
    private static final Context ANY_CONTEXT = Robolectric.application;

    @Mock
    private SimpleChromeCustomTabsConnection mockSimpleChromeCustomTabsConnection;
    @Mock
    private List<Composer> mockComposers;

    private SimpleChromeCustomTabsIntentBuilder simpleChromeCustomTabsIntentBuilder;

    @Before
    public void setUp() {
        initMocks(this);

        simpleChromeCustomTabsIntentBuilder = new SimpleChromeCustomTabsIntentBuilder(mockSimpleChromeCustomTabsConnection, mockComposers);
        when(mockComposers.iterator()).thenReturn(Collections.<Composer>emptyListIterator());
    }

    @Test(expected = DeveloperError.class)
    public void throwsDeveloperErrorIfCreatingIntentWithNoConnection() {
        givenIsDisconnected();

        simpleChromeCustomTabsIntentBuilder.createIntent();
    }

    @Test
    public void requestsNewSessionOnCustomTabsConnectionIfConnected() {
        givenIsConnected();

        simpleChromeCustomTabsIntentBuilder.createIntent();

        verify(mockSimpleChromeCustomTabsConnection).newSession();
    }

    @Test
    public void returnsIntentIfConnected() {
        givenIsConnected();

        assertThat(simpleChromeCustomTabsIntentBuilder.createIntent()).isNotNull();
    }

    @Test
    public void withToolbarColorAddsToolbarColorComposer() {
        simpleChromeCustomTabsIntentBuilder.withToolbarColor(ANY_COLOR);

        verify(mockComposers).add(any(ToolbarColorComposer.class));
    }

    @Test
    public void withUrlBarHidingAddsUrlBarHidingComposer() {
        simpleChromeCustomTabsIntentBuilder.withUrlBarHiding();

        verify(mockComposers).add(any(UrlBarHidingComposer.class));
    }

    @Test
    public void withMenuItemAddsMenuItemComposer() {
        simpleChromeCustomTabsIntentBuilder.withMenuItem(ANY_LABEL, ANY_PENDING_INTENT);

        verify(mockComposers).add(any(MenuItemComposer.class));
    }

    @Test
    public void withActionButtonAddsActionButtonComposer() {
        simpleChromeCustomTabsIntentBuilder.withActionButton(ANY_ICON, ANY_DESCRIPTION, ANY_PENDING_INTENT, false);

        verify(mockComposers).add(any(ActionButtonComposer.class));
    }

    @Test
    public void withCloseButtonAddsCloseButtonComposer() {
        simpleChromeCustomTabsIntentBuilder.withCloseButtonIcon(ANY_ICON);

        verify(mockComposers).add(any(CloseButtonIconComposer.class));
    }

    @Test
    public void withExitAnimationsAddsExitAnimationsComposer() {
        simpleChromeCustomTabsIntentBuilder.withExitAnimations(ANY_CONTEXT, ANY_ANIM_RES, ANY_ANIM_RES);

        verify(mockComposers).add(any(ExitAnimationsComposer.class));
    }

    @Test
    public void withStartAnimationsAddsStartAnimationsComposer() {
        simpleChromeCustomTabsIntentBuilder.withStartAnimations(ANY_CONTEXT, ANY_ANIM_RES, ANY_ANIM_RES);

        verify(mockComposers).add(any(StartAnimationsComposer.class));
    }

    @Test
    public void showingTitleAddsShowingTitleComposer() {
        simpleChromeCustomTabsIntentBuilder.showingTitle();

        verify(mockComposers).add(any(ShowTitleComposer.class));
    }

    private void givenIsDisconnected() {
        when(mockSimpleChromeCustomTabsConnection.isConnected()).thenReturn(false);
    }

    private void givenIsConnected() {
        when(mockSimpleChromeCustomTabsConnection.isConnected()).thenReturn(true);
    }

}
