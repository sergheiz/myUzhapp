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
        View v=inflater.inflate(R.layout.fragment_transport_buses, container, false);
        WebView buses = (WebView) v.findViewById(R.id.buses_webView);
        buses.loadUrl("https://city.dozor.tech/ua/uzhgorod");

        // Enable Javascript
        WebSettings webSettings = buses.getSettings();
        webSettings.setJavaScriptEnabled(true);
        buses.getSettings().setUseWideViewPort(true);
        buses.getSettings().setBuiltInZoomControls(true);

        // Force links and redirects to open in the WebView instead of in a browser
        buses.setWebViewClient(new WebViewClient());

        return v;
    }


}