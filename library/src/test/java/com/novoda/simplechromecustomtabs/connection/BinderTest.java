package com.novoda.simplechromecustomtabs.connection;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;

import com.novoda.simplechromecustomtabs.provider.AvailableAppProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static com.novoda.simplechromecustomtabs.provider.AvailableAppProvider.PackageFoundCallback;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class BinderTest {

    @Mock
    private Activity mockActivity;
    @Mock
    private ServiceConnectionCallback mockServiceConnectionCallback;
    @Mock
    private AvailableAppProvider mockAvailableAppProvider;
    @Captor
    private ArgumentCaptor<AvailableAppProvider.PackageFoundCallback> packageFoundCallbackCaptor;

    private Binder binder;

    @Before
    public void setUp() {
        initMocks(this);

        packageFoundCallbackCaptor = ArgumentCaptor.forClass(PackageFoundCallback.class);
        binder = new Binder(mockAvailableAppProvider);
    }

    @Test
    public void givenThatActivityIsBeingBoundToService_whenPackageIsFound_thenActivityIsBound() {
        givenThatActivityIsBeingBoundToService();

        whenPackageIsFound();

        verify(mockActivity).bindService(any(Intent.class), any(ServiceConnection.class), anyInt());
    }

    @Test
    public void givenThatActivityIsAlreadyBoundToService_whenBindingActivityToService_thenPackageIsNotSearched() {
        givenThatActivityIsAlreadyBoundtoService();

        bindActivityToService();

        verifyNoMoreInteractions(mockAvailableAppProvider);
    }

    @Test
    public void givenThatActivityIsBeingBoundToService_whenPackageIsFound_thenActivityIsNotBound() {
        bindActivityToService();

        whenPackageIsNotFound();

        verifyNoMoreInteractions(mockActivity);
    }

    @Test
    public void givenThatActivityIsBoundToServce_whenActivityIsUnboundFromService_thenActivityIsUnbound() {
        givenThatActivityIsAlreadyBoundtoService();

        binder.unbindCustomTabsService(mockActivity);

        verify(mockActivity).unbindService(any(ServiceConnection.class));
    }

    @Test
    public void givenThatActivityIsNotBoundToService_whenActivityIsUnboundFromService_thenNothingHappens() {
        givenThatActivityIsNotBoundToService();

        binder.unbindCustomTabsService(mockActivity);

        verifyZeroInteractions(mockActivity);
    }

    private void givenThatActivityIsBeingBoundToService() {
        bindActivityToService();
    }

    private void givenThatActivityIsNotBoundToService() {
        //no-op
    }

    private void bindActivityToService() {
        binder.bindCustomTabsServiceTo(mockActivity, mockServiceConnectionCallback);
    }

    private void whenPackageIsFound() {
        verify(mockAvailableAppProvider).findBestPackage(packageFoundCallbackCaptor.capture());
        packageFoundCallbackCaptor.getValue().onPackageFound("anyPackage");
    }

    private void whenPackageIsNotFound() {
        verify(mockAvailableAppProvider).findBestPackage(packageFoundCallbackCaptor.capture());
        packageFoundCallbackCaptor.getValue().onPackageNotFound();
    }

    private void givenThatActivityIsAlreadyBoundtoService() {
        givenThatActivityIsBeingBoundToService();
        whenPackageIsFound();
        reset(mockActivity);
        reset(mockServiceConnectionCallback);
    }

}
