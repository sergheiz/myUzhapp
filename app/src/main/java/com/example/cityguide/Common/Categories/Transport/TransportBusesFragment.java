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


public class TransportBusesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transport_buses, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        WebView buses = view.findViewById(R.id.buses_webView);
        WebSettings webSettings = buses.getSettings();
        buses.setWebViewClient(new WebViewClient());
        buses.loadUrl("https://city.dozor.tech/ua/uzhgorod");
        webSettings.setJavaScriptEnabled(true);
        buses.getSettings().setLoadWithOverviewMode(true);
        //buses.getSettings().setSupportZoom(true);
        //buses.getSettings().setUseWideViewPort(true);
        buses.setInitialScale(200);

    }
}