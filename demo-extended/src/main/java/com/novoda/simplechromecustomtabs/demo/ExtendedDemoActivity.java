package com.novoda.simplechromecustomtabs.demo;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;
import com.novoda.simplechromecustomtabs.navigation.IntentCustomizer;
import com.novoda.simplechromecustomtabs.navigation.NavigationFallback;
import com.novoda.simplechromecustomtabs.navigation.SimpleChromeCustomTabsIntentBuilder;

import static com.novoda.simplechromecustomtabs.provider.SimpleChromeCustomTabsAvailableAppProvider.PackageFoundCallback;

public class ExtendedDemoActivity extends AppCompatActivity {

    private static final Uri WEB_URL = Uri.parse("http://www.novoda.com");
    private static final int REQUEST_CODE_VIEW_SOURCE = 1;
    private static final int REQUEST_CODE_NOVODA_LONDON = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_demo_layout);
        findViewById(R.id.open_url_button).setOnClickListener(openUrlButtonClickListener);

        TextView textView = (TextView) findViewById(R.id.linkified_text_view);
        LinkifyWithCustomTabs.addLinks(textView, navigationFallback, intentCustomizer, this);
    }

    private final View.OnClickListener openUrlButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SimpleChromeCustomTabs.getInstance().withFallback(navigationFallback)
                    .withIntentCustomizer(intentCustomizer)
                    .navigateTo(WEB_URL, ExtendedDemoActivity.this);
        }
    };

    private final NavigationFallback navigationFallback = new NavigationFallback() {
        @Override
        public void onFallbackNavigateTo(Uri url) {
            Toast.makeText(getApplicationContext(), R.string.application_not_found, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(url)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    private final IntentCustomizer intentCustomizer = new IntentCustomizer() {
        @Override
        public SimpleChromeCustomTabsIntentBuilder onCustomiseIntent(SimpleChromeCustomTabsIntentBuilder simpleChromeCustomTabsIntentBuilder) {
            return simpleChromeCustomTabsIntentBuilder.withToolbarColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black))
                    .showingTitle()
                    .withUrlBarHiding()
                    .withCloseButtonIcon(decodeCloseBitmap())
                    .withActionButton(decodeMapBitmap(), getString(R.string.novoda_london), navigateToNovodaLondon(), false)
                    .withMenuItem(getString(R.string.view_demo_source_code), viewSourceCode())
                    .withExitAnimations(getApplicationContext(), android.R.anim.slide_in_left, android.R.anim.fade_out)
                    .withStartAnimations(getApplicationContext(), android.R.anim.fade_in, android.R.anim.slide_out_right);
        }
    };

    private Bitmap decodeMapBitmap() {
        return BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_mapmode);
    }

    private Bitmap decodeCloseBitmap() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back);
    }

    private PendingIntent navigateToNovodaLondon() {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:51.5411671,-0.0947801"));
        return PendingIntent.getActivity(ExtendedDemoActivity.this, REQUEST_CODE_NOVODA_LONDON, viewIntent, 0);
    }

    private PendingIntent viewSourceCode() {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/novoda/simplechromecustomtabs"));
        return PendingIntent.getActivity(ExtendedDemoActivity.this, REQUEST_CODE_VIEW_SOURCE, viewIntent, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForPackageAvailable();
        SimpleChromeCustomTabs.getInstance().connectTo(this);
    }

    private void checkForPackageAvailable() {
        SimpleChromeCustomTabs.getInstance().findBestPackage(packageFoundCallback);
    }

    private final PackageFoundCallback packageFoundCallback = new PackageFoundCallback() {
        @Override
        public void onPackageFound(String packageName) {
            View contentView = findViewById(android.R.id.content);
            Snackbar.make(contentView, R.string.application_found, Snackbar.LENGTH_INDEFINITE).show();
        }

        @Override
        public void onPackageNotFound() {
            View contentView = findViewById(android.R.id.content);
            Snackbar.make(contentView, R.string.application_not_found, Snackbar.LENGTH_INDEFINITE).show();
        }
    };

    @Override
    protected void onPause() {
        SimpleChromeCustomTabs.getInstance().disconnectFrom(this);

        super.onPause();
    }

}
