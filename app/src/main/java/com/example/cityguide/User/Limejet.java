package com.example.cityguide.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cityguide.R;

public class Limejet extends AppCompatActivity {

    private WebView limejetWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_limejet);

        limejetWebView = findViewById(R.id.limejet_webView);
        WebSettings webSettings = limejetWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        limejetWebView.setWebViewClient(new WebViewClient());
        limejetWebView.loadUrl("https://www.limejet.com");

    }

    public void backToTransport(View view) {
        finish();
    }
}