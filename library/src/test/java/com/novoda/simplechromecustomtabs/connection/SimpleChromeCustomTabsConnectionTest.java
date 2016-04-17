package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SimpleChromeCustomTabsConnectionTest {

    private static Uri ANY_URI = mock(Uri.class);

    @Mock
    private Binder mockBinder;
    @Mock
    private Activity mockActivity;
    @Mock
    private ConnectedClient mockConnectedClient;
    @Mock
    private Session mockSession;

    private SimpleChromeCustomTabsConnection simpleChromeCustomTabsConnection;

    @Before
    public void setUp() {
        initMocks(this);

        when(mockActivity.getApplicationContext()).thenReturn(Robolectric.application);
        when(mockConnectedClient.newSession()).thenReturn(mock(Session.class));

        simpleChromeCustomTabsConnection = new SimpleChromeCustomTabsConnection(mockBinder);
    }

    @Test
    public void connectToBindsApplicationContextToService() {
        simpleChromeCustomTabsConnection.connectTo(mockActivity);

        verify(mockBinder).bindCustomTabsServiceTo(mockActivity.getApplicationContext(), simpleChromeCustomTabsConnection);
    }

    @Test
    public void disconnectFromUnbindsApplicationContextFromService() {
        simpleChromeCustomTabsConnection.disconnectFrom(mockActivity);

        verify(mockBinder).unbindCustomTabsService(mockActivity.getApplicationContext());
    }

    @Test
    public void warmsUpConnectedClientOnServiceConnected() {
        givenAConnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);

        verify(mockConnectedClient).warmup();
    }

    @Test
    public void doesNotWarmUpDisconnectedClientOnServiceConnected() {
        givenADisconnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);

        verify(mockConnectedClient, never()).warmup();
    }

    @Test
    public void createsNewSessionWhenClientIsStillConnected() {
        givenAConnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);

        verify(mockConnectedClient).newSession();
    }

    @Test
    public void doesNotCreateNewSessionWhenClientIsDisconnected() {
        givenADisconnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);
        assertThat(simpleChromeCustomTabsConnection.getSession()).isEqualTo(Session.NULL_SESSION);

        verify(mockConnectedClient, never()).newSession();
    }

    @Test
    public void disconnectsConnectedClientOnServiceDisconnected() {
        givenAConnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);
        simpleChromeCustomTabsConnection.onServiceDisconnected();

        verify(mockConnectedClient).disconnect();
    }

    @Test
    public void doesNotDisconnectDisconnectedConnectedClientOnServiceDisconnected() {
        givenADisconnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);
        simpleChromeCustomTabsConnection.onServiceDisconnected();

        verify(mockConnectedClient, never()).disconnect();
    }

    @Test
    public void warmsUpFutureUrlForConnectedClientOnServiceConnected() {
        simpleChromeCustomTabsConnection.mayLaunch(ANY_URI);
        givenAConnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);

        verify(mockSession).mayLaunch(ANY_URI);
    }

    @Test
    public void warmsUpFutureUrlForConnectedClientOnMayLaunchUrl() {
        givenAConnectedClient();
        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);
        Mockito.reset(mockSession);

        simpleChromeCustomTabsConnection.mayLaunch(ANY_URI);

        verify(mockSession).mayLaunch(ANY_URI);
    }

    @Test
    public void doesNotWarmsUpFutureUrlIfItIsEmpty() {
        givenAConnectedClient();
        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);
        Mockito.reset(mockSession);

        simpleChromeCustomTabsConnection.mayLaunch(Uri.EMPTY);

        verifyZeroInteractions(mockSession);
    }

    @Test
    public void doesNotWarmsUpFutureUrlIfItIsNull() {
        givenAConnectedClient();
        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);
        Mockito.reset(mockSession);

        simpleChromeCustomTabsConnection.mayLaunch(null);

        verifyZeroInteractions(mockSession);
    }

    @Test
    public void doesNotWarmsUpFutureUrlForDisconnectedClient() {
        givenADisconnectedClient();

        simpleChromeCustomTabsConnection.mayLaunch(ANY_URI);

        verifyZeroInteractions(mockSession);
    }

    private void givenAConnectedClient() {
        when(mockConnectedClient.stillConnected()).thenReturn(true);
        when(mockConnectedClient.newSession()).thenReturn(mockSession);
    }

    private void givenADisconnectedClient() {
        when(mockConnectedClient.stillConnected()).thenReturn(false);
    }

}
