package com.novoda.simplechromecustomtabs.demo.linkify;

import android.net.Uri;
import android.text.style.URLSpan;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UrlSpanFactoryTest {

    private static final String WEB_URL = "http://www.novoda.com";

    private UrlSpanFactory factory;

    @Mock
    private OnWebLinkClickedListener mockListener;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        factory = new UrlSpanFactory(mockListener);
    }

    @Test
    public void factoryCreatesSpanWithCorrectUrl() {
        URLSpan urlSpan = factory.createSpanFor(WEB_URL);
        assertThat(WEB_URL).isEqualTo(urlSpan.getURL());
    }

    @Test
    public void factoryCreatesSpanWithCorrectClickListener() {
        URLSpan urlSpan = factory.createSpanFor(WEB_URL);
        urlSpan.onClick(any(View.class));

        verify(mockListener).onClick(Uri.parse(WEB_URL));

    }

}
