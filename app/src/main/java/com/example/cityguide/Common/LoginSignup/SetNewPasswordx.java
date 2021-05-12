package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.transition.Fade;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class SetNewPasswordx extends AppCompatActivity {

    //blabla
    RelativeLayout progressBar;
    TextInputLayout newPassword, conformNewPassword;
    ImageView newPasswordIcon;
    TextView title, description;
    Animation animation;
    Button setNewPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_passwordx);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        //Hooks
        progressBar = findViewById(R.id.set_new_password_progress_bar);
        newPassword = findViewById(R.id.new_password);
        conformNewPassword = findViewById(R.id.confirm_new_password);
        newPasswordIcon = findViewById(R.id.set_new_password_icon);
        title = findViewById(R.id.set_new_password_title);
        description = findViewById(R.id.set_new_password_description);
        setNewPasswordBtn = findViewById(R.id.set_new_password_btn);

        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slid_animation);

        //Set animation to elements
        newPasswordIcon.setAnimation(animation);
        title.setAnimation(animation);
        description.setAnimation(animation);
        newPassword.setAnimation(animation);
        conformNewPassword.setAnimation(animation);
        setNewPasswordBtn.setAnimation(animation);
    }

    //Custom Dialog for internet check
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPasswordx.this);
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

    private boolean validatePassword() {
        String _newPassword = newPassword.getEditText().getText().toString().trim();
        String _conformNewPassword = conformNewPassword.getEditText().getText().toString().trim();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (_newPassword.isEmpty()) {
            newPassword.setError(getText(R.string.val_not_empty));
            newPassword.requestFocus();
            return false;
        } else if (!_newPassword.matches(noWhiteSpaces)) {
            newPassword.setError(getText(R.string.val_no_whitespaces));
            return false;
        } else if (_newPassword.length() < 4) {
            newPassword.setError(getText(R.string.val_too_short));
            return false;
        } else if (!_newPassword.equals(_conformNewPassword)) {
            conformNewPassword.setError(getText(R.string.val_match_password));
            conformNewPassword.requestFocus();
            return false;
        } else {
            newPassword.setError(null);
            newPassword.setErrorEnabled(false);
            return true;
        }
    }


    //Setting  new password
    public void setNewPasswordBtn(View view) {

        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if (!validatePassword()) {

            return;

        }
        progressBar.setVisibility(View.VISIBLE);

        //Get data from fields
        String _newPassword = newPassword.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");





        //Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_phoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    assert _phoneNumber != null;
                    String fullNameFromDB = snapshot.child(_phoneNumber).child("fullName").getValue(String.class);
                    String avatarUrlFromDB = snapshot.child(_phoneNumber).child("avatarUrl").getValue(String.class);

                    SessionManager sessionManager = new SessionManager(SetNewPasswordx.this, SessionManager.SESSION_USERSLOGIN);
                    sessionManager.createLoginSession(_phoneNumber, fullNameFromDB, avatarUrlFromDB);
                    finish();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Update Data in firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);


        Intent intent = new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class);
        startActivity(intent);




    }
}