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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transport_trains, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        WebView trains = view.findViewById(R.id.trains_webView);
        WebSettings webSettings = trains.getSettings();
        webSettings.setJavaScriptEnabled(true);
        trains.setWebViewClient(new WebViewClient());
        trains.loadUrl("https://poizdato.net/rozklad-po-stantsii/uzhhorod/");

    }
}