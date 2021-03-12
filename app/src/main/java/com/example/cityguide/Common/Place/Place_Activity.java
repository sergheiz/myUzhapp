package com.example.cityguide.Common.Place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.transition.Fade;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.cityguide.HelperClasses.CheckInternet;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.LocationOwner.RetailerDashboard;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class Place_Activity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvmaplink;
    private EditText edititle, editdescription, editmaplink, editImgUrl;
    private ImageView img;
    private ImageView cancel, save, edit, like;
    private RadioGroup radioGroup;
    private RadioButton selectedGroup, food, resid, entert;
    private LinearLayout groupView;

    String Title, Group, MapLink, fullMapLink, Description, Imgurl, WhatToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        like = (ImageView) findViewById(R.id.like);
        edit = (ImageView) findViewById(R.id.edit);
        cancel = (ImageView) findViewById(R.id.cancel_edit);
        save = (ImageView) findViewById(R.id.save_edit);


        img = (ImageView) findViewById(R.id.img_thumbnail);
        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvmaplink = (TextView) findViewById(R.id.map_link);
        tvdescription = (TextView) findViewById(R.id.txtDesc);


        groupView = (LinearLayout) findViewById(R.id.group_layout);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        food = (RadioButton) findViewById(R.id.food);
        resid = (RadioButton) findViewById(R.id.resid);
        entert = (RadioButton) findViewById(R.id.entert);


        editImgUrl = (EditText) findViewById(R.id.edit_imgurl);
        edititle = (EditText) findViewById(R.id.edit_txttitle);
        editmaplink = (EditText) findViewById(R.id.edit_map_link);
        editdescription = (EditText) findViewById(R.id.edit_txtDesc);


        // Recieve data
        Intent intent = getIntent();
        int Thumbnail = intent.getExtras().getInt("Thumbnail");
        Imgurl = intent.getExtras().getString("Imgurl");

        WhatToDo = intent.getExtras().getString("WhatToDo");



        Title = intent.getExtras().getString("Title");
        MapLink = intent.getExtras().getString("MapLink");
        fullMapLink = "<a href=" + MapLink + ">Show on Google maps</a>";
        Description = intent.getExtras().getString("Description");
        Group = intent.getExtras().getString("Group");

        String FavStatus = intent.getExtras().getString("FavStatus");

        // Setting values
        if (Imgurl != null) {
            Glide.with(img.getContext()).load(Imgurl).placeholder(R.drawable.image_placeholder).into(img);
        } else {
            img.setImageResource(Thumbnail);

        }

        if (Group.equals("Food and Drink")) {
            food.setChecked(true);
        } else if (Group.equals("Residence")) {
            resid.setChecked(true);
        } else if (Group.equals("Entertainment")) {
            entert.setChecked(true);
        }

        tvtitle.setText(Title);
        tvtitle.setHorizontallyScrolling(true);
        tvtitle.setSelected(true);


        tvmaplink.setText(fullMapLink);
        tvmaplink.setClickable(true);
        tvmaplink.setMovementMethod(LinkMovementMethod.getInstance());
        tvmaplink.setText(Html.fromHtml(fullMapLink));

        tvdescription.setText(Description);


        //Edit fields

        edititle.setText(Title);

        editImgUrl.setText(Imgurl);

        editmaplink.setText(MapLink);

        editdescription.setText(Description);

        /**
         //Chech if user is the owner!!!!!!
         */

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            edit.setVisibility(View.VISIBLE);

        }

