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


public class TransportTaxiFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transport_taxi, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        WebView taxi = view.findViewById(R.id.taxi_webView);
        WebSettings webSettings = taxi.getSettings();
        webSettings.setJavaScriptEnabled(true);
        taxi.setWebViewClient(new WebViewClient());
        taxi.loadUrl("https://expresstaxi.uz.ua/online");

    }

}