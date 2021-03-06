package simplers.interestly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import net.ninjas.libninja.ninja;

import java.security.SecureRandom;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WebActivity extends AppCompatActivity {

    private static WebActivity self = null;
    private boolean mVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        self = this;
        setContentView(R.layout.activity_web);

        ImageButton nextBtn = (ImageButton) findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new nextLinkRun()).execute();
                if(mVisible)
                    hideMenu();
            }
        });
        nextBtn.bringToFront();

        ImageButton menuBtn = (ImageButton)findViewById(R.id.menu_button);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        ImageButton shareButton = (ImageButton)findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = (WebView)findViewById(R.id.webView);
                String url = webView.getUrl();
                String title = webView.getTitle();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url +
                        "\nShared from Interestly!");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                hideMenu();
            }
        });

        ImageButton backButton = (ImageButton)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = (WebView) findViewById(R.id.webView);
                if(!webView.canGoBack()) {
                    Toast.makeText(v.getContext(), "No back from here", Toast.LENGTH_LONG).show();
                    return;
                }
                webView.goBack();
                hideMenu();
            }
        });

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                (new nextLinkRun()).execute();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setUserAgentString(getString(R.string.app_name) + "/" + BuildConfig.VERSION_NAME + " Mobile / Android " + Build.VERSION.CODENAME);

        (new nextLinkRun()).execute();

    }
    private void toggle() {
        if (mVisible) {
            hideMenu();
        } else {
            showMenu();
        }
        mVisible = !mVisible;
    }

    private void hideMenu() {
        ImageButton backBtn = (ImageButton)findViewById(R.id.back_button);
        backBtn.setVisibility(View.GONE);
        ImageButton shareBtn = (ImageButton)findViewById(R.id.share_button);
        shareBtn.setVisibility(View.GONE);

    }

    private void showMenu() {
        ImageButton backBtn = (ImageButton)findViewById(R.id.back_button);
        backBtn.setVisibility(View.VISIBLE);
        ImageButton shareBtn = (ImageButton)findViewById(R.id.share_button);
        shareBtn.setVisibility(View.VISIBLE);

    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    static class nextLinkRun extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void ...params) {
            return ContentProviders.getRandomUrl();

        }

        @Override
        protected void onPostExecute(String s) {
            WebView webView = (WebView)self.findViewById(R.id.webView);
            webView.loadUrl(s);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SecureRandom r = new SecureRandom();
        ninja.start("ohadcn", r.nextLong());
    }

    @Override
    protected void onPause() {
        super.onPause();
        ninja.stop();
    }
}
