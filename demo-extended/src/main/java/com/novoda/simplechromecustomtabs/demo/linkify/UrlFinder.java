package com.novoda.simplechromecustomtabs.demo.linkify;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

class UrlFinder {

    List<MatchedUrl> findUrlsIn(CharSequence charSequence) {
        List<MatchedUrl> urls = new ArrayList<>();
        Matcher m = Patterns.WEB_URL.matcher(charSequence);
        while (m.find()) {
            urls.add(new MatchedUrl(m.group(), m.start(), m.end()));
        }
        return urls;
    }
}
