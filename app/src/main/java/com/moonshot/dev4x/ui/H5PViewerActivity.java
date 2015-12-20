package com.moonshot.dev4x.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.ui.common.BaseActivity;

import android.webkit.WebView;

/**
 * Created by hirendave on 9/26/15.
 */
public class H5PViewerActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("www.example.com");
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, H5PViewerActivity.class);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_h5p;
    }
}
