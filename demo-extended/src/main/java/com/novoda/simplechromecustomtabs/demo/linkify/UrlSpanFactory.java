package com.novoda.simplechromecustomtabs.demo.linkify;

import android.net.Uri;
import android.text.style.URLSpan;
import android.view.View;

class UrlSpanFactory {

    private final OnWebLinkClickedListener onWebLinkClickedListener;

    UrlSpanFactory(OnWebLinkClickedListener onWebLinkClickedListener) {
        this.onWebLinkClickedListener = onWebLinkClickedListener;
    }

    URLSpan createSpanFor(final String webLinkUrl) {
        return new URLSpan(webLinkUrl) {
            @Override
            public void onClick(View widget) {
                Uri spanUrl = Uri.parse(getURL());
                onWebLinkClickedListener.onClick(spanUrl);
            }
        };
    }

}
