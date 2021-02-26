package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.cityguide.HelperClasses.CheckInternet;
import com.example.cityguide.HelperClasses.SessionManager;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    //Variables

    TextInputLayout fullName, email, phoneNumber, password;
    CountryCodePicker countryCodePicker;

    Button nextBtn;


     String dbPhone;

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
        phoneNumber.getEditText().addTextChangedListener(PhonetextWatcher);


        fullName = findViewById(R.id.signup_fullname);


        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

        nextBtn = findViewById(R.id.next_btn);



    }

    private void Reading() {



    }


    public void callOTPScreen(View view) {


        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }


        String _fullName = fullName.getEditText().getText().toString();
        String _email = email.getEditText().getText().toString();
        String _password = password.getEditText().getText().toString().trim();

        //Get complete phone number
        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;



        if (!validateFullName() | !validatePhoneNumber() | !validateEmail() | !validatePassword()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("password", _password);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("whatToDo", "createNewUser"); // This is to identify that which action should OTP perform after verification.


        startActivity(intent);
        finish();

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


    private TextWatcher PhonetextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {



        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {




        }

        @Override
        public void afterTextChanged(Editable s) {

            String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
            if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
                _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
            }
            final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

            Query queryPhone = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_phoneNo);
            queryPhone.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        dbPhone = snapshot.child(_phoneNo).child("phoneNo").getValue(String.class);



                    } else {


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Toast.makeText(SignUp.this, "reading", Toast.LENGTH_SHORT).show();


            nextBtn.setEnabled(true);

        }
    };


    // validation Functions

    private boolean validatePhoneNumber() {

          //Get complete phone number
        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;





        String val = phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError(getText(R.string.val_not_empty));
            return false;
        } else if (val.length() > 9) {
            phoneNumber.setError(getText(R.string.val_too_large));
            return false;
        } else if (_phoneNo.equals(dbPhone)) {
            phoneNumber.setError(getText(R.string.already_exist));
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullName() {


        String val = fullName.getEditText().getText().toString();

        if (val.isEmpty()) {
            fullName.setError(getText(R.string.val_not_empty));
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
        //String spaces = "\\A\\w{1,30}+@\\w{1,30}+\\.+\\w{1,30}\\z";
        String emailRegex = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";

        if (val.isEmpty()) {
            email.setError(getText(R.string.val_not_empty));
            return false;
        } else if (!val.matches(emailRegex)) {
            email.setError(getText(R.string.val_invalid_email));
            return false;
        } else if (val.length() > 30) {
            email.setError(getText(R.string.val_too_large));
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
            return false;
        } else if (val.length() < 4) {
            password.setError(getText(R.string.val_too_short));
            return false;
        } else if (val.length() > 20) {
            password.setError(getText(R.string.val_too_large));
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
