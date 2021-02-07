package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cityguide.Databases.SessionManager;
import com.example.cityguide.Databases.User;
import com.example.cityguide.LocationOwner.RetailerDashboard;
import com.example.cityguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView pinFromUser;
    RelativeLayout progressbar;
    TextView otpDescriptionText;
    ScrollView scrollViewOTP;
    private Animation animation;
    String codeBySystem, phoneNo, email, fullName, password, whatToDo;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        //Hooks
        mAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.verify_progress_bar);
        otpDescriptionText = findViewById(R.id.otp_description_text);
        pinFromUser = findViewById(R.id.pin_view);
        scrollViewOTP = findViewById(R.id.otp_scrollview);

        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slid_animation);

        //Set animation to elements
        scrollViewOTP.setAnimation(animation);

        //get all data
        phoneNo = getIntent().getStringExtra("phoneNo");
        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        whatToDo = getIntent().getStringExtra("whatToDo");

        otpDescriptionText.setText(getText(R.string.otp_description_text) + "\n" + phoneNo);

        sendVerificationCodeToUser(phoneNo);

    }


    private void sendVerificationCodeToUser(String phoneNo) {

        progressbar.setVisibility(View.VISIBLE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)              // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                    progressbar.setVisibility(View.GONE);

                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                        progressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (whatToDo.equals("Login")) {
                                Toast.makeText(VerifyOTP.this, getText(R.string.otp_complete), Toast.LENGTH_SHORT).show();
                                login();
                            }

                            if (whatToDo.equals("setNewPassword")) {
                                Toast.makeText(VerifyOTP.this, getText(R.string.otp_complete), Toast.LENGTH_SHORT).show();
                                setNewPassword();
                            }
                            if (whatToDo.equals("createNewUser")) {
                                Toast.makeText(VerifyOTP.this, getText(R.string.otp_complete), Toast.LENGTH_SHORT).show();
                                createNewUser();
                            }

                            if (whatToDo.equals("updatePhone")) {
                                Toast.makeText(VerifyOTP.this, getText(R.string.otp_complete), Toast.LENGTH_SHORT).show();
                                updatePhone();
                            }


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(VerifyOTP.this, getText(R.string.otp_not_completed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updatePhone() {

        //Get data from fields
        String o_phoneNumber = getIntent().getStringExtra("OLDphoneNo");

        //Update Data in firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phoneNo).child("phoneNo").setValue(phoneNo);
        reference.child(phoneNo).child("fullName").setValue(fullName);
        reference.child(phoneNo).child("email").setValue(email);
        reference.child(phoneNo).child("password").setValue(password);

        reference.child(o_phoneNumber).removeValue();

        SessionManager sessionManager = new SessionManager(VerifyOTP.this, SessionManager.SESSION_USERSLOGIN);
        sessionManager.createLoginSession(phoneNo, fullName, email, password);

        Toast.makeText(VerifyOTP.this, "Phone number Updated", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), RetailerDashboard.class);
        startActivity(intent);

        progressbar.setVisibility(View.GONE);



        finish();

    }

    public void setNewPassword() {
        Intent intent = new Intent(getApplicationContext(), SetNewPasswordx.class);
        intent.putExtra("phoneNo", phoneNo);
        startActivity(intent);
        progressbar.setVisibility(View.GONE);
        finish();


    }

    public void login() {

        SessionManager sessionManager = new SessionManager(VerifyOTP.this, SessionManager.SESSION_USERSLOGIN);
        sessionManager.createLoginSession(phoneNo, fullName, email, password);

        Intent intent = new Intent(getApplicationContext(), RetailerDashboard.class);
        startActivity(intent);

        progressbar.setVisibility(View.GONE);



        finish();


    }


    private void createNewUser() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        User addNewUser = new User(phoneNo, email, fullName, password);
        reference.child(phoneNo).setValue(addNewUser);

        SessionManager sessionManager = new SessionManager(VerifyOTP.this, SessionManager.SESSION_USERSLOGIN);
        sessionManager.createLoginSession(phoneNo, fullName, email, password);

        progressbar.setVisibility(View.GONE);

        startActivity(new Intent(getApplicationContext(), RegistrationSuccess.class));



        finish();

    }


    public void callNextScreenFromOTP(View view) {
        String code = pinFromUser.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        } else {

            Toast.makeText(VerifyOTP.this, getText(R.string.otp_not_completed), Toast.LENGTH_SHORT).show();


        }

    }
}