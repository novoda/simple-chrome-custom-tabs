package com.novoda.simplechromecustomtabs.demo;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class UrlFinder {

    public List<MatchedUrl> findUrlsIn(CharSequence charSequence) {
        List<MatchedUrl> urls = new ArrayList<>();
        Matcher m = Patterns.WEB_URL.matcher(charSequence);
        while (m.find()) {
            String group = m.group();
            int start = m.start();
            int end = m.end();
            urls.add(new MatchedUrl(group, start, end));
        }
        return urls;

    }
}