//        if (WhatToDo.equals("Create New Place")) {
//
//            edit.setVisibility(View.GONE);
//            like.setVisibility(View.GONE);
//            cancel.setVisibility(View.VISIBLE);
//            save.setVisibility(View.VISIBLE);
//
//            tvtitle.setVisibility(View.INVISIBLE);
//            tvmaplink.setVisibility(View.INVISIBLE);
//            tvdescription.setVisibility(View.INVISIBLE);
//
//
//            editImgUrl.setVisibility(View.VISIBLE);
//            edititle.setVisibility(View.VISIBLE);
//            editmaplink.setVisibility(View.VISIBLE);
//            editdescription.setVisibility(View.VISIBLE);
//
//        }


    }


    public void EditPlace(View view) {


        edit.setVisibility(View.GONE);
        like.setVisibility(View.GONE);
        cancel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);

        tvtitle.setVisibility(View.INVISIBLE);
        tvmaplink.setVisibility(View.INVISIBLE);
        tvdescription.setVisibility(View.INVISIBLE);


        editImgUrl.setVisibility(View.VISIBLE);
        edititle.setVisibility(View.VISIBLE);
        editmaplink.setVisibility(View.VISIBLE);
        editdescription.setVisibility(View.VISIBLE);
        groupView.setVisibility(View.VISIBLE);


    }

    //Custom Dialog for internet check
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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

    public void CancelEdit(View view) {

        Place_Activity.super.onBackPressed();
//                edit.setVisibility(View.VISIBLE);
//                like.setVisibility(View.VISIBLE);
//                cancel.setVisibility(View.GONE);
//                save.setVisibility(View.GONE);
//
//                tvtitle.setVisibility(View.VISIBLE);
//                tvmaplink.setVisibility(View.VISIBLE);
//                tvdescription.setVisibility(View.VISIBLE);
//
//                editImgUrl.setVisibility(View.GONE);
//                edititle.setVisibility(View.GONE);
//                editmaplink.setVisibility(View.GONE);
//                editdescription.setVisibility(View.GONE);


    }

    public void SaveData(View view) {


        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(getApplicationContext())) {
            showCustomDialog();
            return;
        }

        selectedGroup= findViewById(radioGroup.getCheckedRadioButtonId());
        String n_group = selectedGroup.getText().toString();


        String n_imgUrl = editImgUrl.getText().toString();
        String n_title = edititle.getText().toString();
        String n_maplink = editmaplink.getText().toString();
        String n_description = editdescription.getText().toString();

        if (!validateTitle() | !validateDescription() | !validateImgUrl() | !validateMapLink() | !validateGroup()) {
            return;
        }

        Query query = FirebaseFirestore.getInstance()
                .collection("Places").whereEqualTo("name", Title);

        if (!Imgurl.equals(n_imgUrl)) {

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        FirebaseFirestore.getInstance()
                                .collection("Places").document(documentSnapshot.getId())
                                .update("imgurl", n_imgUrl)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Imgurl = n_imgUrl;
                                        Glide.with(img.getContext()).load(Imgurl).placeholder(R.drawable.image_placeholder).into(img);
                                        editImgUrl.setText(Imgurl);
                                        Toast.makeText(getApplicationContext(), "Image Updated", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Image Update Failed" + e, Toast.LENGTH_LONG).show();

                                    }
                                });
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Not found" + e, Toast.LENGTH_LONG).show();

                        }
                    });
        }

        if (!Title.equals(n_title)) {

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        FirebaseFirestore.getInstance()
                                .collection("Places").document(documentSnapshot.getId())
                                .update("name", n_title)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Title = n_title;
                                        tvtitle.setText(Title);
                                        edititle.setText(Title);
                                        Toast.makeText(getApplicationContext(), "Title Updated", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Title Update Failed" + e, Toast.LENGTH_LONG).show();

                                    }
                                });
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Not found" + e, Toast.LENGTH_LONG).show();

                        }
                    });
        }

        if (!MapLink.equals(n_maplink)) {

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        FirebaseFirestore.getInstance()
                                .collection("Places").document(documentSnapshot.getId())
                                .update("maplink", n_maplink)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        MapLink = n_maplink;
                                        editmaplink.setText(MapLink);
                                        fullMapLink = "<a href=" + MapLink + ">Show on Google maps</a>";
                                        tvmaplink.setText(fullMapLink);
                                        tvmaplink.setClickable(true);
                                        tvmaplink.setMovementMethod(LinkMovementMethod.getInstance());
                                        tvmaplink.setText(Html.fromHtml(fullMapLink));
                                        Toast.makeText(getApplicationContext(), "MapLink Updated", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "MapLink Update Failed" + e, Toast.LENGTH_LONG).show();

                                    }
                                });
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Not found" + e, Toast.LENGTH_LONG).show();

                        }
                    });
        }

        if (!Description.equals(n_description)) {


            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        FirebaseFirestore.getInstance()
                                .collection("Places").document(documentSnapshot.getId())
                                .update("description", n_description)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Description = n_description;
                                        tvdescription.setText(Description);
                                        editdescription.setText(Description);
                                        Toast.makeText(getApplicationContext(), "Description Updated", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Description Update Failed" + e, Toast.LENGTH_LONG).show();

                                    }
                                });
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Not found" + e, Toast.LENGTH_LONG).show();

                        }
                    });
        }

        if (!Group.equals(n_group)) {


            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        FirebaseFirestore.getInstance()
                                .collection("Places").document(documentSnapshot.getId())
                                .update("group", n_group)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Group = n_group;
                                        if (Group.equals("Food and Drink")) {
                                            food.setChecked(true);
                                        } else if (Group.equals("Residence")) {
                                            resid.setChecked(true);
                                        } else if (Group.equals("Entertainment")) {
                                            entert.setChecked(true);
                                        }
                                        Toast.makeText(getApplicationContext(), "Group Updated", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Description Update Failed" + e, Toast.LENGTH_LONG).show();

                                    }
                                });
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Not found" + e, Toast.LENGTH_LONG).show();

                        }
                    });
        }

        edit.setVisibility(View.VISIBLE);
        like.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        tvtitle.setVisibility(View.VISIBLE);
        tvmaplink.setVisibility(View.VISIBLE);
        tvdescription.setVisibility(View.VISIBLE);

        editImgUrl.setVisibility(View.INVISIBLE);
        edititle.setVisibility(View.INVISIBLE);
        editmaplink.setVisibility(View.INVISIBLE);
        editdescription.setVisibility(View.INVISIBLE);
        groupView.setVisibility(View.INVISIBLE);

    }


    private boolean validateTitle() {


        String val = edititle.getText().toString();

        if (val.isEmpty()) {
            edititle.setError(getText(R.string.val_not_empty));
            return false;
        } else if (val.length() < 4) {
            edititle.setError(getText(R.string.val_too_short));
            return false;
        } else {
            edititle.setError(null);
            return true;
        }
    }

    private boolean validateDescription() {


        String val = editdescription.getText().toString();

        if (val.isEmpty()) {
            editdescription.setError(getText(R.string.val_not_empty));
            return false;
        } else if (val.length() < 10) {
            editdescription.setError(getText(R.string.val_too_short));
            return false;
        } else {
            editdescription.setError(null);
            return true;
        }
    }

    private boolean validateImgUrl() {


        String val = editImgUrl.getText().toString().trim();

        if (val.isEmpty()) {
            editImgUrl.setError(getText(R.string.val_not_empty));
            return false;
        } else {
            editImgUrl.setError(null);
            return true;
        }
    }

    private boolean validateMapLink() {


        String val = editmaplink.getText().toString().trim();
        String start = val.substring(0, Math.min(val.length(), 18));

        if (val.isEmpty()) {
            editmaplink.setError(getText(R.string.val_not_empty));
            return false;
        } else if (!start.equals("https://goo.gl/map")) {
            editdescription.setError(getText(R.string.val_maplink));
            return false;
        } else {
            editmaplink.setError(null);
            return true;
        }
    }

    // FIX Crashed
    private boolean validateGroup() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getText(R.string.val_select), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }


}