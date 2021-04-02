package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newtonacademic.newtontutors.R;

/**
 * @创建者 Administrator
 * @创建时间 2020/7/19 23:47
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class AboutActivity extends BaseActivity {

    //    private TextView tv_about;
    private WebView webView;
    private TextView tvTitle;
    private RelativeLayout leftBtnLayout;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        mCtx = this;
        initView();
        initData();
        setListener();
    }

    private void initView() {
        webView = findViewById(R.id.webview);
        tvTitle = findViewById(R.id.tvTitle);
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setTextSize(WebSettings.TextSize.LARGEST);
        webView.loadUrl("file:///android_asset/about.html");
    }

    private void initData() {
    }

    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
