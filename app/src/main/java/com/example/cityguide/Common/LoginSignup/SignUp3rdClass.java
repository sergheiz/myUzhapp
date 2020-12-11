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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3rdClass extends AppCompatActivity {


    //Variables

    Button nextBtn;
    ScrollView scrollView;
    RelativeLayout layout3;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);

        Fade fade = null;
        fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        //Hooks
        nextBtn = findViewById(R.id.signup_next_button);
        scrollView = findViewById(R.id.signup_3rd_class);
        layout3 = findViewById(R.id.layout_signup3);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);

        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slid_animation);

        //Set animation to elements
        layout3.setAnimation(animation);

    }


    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError(getText(R.string.val_phone));
            phoneNumber.requestFocus();
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void callOTPScreen(View view) {

        //Validation
        if (!validatePhoneNumber()) {
            return;
        }

        //Get values from previous screens using intent

        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");


        //Get complete phone number
        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("whatToDo", "createNewUser"); // This is to identify that which action should OTP perform after verification.


        startActivity(intent);
        finish();

    }
}