package com.uit.letrongtin.eventapp.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uit.letrongtin.eventapp.R;

import dmax.dialog.SpotsDialog;

public class NewsWebActivity extends AppCompatActivity {

    WebView webView;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);

        webView = findViewById(R.id.web_view);

        dialog = new SpotsDialog(this);

        dialog.show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        if (getIntent()!=null){
            if (getIntent().getStringExtra("link") != null){
                webView.loadUrl(getIntent().getStringExtra("link"));
            }
        }
    }
}
