package com.novoda.simplechromecustomtabs.demo;

class MatchedUrl {

    final String url;
    final int start;
    final int end;

    MatchedUrl(String url, int start, int end) {
        this.url = url;
        this.start = start;
        this.end = end;
    }

}
