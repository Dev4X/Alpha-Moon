package com.moonshot.dev4x.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.moonshot.dev4x.R;
import com.moonshot.dev4x.eventhandlers.VideoCompleteListener;
import com.moonshot.dev4x.helpers.DatabaseHelper;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;

/**
 * Created by hirendave on 9/26/15.
 */
public class H5PViewerActivity extends Activity {
    public int nodeId;
    public int contentId;
    public int skillId;
    DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting up full screen mode for kisok mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //////////////
        setContentView(R.layout.h5p_viewer);
        String content = getIntent().getExtras().getString("content");
        this.nodeId = getIntent().getExtras().getInt("node_id");
        this.contentId = getIntent().getExtras().getInt("content_id");
        this.skillId = getIntent().getExtras().getInt("skill_id");

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebChromeClient(new WebChromeClient());
        browser.setWebViewClient(new WebViewClient());
        browser.getSettings().setAllowFileAccess(true);
        browser.getSettings().setPluginState(PluginState.ON);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl(content);
    }
}
