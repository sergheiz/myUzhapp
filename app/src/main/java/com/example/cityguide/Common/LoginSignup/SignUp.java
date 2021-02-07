package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.transition.Fade;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.cityguide.Databases.CheckInternet;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {

    //Variables

    TextInputLayout fullName, email, phoneNumber, password;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_sign_up);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);



        //Hooks
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
        fullName = findViewById(R.id.signup_fullname);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }

    //Custom Dialog for internet check
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setMessage(getText(R.string.no_internet))
                .setCancelable(true)
                .setPositiveButton(getText(R.string.connect), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton(getText(R.string.home), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        finishAffinity();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    public void callOTPScreen(View view) {

        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if (!validateFullName() | !validatePhoneNumber() | !validateEmail() | !validatePassword()) {
            return;
        }

        String _fullName = fullName.getEditText().getText().toString();
        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        //Get complete phone number
        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("password", _password);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("whatToDo", "createNewUser"); // This is to identify that which action should OTP perform after verification.


        startActivity(intent);
        finish();

    }


    // validation Functions

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError(getText(R.string.val_not_empty));
            phoneNumber.requestFocus();
            return false;
        } else if (val.length() > 20) {
            fullName.setError(getText(R.string.val_too_large));
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError(getText(R.string.val_not_empty));
            fullName.requestFocus();
            return false;
        } else if (val.length() > 30) {
            fullName.setError(getText(R.string.val_too_large));
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            email.setError(getText(R.string.val_not_empty));
            email.requestFocus();
            return false;
        } else if (val.length() > 30) {
            fullName.setError(getText(R.string.val_too_large));
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            password.setError(getText(R.string.val_not_empty));
            password.requestFocus();
            return false;
        } else if (!val.matches(noWhiteSpaces)) {
            password.setError(getText(R.string.val_no_whitespaces));
            return false;
        } else if (val.length() < 4) {
            password.setError(getText(R.string.val_too_short));
            return false;
        } else if (val.length() > 20) {
            fullName.setError(getText(R.string.val_too_large));
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}