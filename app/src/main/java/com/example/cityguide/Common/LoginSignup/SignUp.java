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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    //Variables
    Button next, login;
    TextView titleText, slideText;
    ScrollView scrollView;

    //get data variables
    TextInputLayout fullName, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up);

        Fade fade = null;
        fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        //Hooks
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);

        scrollView = findViewById(R.id.signup_1st_scrollView);

        //hooks for getting data
        fullName = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }


    public void callNextSignupScreen(View view) {

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }

        String _fullName = fullName.getEditText().getText().toString();
        String _email = email.getEditText().getText().toString().trim();
        String _username = username.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);


        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_signup");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, options.toBundle());
        finish();



    }


    // validation Functions

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError(getText(R.string.val_not_empty));
            fullName.requestFocus();
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError(getText(R.string.val_not_empty));
            username.requestFocus();
            return false;
        } else if (val.length() > 20) {
            username.setError(getText(R.string.val_too_large));
            return false;

        } else if (!val.matches(checkspaces)) {
            username.setError(getText(R.string.val_no_whitespaces));
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError(getText(R.string.val_not_empty));
            email.requestFocus();
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError(getText(R.string.val_invalid_email));
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            password.setError(getText(R.string.val_not_empty));
            password.requestFocus();
            return false;
        } else if (val.length() < 4) {
            password.setError(getText(R.string.val_password));
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }


}