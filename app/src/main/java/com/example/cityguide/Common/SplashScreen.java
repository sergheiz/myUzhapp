package com.example.cityguide.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 7000;

    //Variables
    ImageView backgroundImage;
    TextView poweredByLine;
    LottieAnimationView bounces, snowman;



    SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        //Hooks
        backgroundImage = findViewById(R.id.background_image);
        poweredByLine = findViewById(R.id.powered_by_line);
        bounces = findViewById(R.id.bounces);
        snowman = findViewById(R.id.snowman);


        backgroundImage.animate().translationY(-1400).setDuration(1000).setStartDelay(5000);
        bounces.animate().translationY(-1600).setDuration(1000).setStartDelay(5000);
        poweredByLine.animate().translationY(1400).setDuration(1000).setStartDelay(5000);
        snowman.animate().translationY(1400).setDuration(1000).setStartDelay(5000);



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
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                    startActivity(intent);
                    finish();

                }




            }
        },SPLASH_TIMER);


    }
}