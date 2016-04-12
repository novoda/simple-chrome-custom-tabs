package com.novoda.simplechromecustomtabs.demo;

import android.net.Uri;
import android.text.style.URLSpan;
import android.view.View;

class UrlSpanFactory {

    private final OnWebLinkClickedListener onWebLinkClickedListener;

    UrlSpanFactory(OnWebLinkClickedListener onWebLinkClickedListener) {
        this.onWebLinkClickedListener = onWebLinkClickedListener;
    }

    URLSpan createSpan(final String url) {
        return new URLSpan(url) {
            @Override
            public void onClick(View widget) {
                Uri spanUrl = Uri.parse(getURL());
                onWebLinkClickedListener.onClick(spanUrl);
            }
        };
    }

    interface OnWebLinkClickedListener {
        void onClick(Uri url);
    }
}
