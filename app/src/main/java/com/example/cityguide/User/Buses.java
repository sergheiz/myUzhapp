package com.example.cityguide.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cityguide.R;

public class Buses extends AppCompatActivity {

    private WebView busesWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        busesWebView = findViewById(R.id.buses_webView);
        WebSettings webSettings = busesWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        busesWebView.setWebViewClient(new WebViewClient());
        busesWebView.loadUrl("https://city.dozor.tech/ua/uzhgorod");

    }


    public void backToTransport(View view) {
        finish();
    }
}