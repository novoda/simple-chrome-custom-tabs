package com.novoda.simplechromecustomtabs.provider;

import android.support.annotation.NonNull;

import com.novoda.notils.exception.DeveloperError;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface AvailableAppProvider {

    void findBestPackage(@NonNull SimpleChromeCustomTabsAvailableAppProvider.PackageFoundCallback packageFoundCallback);

    interface PackageFoundCallback {
        void onPackageFound(String packageName);

        void onPackageNotFound();
    }

    class Creator {

        private Creator() {
            throw new DeveloperError("Shouldn't be instantiated");
        }

        public static AvailableAppProvider create() {
            BestPackageFinder bestPackageFinder = BestPackageFinder.newInstance();
            Executor executor = Executors.newSingleThreadExecutor();
            return new SimpleChromeCustomTabsAvailableAppProvider(bestPackageFinder, executor);
        }
    }

}
