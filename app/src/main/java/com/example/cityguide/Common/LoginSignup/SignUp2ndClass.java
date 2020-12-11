package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguide.R;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {

    //Variables
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;
    LinearLayout layout2;
    ScrollView scrollView;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd_class);

        Fade fade = null;
        fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        //Hooks
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);
        layout2 = findViewById(R.id.layout_signup2);
        scrollView = findViewById(R.id.signup_2nd_class);


        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slid_animation);


        //Set animation to elements
        layout2.setAnimation(animation);

    }

    public void call3rdSignupScreen(View view) {

        if (!validateGender() | !validateAge()){
            return;
        }

        int day = datePicker . getDayOfMonth();
        int month = datePicker . getMonth();
        int year = datePicker . getYear();
        String _date = day+"/"+month+"/"+year;

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender = selectedGender.getText().toString();

        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");

        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);

        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);


        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_SignUp2ndClass");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
        startActivity(intent, options.toBundle());
        finish();


    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getText(R.string.val_gender), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, getText(R.string.val_age), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

}