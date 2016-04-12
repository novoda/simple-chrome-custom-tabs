package com.novoda.simplechromecustomtabs.demo;

import android.app.Activity;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;
import com.novoda.simplechromecustomtabs.navigation.IntentCustomizer;
import com.novoda.simplechromecustomtabs.navigation.NavigationFallback;

import java.util.regex.Matcher;

public class LinkifyWithCustomTabs {

    public static void addLinks(TextView textView, final NavigationFallback navigationFallback, final IntentCustomizer intentCustomizer, final Activity activity) {
        Spannable spannable = getSpannableFrom(textView);
        removeExistingUrlSpansFrom(spannable);

        Matcher m = Patterns.WEB_URL.matcher(spannable);

        while (m.find()) {
            int start = m.start();
            int end = m.end();

            final String group = m.group();
            final URLSpan urlSpan = new URLSpan(group) {
                @Override
                public void onClick(View widget) {
                    SimpleChromeCustomTabs.getInstance()
                            .withFallback(navigationFallback)
                            .withIntentCustomizer(intentCustomizer)
                            .navigateTo(Uri.parse(group), activity);
                }
            };

            spannable.setSpan(urlSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static void removeExistingUrlSpansFrom(Spannable spannable) {
        final URLSpan[] urlSpans = spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (URLSpan urlSpan : urlSpans) {
            spannable.removeSpan(urlSpan);
        }
    }

    private static Spannable getSpannableFrom(TextView textView) {
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
