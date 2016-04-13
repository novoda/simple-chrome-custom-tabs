package com.novoda.simplechromecustomtabs.demo;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.widget.TextView;

import java.util.List;

public final class SimpleChromeCustomTabsLinkifier {

    public static void linkify(TextView textView, UrlSpanFactory.OnWebLinkClickedListener onWebLinkClickedListener) {
        Spannable spannable = getSpannableTextOf(textView);
        removeAllUrlSpansFrom(spannable);

        List<MatchedUrl> urls = new UrlFinder().findUrlsIn(spannable);
        UrlSpanFactory urlSpanFactory = new UrlSpanFactory(onWebLinkClickedListener);

        for (MatchedUrl matchedUrl : urls) {
            URLSpan urlSpan = urlSpanFactory.createSpan(matchedUrl.url);
            spannable.setSpan(urlSpan, matchedUrl.start, matchedUrl.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannable);
        if (urls.size() > 0) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private static void removeAllUrlSpansFrom(Spannable spannable) {
        final URLSpan[] urlSpans = spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (URLSpan urlSpan1 : urlSpans) {
            spannable.removeSpan(urlSpan1);
        }
    }

    private static Spannable getSpannableTextOf(TextView textView) {
        CharSequence textViewText = textView.getText();
        Spannable spannable;
        if (textViewText instanceof Spannable) {
            spannable = (Spannable) textViewText;
        } else {
            spannable = SpannableString.valueOf(textViewText);
        }
        return spannable;
    }
}
