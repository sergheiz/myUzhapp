package com.example.cityguide.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Pair;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cityguide.Common.Categories.Entertainment.MainEntertainment;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 5000;

    //Variabs
    ImageView backgroundImage;
    TextView poweredByLine, appName;
    LottieAnimationView  bottom_line_anim;

    Animation bottomLine, zoomIN;



    SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        //Hooks
        backgroundImage = findViewById(R.id.background_image);
        appName = findViewById(R.id.app_name);
        bottom_line_anim = findViewById(R.id.bottom_line_anim);
        poweredByLine = findViewById(R.id.powered_by_line);

        bottomLine = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_anim);
        zoomIN = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_in_anim);


        backgroundImage.setAnimation(zoomIN);
        appName.setAnimation(zoomIN);
        poweredByLine.setAnimation(bottomLine);
        //bottom_line_anim.setAnimation(bottomLine);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                if (isFirstTime){

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair(findViewById(R.id.splash_layout), "Onborading");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                    startActivity(intent, options.toBundle());
                    finish();

                }
                else {
                    Intent intent = new Intent(getApplicationContext(), UserDashboard.class);

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair(findViewById(R.id.splash_layout), "ud_layout_transition");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                    startActivity(intent, options.toBundle());

                    finish();

                }




            }
        },SPLASH_TIMER);


    }
}