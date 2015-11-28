package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;
import android.content.ComponentName;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;

import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;
import com.novoda.simplechromecustomtabs.provider.AvailableAppProvider;
import com.novoda.simplechromecustomtabs.provider.SimpleChromeCustomTabsAvailableAppProvider;

class Binder {

    private final AvailableAppProvider availableAppProvider;
    private ServiceConnection serviceConnection;

    Binder(@NonNull AvailableAppProvider availableAppProvider) {
        this.availableAppProvider = availableAppProvider;
    }

    public static Binder newInstance() {
        AvailableAppProvider availableAppProvider = SimpleChromeCustomTabs.getInstance();
        return new Binder(availableAppProvider);
    }

    public void bindCustomTabsServiceTo(@NonNull final Activity activity, ServiceConnectionCallback callback) {
        if (isConnected()) {
            return;
        }

        serviceConnection = new ServiceConnection(callback);
        availableAppProvider.findBestPackage(
                new SimpleChromeCustomTabsAvailableAppProvider.PackageFoundCallback() {
                    @Override
                    public void onPackageFound(String packageName) {
                        CustomTabsClient.bindCustomTabsService(activity, packageName, serviceConnection);
                    }

                    @Override
                    public void onPackageNotFound() {
                        serviceConnection = null;
                    }
                }
        );
    }

    private boolean isConnected() {
        return serviceConnection != null;
    }

    public void unbindCustomTabsService(@NonNull Activity activity) {
        if (isDisconnected()) {
            return;
        }

        activity.unbindService(serviceConnection);
        serviceConnection.onServiceDisconnected(null);
        serviceConnection = null;
    }

    private boolean isDisconnected() {
        return !isConnected();
    }

    private static class ServiceConnection extends CustomTabsServiceConnection {
        private final ServiceConnectionCallback serviceConnectionCallback;

        public ServiceConnection(ServiceConnectionCallback connectionCallback) {
            serviceConnectionCallback = connectionCallback;
        }

        @Override
        public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
            if (hasServiceConnectionCallback()) {
                ConnectedClient connectedClient = new ConnectedClient(client);
                serviceConnectionCallback.onServiceConnected(connectedClient);
            }
        }

        private boolean hasServiceConnectionCallback() {
            return serviceConnectionCallback != null;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (hasServiceConnectionCallback()) {
                serviceConnectionCallback.onServiceDisconnected();
            }
        }
    }

}
