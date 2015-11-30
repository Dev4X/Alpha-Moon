package com.moonshot.dev4x.ui;

import android.app.Activity;
import android.os.Bundle;

import com.moonshot.dev4x.R;

import android.webkit.WebView;

/**
 * Created by hirendave on 9/26/15.
 */
public class H5PViewerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_h5p);

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("www.example.com");
    }
}
