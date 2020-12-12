package com.example.cityguide.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cityguide.R;

public class Buses extends AppCompatActivity {

    private WebView busesWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_buses);


        busesWebView = findViewById(R.id.buses_webView);
        WebSettings webSettings = busesWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        busesWebView.setWebViewClient(new WebViewClient());
        busesWebView.loadUrl("https://city.dozor.tech/ua/uzhgorod");

    }



}