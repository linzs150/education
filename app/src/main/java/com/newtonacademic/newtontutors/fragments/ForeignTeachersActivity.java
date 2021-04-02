package com.newtonacademic.newtontutors.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.retrofit.HttpsServiceFactory;
import com.newtonacademic.newtontutors.retrofit.model.GetArticleRsp;
import com.newtonacademic.newtontutors.thread.ThreadPoolProxyFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author laiyongyang
 * @date 2020-07-03
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class ForeignTeachersActivity extends BaseActivity {

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, ForeignTeachersActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_teacher);
        mWebView = findViewById(R.id.content_tv);
        WebSettings settings = mWebView.getSettings();
        // 设置WebView支持JavaScript
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(false);//关键点
        settings.setTextZoom(100);
        settings.setSupportZoom(true);  //支持放大缩小
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 添加客户端支持
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受所有网站的证书
            }
        });

        //不加这个图片显示不出来
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        }

        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int id = getIntent().getIntExtra("id", -1);
        showProgress(getString(R.string.loading));
        ThreadPoolProxyFactory.getNormalThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                GetArticleRsp getArticleRsp = HttpsServiceFactory.getArticle(id);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        closeProgress();
                        if (getArticleRsp.getStatus() == 1) {
                            mWebView.loadDataWithBaseURL(null, getNewContent(getArticleRsp.getData().getContent()), "text/html", "UTF-8", null);
                        } else {
                            ToastUtils.showToastLong(getArticleRsp.getDescript());
                        }
                    }
                });
            }
        });
    }

    public static String getNewContent(String htmltext){
        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");

        }

        return doc.toString();
    }
}
