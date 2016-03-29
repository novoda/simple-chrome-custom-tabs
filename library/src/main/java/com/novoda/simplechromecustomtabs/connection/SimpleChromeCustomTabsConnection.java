package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsSession;

import java.util.List;

/**
 * TODO Open implementation to allow {@link CustomTabsSession#mayLaunchUrl(Uri, Bundle, List)}
 */
public class SimpleChromeCustomTabsConnection implements Connection, ServiceConnectionCallback {

    private static final CustomTabsSession NULL_SESSION = null;

    private final Binder binder;

    private ConnectedClient client;

    SimpleChromeCustomTabsConnection(Binder binder) {
        this.binder = binder;
    }

    public static SimpleChromeCustomTabsConnection newInstance() {
        Binder binder = Binder.newInstance();
        return new SimpleChromeCustomTabsConnection(binder);
    }

    @Override
    public void connectTo(@NonNull Activity activity) {
        binder.bindCustomTabsServiceTo(activity, this);
    }

    @Override
    public void onServiceConnected(ConnectedClient client) {
        this.client = client;

        if (hasConnectedClient()) {
            this.client.warmup();
        }
    }

    @Override
    public boolean isConnected() {
        return hasConnectedClient();
    }

    @Override
    @Nullable
    public CustomTabsSession newSession() {
        if (hasConnectedClient()) {
            return client.newSession();
        }

        return NULL_SESSION;
    }

    private boolean hasConnectedClient() {
        return client != null && client.stillConnected();
    }

    @Override
    public void disconnectFrom(@NonNull Activity activity) {
        binder.unbindCustomTabsService(activity);
    }

    @Override
    public boolean isDisconnected() {
        return !isConnected();
    }

    @Override
    public void onServiceDisconnected() {
        if (hasConnectedClient()) {
            client.disconnect();
        }
    }

}
