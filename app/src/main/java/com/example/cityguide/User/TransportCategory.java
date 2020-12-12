package com.example.cityguide.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.example.cityguide.R;

public class TransportCategory extends AppCompatActivity {

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
        setContentView(R.layout.activity_transport_category);

    }

    public void callBuses(View view) {

        Intent intent = new Intent(getApplicationContext(), Buses.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.buses_btn), "transition_buses");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TransportCategory.this, pairs);
        startActivity(intent, options.toBundle());

    }

    public void callLimejet(View view) {

        Intent intent = new Intent(getApplicationContext(), Limejet.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.limejet_btn), "transition_limejet");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TransportCategory.this, pairs);
        startActivity(intent, options.toBundle());

    }
}