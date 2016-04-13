package com.novoda.simplechromecustomtabs.demo.linkify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class UrlFinderTest {

    private UrlFinder finder = new UrlFinder();

    @Test
    public void passingNullReturnsEmptyList() {
        List<MatchedUrl> urls = finder.findUrlsIn(null);
        assertThat(urls.size()).isEqualTo(0);
    }

    @Test
    public void passingEmptyStringReturnsEmptyList() {
        List<MatchedUrl> urls = finder.findUrlsIn("");
        assertThat(urls.size()).isEqualTo(0);
    }

    @Test
    public void urlIsCorrect() {
        String url = "http://www.novoda.com";
        int start = 0;
        int end = url.length();
        MatchedUrl expectedMatchedUrl = new MatchedUrl(url, start, end);

        String fullString = url + " is a great url!";
        List<MatchedUrl> urls = finder.findUrlsIn(fullString);
        MatchedUrl matchedUrl = urls.get(0);
        assertThat(matchedUrl).isEqualTo(expectedMatchedUrl);
    }

    @Test
    public void finderFindsTheCorrectNumberOfUrls() {
        String url = "List of URLs\n 1) http://www.novoda.com\n2) http://www.google.com 3) ???";
        List<MatchedUrl> urls = finder.findUrlsIn(url);
        assertThat(urls.size()).isEqualTo(2);
    }

}
