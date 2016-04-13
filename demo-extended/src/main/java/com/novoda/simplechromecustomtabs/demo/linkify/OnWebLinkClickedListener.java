package com.novoda.simplechromecustomtabs.demo.linkify;

import android.net.Uri;

public interface OnWebLinkClickedListener {
    /**
     * Called when a Web link has been clicked
     *
     * @param url The uri to the link that has been pressed
     */
    void onClick(Uri url);
}
