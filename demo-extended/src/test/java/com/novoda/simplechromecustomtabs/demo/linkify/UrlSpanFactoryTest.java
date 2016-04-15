package com.novoda.simplechromecustomtabs.demo.linkify;

import android.text.style.URLSpan;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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
    public void factoryCreatesSpanWithCorrectClickListener() {
        URLSpan urlSpan = factory.createSpanFor(WEB_URL);
        urlSpan.onClick(any(View.class));

        verify(mockListener).onClick(WEB_URL);

    }

}
