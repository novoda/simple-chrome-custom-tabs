package com.novoda.simplechromecustomtabs.provider;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class SimpleChromeCustomTabsAvailableAppProviderTest {

    private static final String NON_EMPTY_PACKAGE = "non.empty.package";

    @Mock
    private BestPackageFinder mockBestPackageFinder;
    @Mock
    private SimpleChromeCustomTabsAvailableAppProvider.PackageFoundCallback mockPackageFoundCallback;

    private SimpleChromeCustomTabsAvailableAppProvider simpleChromeCustomTabsAvailableAppProvider;

    @Before
    public void setUp() {
        initMocks(this);

        simpleChromeCustomTabsAvailableAppProvider = new SimpleChromeCustomTabsAvailableAppProvider(mockBestPackageFinder, new StubExecutor());
    }

    @Test
    public void findBestPackageDelegatesToPackageFinder() {
        simpleChromeCustomTabsAvailableAppProvider.findBestPackage(mockPackageFoundCallback);

        verify(mockBestPackageFinder).findBestPackage();
    }

    @Test
    public void packageIsFoundIfNotNullOrEmpty() {
        when(mockBestPackageFinder.findBestPackage()).thenReturn(NON_EMPTY_PACKAGE);

        simpleChromeCustomTabsAvailableAppProvider.findBestPackage(mockPackageFoundCallback);

        verify(mockPackageFoundCallback).onPackageFound(NON_EMPTY_PACKAGE);
    }

    @Test
    public void packageIsNotFoundIfNull() {
        when(mockBestPackageFinder.findBestPackage()).thenReturn(null);

        simpleChromeCustomTabsAvailableAppProvider.findBestPackage(mockPackageFoundCallback);

        verify(mockPackageFoundCallback).onPackageNotFound();
    }

    @Test
    public void packageNotFoundIfEmpty() {
        when(mockBestPackageFinder.findBestPackage()).thenReturn("");

        simpleChromeCustomTabsAvailableAppProvider.findBestPackage(mockPackageFoundCallback);

        verify(mockPackageFoundCallback).onPackageNotFound();
    }

    @Test
    public void ifPackageIsFoundThenPackageNotFoundWillNotBeCalled() {
        givenThatPackageIsFound();

        simpleChromeCustomTabsAvailableAppProvider.findBestPackage(mockPackageFoundCallback);

        verify(mockPackageFoundCallback, never()).onPackageNotFound();
    }

    @Test
    public void ifPackageIsNotFoundThenPackageFoundWillNotBeCalled() {
        givenThatPackageIsNotFound();

        simpleChromeCustomTabsAvailableAppProvider.findBestPackage(mockPackageFoundCallback);

        verify(mockPackageFoundCallback, never()).onPackageFound(anyString());
    }

    private void givenThatPackageIsFound() {
        when(mockBestPackageFinder.findBestPackage()).thenReturn(NON_EMPTY_PACKAGE);
    }

    private void givenThatPackageIsNotFound() {
        when(mockBestPackageFinder.findBestPackage()).thenReturn("");
    }

    private static class StubExecutor implements Executor {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mHandler.post(command);
        }
    }

}
