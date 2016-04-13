package com.novoda.simplechromecustomtabs.demo.linkify;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

class UrlFinder {

    List<MatchedUrl> findUrlsIn(CharSequence charSequence) {
        List<MatchedUrl> urls = new ArrayList<>();
        Matcher matcher = Patterns.WEB_URL.matcher(charSequence);
        while (matcher.find()) {
            urls.add(new MatchedUrl(matcher.group(), matcher.start(), matcher.end()));
        }
        return urls;
    }
}
