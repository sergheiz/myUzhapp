package com.example.cityguide.Common.Transport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cityguide.R;


public class TransportFragmentTaxi extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_transport_taxi, container, false);
        WebView taxi = v.findViewById(R.id.taxi_webView);
        taxi.loadUrl("https://www.limejet.com/en/");

        // Enable Javascript
        WebSettings webSettings = taxi.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        taxi.setWebViewClient(new WebViewClient());

        return v;
    }


}