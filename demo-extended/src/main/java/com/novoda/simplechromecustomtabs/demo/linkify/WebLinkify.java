package com.novoda.simplechromecustomtabs.demo.linkify;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Patterns;
import android.widget.TextView;

import java.util.List;

/**
 * A custom version of {@link android.text.util.Linkify} which allows the user to set their own listener for when any of the Web links has been clicked
 */
public final class WebLinkify {

    /**
     * Converts all Web links in the text of the given TextView into clickable {@link URLSpan}s. If the given TextView contains no links, this does nothing
     * <p>If the TextView already contains URLSpans they will be replaced</p>
     *
     * @param listener The listener that is being called when the URL has been clicked
     */
    public static void addLinks(@NonNull TextView textView, @NonNull OnWebLinkClickedListener listener) {
        Spannable spannable = getSpannableTextOf(textView);
        removeAllUrlSpansFrom(spannable);

        List<MatchedUrl> urls = new UrlFinder(Patterns.WEB_URL).findUrlsIn(spannable);
        UrlSpanFactory urlSpanFactory = new UrlSpanFactory(listener);

        for (MatchedUrl matchedUrl : urls) {
            URLSpan urlSpan = urlSpanFactory.createSpanFor(matchedUrl.url);
            spannable.setSpan(urlSpan, matchedUrl.start, matchedUrl.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannable);
        if (urls.size() > 0) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private static void removeAllUrlSpansFrom(Spannable spannable) {
        // The Spannable might already have some URLSpans set on it.
        // Make sure to remove them so that we can replace them with our own
        final URLSpan[] urlSpans = spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (URLSpan urlSpan : urlSpans) {
            spannable.removeSpan(urlSpan);
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
