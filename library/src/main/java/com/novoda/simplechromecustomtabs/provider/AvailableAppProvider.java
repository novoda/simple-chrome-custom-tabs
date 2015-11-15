package com.novoda.simplechromecustomtabs.provider;

import android.support.annotation.NonNull;

public interface AvailableAppProvider {

    void findBestPackage(@NonNull SimpleChromeCustomTabsAvailableAppProvider.PackageFoundCallback packageFoundCallback);

}
