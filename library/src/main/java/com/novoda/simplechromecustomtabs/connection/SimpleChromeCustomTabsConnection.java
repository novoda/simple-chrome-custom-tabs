package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

public class SimpleChromeCustomTabsConnection implements Connection, ServiceConnectionCallback {

    private final Binder binder;

    private ConnectedClient client;
    private Session session = Session.NULL_SESSION;
    private Uri pendingUrlToWarmUp;

    SimpleChromeCustomTabsConnection(Binder binder) {
        this.binder = binder;
    }

    public static Connection newInstance() {
        Binder binder = Binder.newInstance();
        return new SimpleChromeCustomTabsConnection(binder);
    }

    @Override
    public void connectTo(@NonNull Activity activity) {
        binder.bindCustomTabsServiceTo(activity.getApplicationContext(), this);
    }

    @Override
    public void onServiceConnected(ConnectedClient client) {
        this.client = client;

        if (hasConnectedClient()) {
            session = client.newSession();
            warmUpPendingUrl();
        }
    }

    private void warmUpPendingUrl() {
        if (isEmpty(pendingUrlToWarmUp)) {
            return;
        }

        session.mayLaunch(pendingUrlToWarmUp);
        pendingUrlToWarmUp = Uri.EMPTY;
    }

    private boolean hasConnectedClient() {
        return client != null && client.stillConnected();
    }

    @Override
    public boolean isConnected() {
        return hasConnectedClient();
    }

    @Override
    public void mayLaunch(Uri url) {
        if (isEmpty(url)) {
            return;
        }

        if (isConnected()) {
            session.mayLaunch(url);
        } else {
            pendingUrlToWarmUp = url;
        }
    }

    private boolean isEmpty(Uri url) {
        return url == null || url.equals(Uri.EMPTY) ;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void disconnectFrom(@NonNull Activity activity) {
        binder.unbindCustomTabsService(activity.getApplicationContext());
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
