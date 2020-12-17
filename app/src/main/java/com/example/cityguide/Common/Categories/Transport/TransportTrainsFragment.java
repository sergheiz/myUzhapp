package com.example.cityguide.Common.Categories.Transport;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cityguide.R;

public class TransportTrainsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_transport_trains, container, false);
        WebView trains = v.findViewById(R.id.trains_webView);
        trains.loadUrl("https://poizdato.net/rozklad-po-stantsii/uzhhorod/");

        // Enable Javascript
        WebSettings webSettings = trains.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        trains.setWebViewClient(new WebViewClient());

        return v;
    }

}