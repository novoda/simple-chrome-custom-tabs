package com.novoda.simplechromecustomtabs.demo.linkify;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UrlFinder {

    private final Pattern urlPattern;

    public UrlFinder(Pattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    List<MatchedUrl> findUrlsIn(CharSequence charSequence) {
        List<MatchedUrl> urls = new ArrayList<>();
        if (isEmpty(charSequence)) {
            return urls;
        }

        Matcher matcher = urlPattern.matcher(charSequence);
        while (matcher.find()) {
            urls.add(new MatchedUrl(matcher.group(), matcher.start(), matcher.end()));
        }
        return urls;
    }

    private boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }
}
