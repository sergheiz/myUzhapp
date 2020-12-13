package com.example.cityguide.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.example.cityguide.Common.LoginSignup.ForgetPassword;
import com.example.cityguide.R;

public class TransportCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_transport_category);

    }

    public void callBuses(View view) {

        startActivity(new Intent(getApplicationContext(), Buses.class));

    }

    public void callLimejet(View view) {

        Intent intent = new Intent(getApplicationContext(), Limejet.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.limejet_btn), "transition_limejet");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TransportCategory.this, pairs);
        startActivity(intent, options.toBundle());

    }
}