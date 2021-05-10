package com.example.cityguide.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cityguide.Common.LoginSignup.VerifyOTP;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.HelperClasses.Adapters.fsAdapter;
import com.example.cityguide.HelperClasses.CheckInternet;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.HelperClasses.SessionManager;
import com.example.cityguide.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class UserProfile extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ByteArrayOutputStream baos;


    Button saveFullName, imgError, savePhoto, userPhotoPicker;

    TextView fullnameTV, phoneTV;


    ImageView userPhoto;

    fsAdapter manageAdapter, myLikesAdapter;

    TextInputLayout fullNameField;

    String fullNameFromDB, phoneFromDB, n_avatarUrl;


    DatabaseReference reference;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageRef = storage.getReference("Profile Images");

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference placesRef = firebaseFirestore.collection("Places");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        baos = new ByteArrayOutputStream();


        //Hooks
        fullNameField = (TextInputLayout) findViewById(R.id.full_name);


        imgError = (Button) findViewById(R.id.img_error);


        fullnameTV = (TextView) findViewById(R.id.fullname_tv);
        fullnameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFullName.setVisibility(View.VISIBLE);
                fullNameField.setVisibility(View.VISIBLE);
                fullnameTV.setVisibility(View.INVISIBLE);

            }
        });


        phoneTV = (TextView) findViewById(R.id.phone_tv);

        userPhoto = (ImageView) findViewById(R.id.profile_img);
        userPhoto.setClipToOutline(true);


        userPhotoPicker = (Button) findViewById(R.id.profile_image_picker);
        userPhotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        savePhoto = (Button) findViewById(R.id.savePhoto);
        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePhoto();
                savePhoto.setVisibility(View.INVISIBLE);
            }
        });


        saveFullName = (Button) findViewById(R.id.saveFullname);
        saveFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateFullName();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");


        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSLOGIN);
        if (sessionManager.checkLogin()) {
            HashMap<String, String> userDetails = sessionManager.getUsersDetailFromSession();

            fullNameFromDB = userDetails.get(SessionManager.KEY_FULLNAME);
            phoneFromDB = userDetails.get(SessionManager.KEY_PHONENUMBER);
            n_avatarUrl = userDetails.get(SessionManager.KEY_AVATARURL);


            Glide.with(UserProfile.this).load(n_avatarUrl).placeholder(R.drawable.field_username_icon).into(userPhoto);
            fullnameTV.setText(fullNameFromDB);
            phoneTV.setText(phoneFromDB);
            fullNameField.getEditText().setText(fullNameFromDB);
        }


        // Recycler Function Calls
        manageRecycler();
        myLikesRecycler();

    }

    private void UpdatePhoto() {


        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if (!validateImageSize()) {
            return;
        }


        StorageReference fileReference = mStorageRef
                .child(phoneFromDB + ".jpeg");

        fileReference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                n_avatarUrl = uri.toString();

                                reference.child(phoneFromDB).child("avatarUrl").setValue(n_avatarUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(UserProfile.this, "Profile Image Updated!", Toast.LENGTH_SHORT).show();

                                        Glide.with(UserProfile.this).load(n_avatarUrl).placeholder(R.drawable.field_username_icon).into(userPhoto);

                                        SessionManager sessionManager = new SessionManager(UserProfile.this, SessionManager.SESSION_USERSLOGIN);
                                        sessionManager.createLoginSession(phoneFromDB, fullNameFromDB, n_avatarUrl);

                                    }
                                });


                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void UpdateFullName() {


        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if (!validateFullName()) {
            return;
        }


        String n_fullName = fullNameField.getEditText().getText().toString();


        if (!fullNameFromDB.equals(n_fullName)) {
            reference.child(phoneFromDB).child("fullName").setValue(n_fullName).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(UserProfile.this, "Full Name Updated!", Toast.LENGTH_SHORT).show();
                    fullnameTV.setText(n_fullName);
                    fullNameFromDB = n_fullName;

                    SessionManager sessionManager = new SessionManager(UserProfile.this, SessionManager.SESSION_USERSLOGIN);
                    sessionManager.createLoginSession(phoneFromDB, n_fullName, n_avatarUrl);


                    saveFullName.setVisibility(View.INVISIBLE);
                    fullNameField.setVisibility(View.INVISIBLE);
                    fullnameTV.setVisibility(View.VISIBLE);

                }
            });
        }


    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }


    public static class UtilClassName {
        public static int getFileSize(Uri imageUri, Activity activity) {
            int kb_size = 0;
            try {
                InputStream is = activity.getContentResolver().openInputStream(imageUri);
                int byte_size = is.available();
                kb_size = byte_size / 1024;
                Toast.makeText(activity, "Image size: " + String.valueOf(kb_size) + "kb", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return kb_size;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();

            try {
                Bitmap placeBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                placeBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            } catch (IOException e) {
                e.printStackTrace();
            }


            Glide.with(this).load(mImageUri).placeholder(R.drawable.field_username_icon).into(userPhoto);

            if (UtilClassName.getFileSize(mImageUri, this) <= 1024) {
                Toast.makeText(this, "Image Size Ok", Toast.LENGTH_SHORT).show();
                imgError.setVisibility(View.INVISIBLE);
                savePhoto.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Image is too large" + "\n" + "Maximum image file size allowed: 1Mb", Toast.LENGTH_SHORT).show();
                imgError.setVisibility(View.VISIBLE);
                savePhoto.setVisibility(View.INVISIBLE);
            }


        }
    }


    private void manageRecycler() {

        RecyclerView manageRV = (RecyclerView) findViewById(R.id.myplaces_recycler);
        manageRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        com.google.firebase.firestore.Query query = placesRef.whereEqualTo("owner", phoneFromDB).limit(5);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.getResult().size() == 0) {
                    manageRV.setVisibility(View.GONE);
                } else {
                    FirestoreRecyclerOptions<fsPlace> options =
                            new FirestoreRecyclerOptions.Builder<fsPlace>()
                                    .setQuery(query, fsPlace.class)
                                    .build();

                    manageAdapter = new fsAdapter(UserProfile.this, options);
                    manageRV.setAdapter(manageAdapter);
                    manageAdapter.startListening();
                }
            }
        });


    }

    private void myLikesRecycler() {

        RecyclerView myLikesRV = (RecyclerView) findViewById(R.id.mylikes_recycler);
        myLikesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        com.google.firebase.firestore.Query query = placesRef.whereArrayContains("likes", phoneFromDB).limit(5);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.getResult().size() == 0) {
                    myLikesRV.setVisibility(View.GONE);
                } else {
                    FirestoreRecyclerOptions<fsPlace> options =
                            new FirestoreRecyclerOptions.Builder<fsPlace>()
                                    .setQuery(query, fsPlace.class)
                                    .build();

                    myLikesAdapter = new fsAdapter(UserProfile.this, options);
                    myLikesRV.setAdapter(myLikesAdapter);
                    myLikesAdapter.startListening();
                }
            }
        });

    }

    @Override
    protected void onRestart() {

        if (manageAdapter != null) {
            manageAdapter.startListening();
        }

        if (myLikesAdapter != null) {
            myLikesAdapter.startListening();
        }

        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (manageAdapter != null) {
            manageAdapter.stopListening();
        }

        if (myLikesAdapter != null) {
            myLikesAdapter.stopListening();
        }
    }


    // validation Functions

    private boolean validateImageSize() {

        if (Place_Activity.UtilClassName.getFileSize(mImageUri, this) >= 1024) {
            Toast.makeText(this, "Image is too large" + "\n" + "Maximum image file size allowed: 1Mb", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(this, "Image size OK", Toast.LENGTH_SHORT).show();
            return true;
        }

    }


    private boolean validateFullName() {
        String val = fullNameField.getEditText().getText().toString();

        if (val.isEmpty()) {
            fullNameField.setError(getText(R.string.val_not_empty));
            fullNameField.requestFocus();
            return false;
        } else if (val.length() > 17) {
            fullNameField.setError(getText(R.string.val_too_large));
            return false;
        } else {
            fullNameField.setError(null);
            fullNameField.setErrorEnabled(false);
            return true;
        }
    }


    //Custom Dialog for internet check
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        startActivity(new Intent(UserProfile.this, UserDashboard.class));
                        finishAffinity();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


}