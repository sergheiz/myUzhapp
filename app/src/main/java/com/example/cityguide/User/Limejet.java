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

public class Limejet extends AppCompatActivity {

    private WebView limejetWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limejet);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

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