package com.example.cityguide.LocationOwner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguide.Common.LoginSignup.VerifyOTP;
import com.example.cityguide.HelperClasses.CheckInternet;
import com.example.cityguide.HelperClasses.SessionManager;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class RetailerProfileFragment extends Fragment implements View.OnClickListener {


    TextView fullnameTV, emailTV;


    TextInputLayout emailField, fullNameField, passwordField, phoneNumberField;

    String phoneNoFromDB, fullNameFromDB, emailFromDB, passwordFromDB, _currentUser, dbPhone;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;

    Button Update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_retailer_profile, container, false);


        //Hooks
        phoneNumberField = (TextInputLayout) v.findViewById(R.id.phone_number);
        fullNameField = (TextInputLayout) v.findViewById(R.id.full_name);
        emailField = (TextInputLayout) v.findViewById(R.id.email);
        passwordField = (TextInputLayout) v.findViewById(R.id.password);


        fullnameTV = (TextView) v.findViewById(R.id.fullname_tv);
        emailTV = (TextView) v.findViewById(R.id.email_tv);


        Update = (Button) v.findViewById(R.id.update_btn);
        Update.setOnClickListener(this::onClick);

        reference = FirebaseDatabase.getInstance().getReference("Users");


        SessionManager sessionManager = new SessionManager(getContext(), SessionManager.SESSION_USERSLOGIN);
        if (sessionManager.checkLogin()) {
            HashMap<String, String> userDetails = sessionManager.getUsersDetailFromSession();

            fullnameTV.setText(userDetails.get(SessionManager.KEY_FULLNAME));
            emailTV.setText(userDetails.get(SessionManager.KEY_EMAIL));


            phoneNumberField.getEditText().setText(userDetails.get(SessionManager.KEY_PHONENUMBER));
            phoneNumberField.getEditText().addTextChangedListener(PhonetextWatcher);
            fullNameField.getEditText().setText(userDetails.get(SessionManager.KEY_FULLNAME));
            fullNameField.getEditText().addTextChangedListener(textWatcher);
            emailField.getEditText().setText(userDetails.get(SessionManager.KEY_EMAIL));
            emailField.getEditText().addTextChangedListener(textWatcher);
            passwordField.getEditText().setText(userDetails.get(SessionManager.KEY_PASSWORD));
            passwordField.getEditText().addTextChangedListener(textWatcher);
        }



        _currentUser = user.getPhoneNumber();

        //Get data from Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_currentUser);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    assert _currentUser != null;
                    phoneNoFromDB = snapshot.child(_currentUser).child("phoneNo").getValue(String.class);
                    fullNameFromDB = snapshot.child(_currentUser).child("fullName").getValue(String.class);
                    emailFromDB = snapshot.child(_currentUser).child("email").getValue(String.class);
                    passwordFromDB = snapshot.child(_currentUser).child("password").getValue(String.class);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }




    @Override
    public void onClick(View v) {


        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(getContext())) {
            showCustomDialog();
            return;
        }

        if (!validateFullName() | !validatePhoneNumber() | !validateEmail() | !validatePassword()) {
            return;
        }

        //Get data from Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_currentUser);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    assert _currentUser != null;
                    phoneNoFromDB = snapshot.child(_currentUser).child("phoneNo").getValue(String.class);
                    fullNameFromDB = snapshot.child(_currentUser).child("fullName").getValue(String.class);
                    emailFromDB = snapshot.child(_currentUser).child("email").getValue(String.class);
                    passwordFromDB = snapshot.child(_currentUser).child("password").getValue(String.class);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        String n_phoneNumber = phoneNumberField.getEditText().getText().toString().trim();
        String n_fullName = fullNameField.getEditText().getText().toString();
        String n_email = emailField.getEditText().getText().toString();
        String n_password = passwordField.getEditText().getText().toString().trim();



        if (!phoneNoFromDB.equals(n_phoneNumber)) {

            Intent intent = new Intent(getContext(), VerifyOTP.class);

            intent.putExtra("OLDphoneNo", phoneNoFromDB);
            intent.putExtra("phoneNo", n_phoneNumber);
            intent.putExtra("fullName", n_fullName);
            intent.putExtra("email", n_email);
            intent.putExtra("password", n_password);
            intent.putExtra("whatToDo", "updatePhone");

            startActivity(intent);
            getActivity().finish();

        }

        if (!fullNameFromDB.equals(n_fullName)) {
            reference.child(n_phoneNumber).child("fullName").setValue(n_fullName);
            Toast.makeText(getContext(), "Full Name Updated!", Toast.LENGTH_SHORT).show();
            fullnameTV.setText(n_fullName);
        }

        if (!emailFromDB.equals(n_email)) {
            reference.child(n_phoneNumber).child("email").setValue(n_email);
            Toast.makeText(getContext(), "Email Updated!", Toast.LENGTH_SHORT).show();
            emailTV.setText(n_email);

        }

        if (!passwordFromDB.equals(n_password)) {
            reference.child(n_phoneNumber).child("password").setValue(n_password);
            Toast.makeText(getContext(), "Password Updated!" + "\n"  + "New password:" + n_password, Toast.LENGTH_LONG).show();

        }

        SessionManager sessionManager = new SessionManager(getContext(), SessionManager.SESSION_USERSLOGIN);
        sessionManager.createLoginSession(n_phoneNumber, n_fullName, n_email, n_password);


    }


    //Custom Dialog for internet check
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                        startActivity(new Intent(getContext(), UserDashboard.class));
                        getActivity().finishAffinity();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {



            Update.setEnabled(true);
        }
    };

    private TextWatcher PhonetextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {

            final String _phoneNo = phoneNumberField.getEditText().getText().toString().trim();

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

            Update.setEnabled(true);
        }
    };


    // validation Functions

    private boolean validatePhoneNumber() {
        String val = phoneNumberField.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumberField.setError(getText(R.string.val_not_empty));
            phoneNumberField.requestFocus();
            return false;
        } else if (val.length() > 20) {
            phoneNumberField.setError(getText(R.string.val_too_large));
            return false;
        } else if (val.equals(dbPhone) && !phoneNoFromDB.equals(dbPhone)) {
            phoneNumberField.setError(getText(R.string.already_exist));
            return false;
        } else {
            phoneNumberField.setError(null);
            phoneNumberField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullName() {
        String val = fullNameField.getEditText().getText().toString();

        if (val.isEmpty()) {
            fullNameField.setError(getText(R.string.val_not_empty));
            fullNameField.requestFocus();
            return false;
        } else if (val.length() > 30) {
            fullNameField.setError(getText(R.string.val_too_large));
            return false;
        } else {
            fullNameField.setError(null);
            fullNameField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = emailField.getEditText().getText().toString().trim();
        String emailRegex = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";

        if (val.isEmpty()) {
            emailField.setError(getText(R.string.val_not_empty));
            emailField.requestFocus();
            return false;
        } else if (!val.matches(emailRegex)) {
            emailField.setError(getText(R.string.val_invalid_email));
            return false;
        } else if (val.length() > 30) {
            emailField.setError(getText(R.string.val_too_large));
            return false;
        } else {
            emailField.setError(null);
            emailField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordField.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            passwordField.setError(getText(R.string.val_not_empty));
            passwordField.requestFocus();
            return false;
        } else if (val.length() < 4) {
            passwordField.setError(getText(R.string.val_too_short));
            return false;
        } else if (val.length() > 20) {
            passwordField.setError(getText(R.string.val_too_large));
            return false;
        } else {
            passwordField.setError(null);
            passwordField.setErrorEnabled(false);
            return true;
        }
    }


}