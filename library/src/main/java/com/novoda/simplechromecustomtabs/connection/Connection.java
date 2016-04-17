package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

public interface Connection {

    void connectTo(@NonNull Activity activity);

    boolean isConnected();

    void mayLaunch(Uri url);

    Session getSession();

    void disconnectFrom(@NonNull Activity activity);

    boolean isDisconnected();

}
