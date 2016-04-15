package com.novoda.simplechromecustomtabs.demo.linkify;

class MatchedUrl {

    final String url;
    final int start;
    final int end;

    MatchedUrl(String url, int start, int end) {
        this.url = url;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchedUrl that = (MatchedUrl) o;

        if (start != that.start) {
            return false;
        }
        if (end != that.end) {
            return false;
        }
        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + start;
        result = 31 * result + end;
        return result;
    }

    @Override
    public String  toString() {
        return "MatchedUrl{" +
                "url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
