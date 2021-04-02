package com.newtonacademic.newtontutors.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.R;

/**
 * @author laiyongyang
 * @date 2020-07-11
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class AcceptAccountPrivacyActivity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, AcceptAccountPrivacyActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_account_privacy);
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebView webView = findViewById(R.id.tbsReaderView);
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        webView.loadUrl("file:///android_asset/accept_account_privacy.html");
    }

}
