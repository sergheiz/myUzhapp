package com.example.cityguide.Common;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.transition.Fade;
import android.util.Pair;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cityguide.Common.Services.PlaySound;
import com.example.cityguide.HelperClasses.CheckInternet;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 4000;

    //Variabs
    ImageView logoImage;
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

        Intent playsound = new Intent(this, PlaySound.class);
        startService(playsound);


        //Hooks
        logoImage = findViewById(R.id.logo_image);
        appName = findViewById(R.id.app_name);
        bottom_line_anim = findViewById(R.id.bottom_line_anim);
        poweredByLine = findViewById(R.id.powered_by_line);

        bottomLine = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_anim);
        zoomIN = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_in_anim);


        logoImage.setAnimation(zoomIN);
        appName.setAnimation(zoomIN);
        poweredByLine.setAnimation(bottomLine);
        //bottom_line_anim.setAnimation(bottomLine);


        CheckInternet checkInternet = new CheckInternet();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                stopService(playsound);

                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                SharedPreferences.Editor editor = onBoardingScreen.edit();

                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                if (isFirstTime){



                    if (!checkInternet.isConnected(getApplicationContext())) {
                        showCustomDialog();
                        editor.putBoolean("firstTime",true);
                        return;
                    }


                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair(findViewById(R.id.splash_layout), "Onborading");
                    pairs[1] = new Pair(findViewById(R.id.logo_image), "app_logo");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                    startActivity(intent, options.toBundle());
                    finish();

                }
                else {
                    Intent intent = new Intent(getApplicationContext(), UserDashboard.class);

                    Pair[] pairs = new Pair[3];
                    pairs[0] = new Pair(findViewById(R.id.splash_layout), "ud_layout_transition");
                    pairs[1] = new Pair(findViewById(R.id.logo_image), "app_logo");
                    pairs[2] = new Pair(findViewById(R.id.app_name), "app_name");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                    startActivity(intent, options.toBundle());
                    finish();

                }




            }
        }

        ,SPLASH_TIMER);






    }

    //Custom Dialog for internet check
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
        builder.setMessage(getText(R.string.no_internet_first_start))
                .setCancelable(false)
                .setPositiveButton(getText(R.string.connect), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .setNegativeButton(getText(R.string.finish), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


}