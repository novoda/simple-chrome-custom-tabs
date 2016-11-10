package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.novoda.notils.exception.DeveloperError;

public interface Connection {

    void connectTo(@NonNull Activity activity);

    boolean isConnected();

    void mayLaunch(Uri uri);

    Session getSession();

    void disconnectFrom(@NonNull Activity activity);

    boolean isDisconnected();

    class Creator {

        private Creator() {
            throw new DeveloperError("Shouldn't be instantiated");
        }

        public static Connection create() {
            Binder binder = Binder.newInstance();
            return new SimpleChromeCustomTabsConnection(binder);
        }

    }

}
