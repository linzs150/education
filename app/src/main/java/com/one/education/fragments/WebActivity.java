package com.one.education.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.one.education.activities.BaseActivity;
import com.one.education.activities.IntentEx;
import com.one.education.education.R;

/**
 * @author laiyongyang
 * @date 2020-07-03
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class WebActivity extends BaseActivity {

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        return intent;
    }

    public static Intent newIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        showProgress("");
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        WebView wv = findViewById(R.id.webview);
        wv.setVerticalScrollBarEnabled(false);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.loadUrl(url);
        wv.setWebViewClient(new MyWebViewClient());
        //支持javascript
        wv.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        wv.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        wv.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        wv.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv.getSettings().setLoadWithOverviewMode(true);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            closeProgress();
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 接受所有网站的证书
            handler.proceed();
        }
    }
}
