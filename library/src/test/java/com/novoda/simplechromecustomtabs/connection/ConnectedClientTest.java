package com.novoda.simplechromecustomtabs.connection;

import android.support.customtabs.CustomTabsClient;

import com.novoda.notils.exception.DeveloperError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConnectedClientTest {

    @Mock
    private CustomTabsClient mockCustomTabsClient;

    private ConnectedClient connectedClient;

    @Before
    public void setUp() {
        initMocks(this);
        connectedClient = new ConnectedClient(mockCustomTabsClient);
    }

    @Test(expected = DeveloperError.class)
    public void newSessionThrowsDeveloperErrorIfNotConnected() {
        connectedClient.disconnect();

        connectedClient.newSession();

        verifyZeroInteractions(mockCustomTabsClient);
    }

    @Test
    public void newSessionDelegatesIfConnected() {
        connectedClient.newSession();

        verify(mockCustomTabsClient).newSession(null);
    }

    @Test
    public void stillConnectedReturnsTrueIfConnected() {
        assertThat(connectedClient.stillConnected()).isTrue();
    }

    @Test
    public void stillConnectedReturnsFalseIfNotConnected() {
        connectedClient.disconnect();

        assertThat(connectedClient.stillConnected()).isFalse();
    }

    @Test
    public void newSessionWarmsUpClientUpIfConnected() {
        connectedClient.newSession();

        verify(mockCustomTabsClient).warmup(0);
    }

    @Test(expected = DeveloperError.class)
    public void newSessionDoesNotWarmUpClientUpIfDisconnected() {
        connectedClient.disconnect();

        connectedClient.newSession();

        verify(mockCustomTabsClient, never()).warmup(anyInt());
    }

}
