package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SimpleChromeCustomTabsConnectionTest {

    @Mock
    private Binder mockBinder;
    @Mock
    private Activity mockActivity;
    @Mock
    private ConnectedClient mockConnectedClient;

    private SimpleChromeCustomTabsConnection simpleChromeCustomTabsConnection;

    @Before
    public void setUp() {
        initMocks(this);

        simpleChromeCustomTabsConnection = new SimpleChromeCustomTabsConnection(mockBinder);
    }

    @Test
    public void connectToBindsActivityToService() {
        simpleChromeCustomTabsConnection.connectTo(mockActivity);

        verify(mockBinder).bindCustomTabsServiceTo(mockActivity);
    }

    @Test
    public void disconnectFromUnbindsActivityFromService() {
        simpleChromeCustomTabsConnection.disconnectFrom(mockActivity);

        verify(mockBinder).unbindCustomTabsService(mockActivity);
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
        simpleChromeCustomTabsConnection.newSession();

        verify(mockConnectedClient).newSession();
    }

    @Test
    public void doesNotCreateNewSessionWhenClientIsDisconnected() {
        givenADisconnectedClient();

        simpleChromeCustomTabsConnection.onServiceConnected(mockConnectedClient);
        assertThat(simpleChromeCustomTabsConnection.newSession()).isNull();

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

    private void givenAConnectedClient() {
        when(mockConnectedClient.stillConnected()).thenReturn(true);
    }

    private void givenADisconnectedClient() {
        when(mockConnectedClient.stillConnected()).thenReturn(false);
    }

}
