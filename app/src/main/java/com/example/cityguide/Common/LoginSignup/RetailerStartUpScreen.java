package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.cityguide.Databases.SessionManager;
import com.example.cityguide.LocationOwner.RetailerDashboard;
import com.example.cityguide.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;


public class RetailerStartUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            Toast.makeText(RetailerStartUpScreen.this, "Already Logged In",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RetailerStartUpScreen.this, RetailerDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        setContentView(R.layout.activity_retailer_start_up_screen);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);



    }

    public void callLoginScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.login_btn), "transition_login");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this, pairs);
        startActivity(intent, options.toBundle());

        finish();


    }

    public void callSignUpScreen(View view) {


        Intent intent = new Intent(getApplicationContext(), SignUp.class);


        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.signup_btn), "transition_signup");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this, pairs);
        startActivity(intent, options.toBundle());

        finish();


    }
}