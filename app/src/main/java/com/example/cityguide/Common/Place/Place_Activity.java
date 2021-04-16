package com.example.cityguide.Common.Place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.transition.Fade;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Place_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView tvtitle, tvdescription, tvcall, tvmaplink, likes_count;
    private TextInputLayout edititle, editdescription, editcall, editmaplink;
    private ImageView img;
    private ImageView cancel, save, like, dlike, delete;
    private Button edit, img_select;
    private RadioGroup radioGroup;
    private RadioButton selectedGroup, food, resid, entert, shops, other;
    private LinearLayout groupView;
    private Uri mImageUri;


    String Title;
    String Owner;
    String Group;
    String Phone;
    String MapLink;
    String fullMapLink;
    String Description;
    String Imgurl;
    String n_imgUrl;
    String WhatToDo;
    String Likers;
    String currentUserPhone;
    int LikesNum, n_LikesNum;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageRef = storage.getReference("Place Images");

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference Places = FirebaseFirestore.getInstance().collection("Places");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        edit = (Button) findViewById(R.id.edit);
        img_select = (Button) findViewById(R.id.img_select_btn_id);
        like = (ImageView) findViewById(R.id.like);
        dlike = (ImageView) findViewById(R.id.dlike);
        cancel = (ImageView) findViewById(R.id.cancel_edit);
        save = (ImageView) findViewById(R.id.save_edit);
        delete = (ImageView) findViewById(R.id.delete_place);


        img = (ImageView) findViewById(R.id.img_thumbnail);
        tvtitle = (TextView) findViewById(R.id.txttitle);
        likes_count = (TextView) findViewById(R.id.likes_count);
        tvcall = (TextView) findViewById(R.id.call_number);
        tvmaplink = (TextView) findViewById(R.id.map_link);
        tvdescription = (TextView) findViewById(R.id.txtDesc);


        groupView = (LinearLayout) findViewById(R.id.group_layout);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        food = (RadioButton) findViewById(R.id.food);
        resid = (RadioButton) findViewById(R.id.resid);
        entert = (RadioButton) findViewById(R.id.entert);
        shops = (RadioButton) findViewById(R.id.shops);
        other = (RadioButton) findViewById(R.id.other);


        edititle = findViewById(R.id.edit_txttitle);
        editcall = findViewById(R.id.edit_call_number);
        editmaplink = findViewById(R.id.edit_map_link);
        editdescription = findViewById(R.id.edit_txtDesc);


        // Recieve data
        Intent intent = getIntent();
        int Thumbnail = intent.getExtras().getInt("Thumbnail");
        Imgurl = intent.getExtras().getString("Imgurl");
        WhatToDo = intent.getExtras().getString("WhatToDo");
        Title = intent.getExtras().getString("Title");
        Owner = intent.getExtras().getString("Owner");
        MapLink = intent.getExtras().getString("MapLink");
        Phone = intent.getExtras().getString("Phone");
        fullMapLink = "<a href=" + MapLink + ">Show on Google maps</a>";
        Description = intent.getExtras().getString("Description");
        Group = intent.getExtras().getString("Group");

        String FavStatus = intent.getExtras().getString("FavStatus");
        Likers = intent.getExtras().getString("Likers");
        LikesNum = intent.getExtras().getInt("LikesNum");

        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

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
        } else if (Group.equals("Shops")) {
            shops.setChecked(true);
        } else if (Group.equals("Other")) {
            other.setChecked(true);
        }

        tvtitle.setText(Title);
        tvtitle.setHorizontallyScrolling(true);
        tvtitle.setSelected(true);

        likes_count.setText(String.valueOf(LikesNum));


        tvcall.setText(Phone);
//        Linkify.addLinks(tvcall, Linkify.ALL);
        tvcall.setMovementMethod(LinkMovementMethod.getInstance());

        tvmaplink.setText(fullMapLink);
//        Linkify.addLinks(tvmaplink, Linkify.ALL);
        tvmaplink.setClickable(true);
        tvmaplink.setMovementMethod(LinkMovementMethod.getInstance());
        tvmaplink.setText(Html.fromHtml(fullMapLink));


        tvdescription.setText(Description);


        //Edit fields

        edititle.getEditText().setText(Title);


        editmaplink.getEditText().setText(MapLink);

        editcall.getEditText().setText(Phone);

        editdescription.getEditText().setText(Description);


        if (currentUser == null) {
            currentUserPhone = "null";
        } else {
            currentUserPhone = currentUser.getPhoneNumber();
            if (Likers.contains(currentUserPhone)) {
                like.setVisibility(View.INVISIBLE);
                dlike.setVisibility(View.VISIBLE);
            } else {
                like.setVisibility(View.VISIBLE);
                dlike.setVisibility(View.INVISIBLE);
            }
        }

        /**
         //Chech if user is the owner!!!!!!
         */
        if (currentUserPhone != "null") {

            if (currentUserPhone.equals(Owner)) {

                delete.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);

            }
        } else {
            like.setClickable(false);
            dlike.setClickable(false);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePlacedata();
            }
        });

        if (Title.equals("")) {
            edit.performClick();
        }


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    public static class UtilClassName{
        public static int getFileSize(Uri imageUri, Activity activity){
            int kb_size=0;
            try {
                InputStream is=activity.getContentResolver().openInputStream(imageUri);
                int byte_size=is.available();
                kb_size=byte_size/1024;
                Toast.makeText(activity, String.valueOf(kb_size) , Toast.LENGTH_SHORT).show();

            }
            catch (Exception e){
                // here you can handle exception here
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

            Glide.with(this).load(mImageUri).into(img);

            if(UtilClassName.getFileSize(mImageUri,this)<=500){
                Toast.makeText(this, "Image Size Ok", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Image is too large" + "\n" + "Maximum image file size allowed: 1Mb", Toast.LENGTH_LONG).show();
            }



        }
    }

    public void LikeHit(View view) {

        Query query = Places.whereEqualTo("name", Title);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                    Places.document(documentSnapshot.getId())
                            .update("likes", FieldValue.arrayUnion(currentUserPhone));

                    Places.document(documentSnapshot.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    ArrayList<String> likes_list = (ArrayList<String>) document.get("likes");

                                    like.setVisibility(View.INVISIBLE);
                                    dlike.setVisibility(View.VISIBLE);

                                    n_LikesNum = likes_list.size();
                                    likes_count.setText(String.valueOf(n_LikesNum));

                                    Places.document(documentSnapshot.getId())
                                            .update("likesNum", FieldValue.increment(1));

                                }
                            }
                        }
                    });


                }

            }
        });


    }

    public void dLikeHit(View view) {
        Query query = Places.whereEqualTo("name", Title);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                    Places.document(documentSnapshot.getId())
                            .update("likes", FieldValue.arrayRemove(currentUserPhone));

                    Places.document(documentSnapshot.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    ArrayList<String> likes_list = (ArrayList<String>) document.get("likes");

                                    like.setVisibility(View.VISIBLE);
                                    dlike.setVisibility(View.INVISIBLE);

                                    n_LikesNum = likes_list.size();
                                    likes_count.setText(String.valueOf(n_LikesNum));

                                    Places.document(documentSnapshot.getId())
                                            .update("likesNum", FieldValue.increment(-1));

                                }
                            }
                        }
                    });
                }

            }
        });


    }


    public void EditPlace(View view) {


        edit.setVisibility(View.GONE);
        like.setVisibility(View.INVISIBLE);
        dlike.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);

        tvtitle.setVisibility(View.INVISIBLE);
        tvmaplink.setVisibility(View.INVISIBLE);
        tvcall.setVisibility(View.INVISIBLE);
        likes_count.setVisibility(View.INVISIBLE);
        tvdescription.setVisibility(View.INVISIBLE);


        img_select.setVisibility(View.VISIBLE);
        edititle.setVisibility(View.VISIBLE);
        editmaplink.setVisibility(View.VISIBLE);
        editcall.setVisibility(View.VISIBLE);
        editdescription.setVisibility(View.VISIBLE);
        groupView.setVisibility(View.VISIBLE);


    }

    private void DeletePlacedata() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.delete_place) + Title + " ?")
                .setCancelable(true)
                .setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Query query = Places.whereEqualTo("name", Title);

                        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                    Places.document(documentSnapshot.getId())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    // Deleting image from Cloud Storage

                                                    StorageReference imgUrlRef = storage.getReferenceFromUrl(Imgurl);
                                                    imgUrlRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getApplicationContext(), "Deleted " + Title + " image!", Toast.LENGTH_LONG).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                            Toast.makeText(getApplicationContext(), "Failed to delete " + Title + " image " + e.getMessage(), Toast.LENGTH_LONG).show();


                                                        }
                                                    });


                                                    Toast.makeText(getApplicationContext(), "Place Data" + Title + " Deleted!", Toast.LENGTH_LONG).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), Title + " Failed Delete Data " + e.getMessage(), Toast.LENGTH_LONG).show();

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

                        finish();
                    }
                })
                .setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();


    }


    public void SaveData(View view) {


        //
        // FIND Solution to Rename/Replace image file on Cloud Storage when updating the Place Name
        //


        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(getApplicationContext())) {
            showCustomDialog();
            return;
        }


        if (!validateTitle() | !validateImageSize() | !validateDescription() | !validatePhone() | !validateMapLink() | !validateGroup()) {
            return;
        }

        selectedGroup = findViewById(radioGroup.getCheckedRadioButtonId());
        String n_group = selectedGroup.getText().toString();

        String n_title = edititle.getEditText().getText().toString().trim();
        String n_maplink = editmaplink.getEditText().getText().toString();
        String n_phone = editcall.getEditText().getText().toString();
        String n_description = editdescription.getEditText().getText().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String curdate = formatter.format(date);
        Toast.makeText(getApplicationContext(), curdate, Toast.LENGTH_SHORT).show();


        if (WhatToDo != null) {
            if (WhatToDo.equals("Create New Place")) {

                if (mImageUri != null) {

                    StorageReference fileReference = mStorageRef
                            .child(n_title + "." + getFileExtension(mImageUri));

                    fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            n_imgUrl = uri.toString();

                                            // Create a Map to store the data we want to set
                                            Map<String, Object> docData = new HashMap<>();
                                            docData.put("name", n_title);
                                            docData.put("group", n_group);
                                            docData.put("description", n_description);
                                            docData.put("imgurl", n_imgUrl);
                                            docData.put("maplink", n_maplink);
                                            docData.put("phone", n_phone);
                                            docData.put("owner", currentUserPhone);
                                            docData.put("updated", curdate);
                                            docData.put("likes", Arrays.asList());
                                            docData.put("likesNum", 0);
                                            docData.put("featured", "no");

                                            //add new document
                                            Places.document().set(docData);

                                            Toast.makeText(getApplicationContext(), "Place " + n_title + " has been created!", Toast.LENGTH_SHORT).show();

                                            finish();

                                        }
                                    });


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Place_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();

                }

            }
        } else {

            Query query = Places.whereEqualTo("name", Title);


            if (!Imgurl.equals(n_imgUrl)) {

                if (mImageUri != null) {

                    StorageReference fileReference = mStorageRef
                            .child(n_title + "." + getFileExtension(mImageUri));

                    fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            n_imgUrl = uri.toString();

                                            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                                        Places.document(documentSnapshot.getId())
                                                                .update("imgurl", n_imgUrl)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Glide.with(img.getContext()).load(n_imgUrl).placeholder(R.drawable.image_placeholder).into(img);
                                                                        Toast.makeText(getApplicationContext(), "Image Updated", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getApplicationContext(), "Image Update Failed" + e.getMessage(), Toast.LENGTH_LONG).show();

                                                                    }
                                                                });

                                                        Places.document(documentSnapshot.getId())
                                                                .update("updated", curdate);
                                                    }


                                                }
                                            });


                                        }
                                    });


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Place_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }

            }

            if (!Title.equals(n_title)) {

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                            Places.document(documentSnapshot.getId())
                                    .update("name", n_title)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Title = n_title;
                                            tvtitle.setText(Title);
                                            edititle.getEditText().setText(Title);
                                            RenameImageFile();
                                            Toast.makeText(getApplicationContext(), "Title Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Title Update Failed" + e.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    });

                            Places.document(documentSnapshot.getId())
                                    .update("updated", curdate);

                        }

                    }


                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), Title + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }

            if (!Phone.equals(n_phone)) {

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Places.document(documentSnapshot.getId())
                                    .update("phone", n_phone)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Phone = n_phone;
                                            editcall.getEditText().setText(Phone);
                                            tvcall.setText(Phone);
                                            tvcall.setMovementMethod(LinkMovementMethod.getInstance());
//                                            Linkify.addLinks(tvcall, Linkify.ALL);
                                            Toast.makeText(getApplicationContext(), "Phone Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Phone Update Failed" + e.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    });

                            Places.document(documentSnapshot.getId())
                                    .update("updated", curdate);

                        }

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), Title + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }

            if (!MapLink.equals(n_maplink)) {

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Places.document(documentSnapshot.getId())
                                    .update("maplink", n_maplink)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            MapLink = n_maplink;
                                            editmaplink.getEditText().setText(MapLink);
                                            fullMapLink = "<a href=" + MapLink + ">Show on Google maps</a>";
                                            tvmaplink.setText(fullMapLink);
//                                            Linkify.addLinks(tvmaplink, Linkify.ALL);
                                            tvmaplink.setClickable(true);
                                            tvmaplink.setMovementMethod(LinkMovementMethod.getInstance());
                                            tvmaplink.setText(Html.fromHtml(fullMapLink));
                                            Toast.makeText(getApplicationContext(), "MapLink Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "MapLink Update Failed" + e.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    });

                            Places.document(documentSnapshot.getId())
                                    .update("updated", curdate);

                        }

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), Title + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }

            if (!Description.equals(n_description)) {


                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Places.document(documentSnapshot.getId())
                                    .update("description", n_description)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Description = n_description;
                                            tvdescription.setText(Description);
                                            editdescription.getEditText().setText(Description);
                                            Toast.makeText(getApplicationContext(), "Description Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Description Update Failed" + e.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    });

                            Places.document(documentSnapshot.getId())
                                    .update("updated", curdate);

                        }

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), Title + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }

            if (!Group.equals(n_group)) {


                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Places.document(documentSnapshot.getId())
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
                                            } else if (Group.equals("Shops")) {
                                                shops.setChecked(true);
                                            } else if (Group.equals("Other")) {
                                                other.setChecked(true);
                                            }
                                            Toast.makeText(getApplicationContext(), "Group Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Group Update Failed" + e.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    });

                            Places.document(documentSnapshot.getId())
                                    .update("updated", curdate);

                        }

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), Title + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }
        }


        edit.setVisibility(View.VISIBLE);
        like.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        delete.setVisibility(View.VISIBLE);

        tvtitle.setVisibility(View.VISIBLE);
        tvmaplink.setVisibility(View.VISIBLE);
        tvcall.setVisibility(View.VISIBLE);
        tvdescription.setVisibility(View.VISIBLE);

        img_select.setVisibility(View.INVISIBLE);
        edititle.setVisibility(View.INVISIBLE);
        editmaplink.setVisibility(View.INVISIBLE);
        editcall.setVisibility(View.INVISIBLE);
        editdescription.setVisibility(View.INVISIBLE);
        groupView.setVisibility(View.INVISIBLE);

    }

    private void RenameImageFile() {

        Query query = Places.whereEqualTo("name", Title);

        StorageReference o_imgNameRef = storage.getReferenceFromUrl(Imgurl);
        o_imgNameRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Toast.makeText(getApplicationContext(), "Success Getting Bytes ", Toast.LENGTH_LONG).show();
                        StorageReference n_imgNameRef = mStorageRef
                                .child(Title + ".png");
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                        n_imgNameRef.putBytes(baos.toByteArray())
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getApplicationContext(), "Success Putting Bytes ", Toast.LENGTH_LONG).show();
                                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                                            Places.document(documentSnapshot.getId())
                                                                    .update("imgurl", uri.toString())
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Imgurl = uri.toString();
                                                                            Toast.makeText(getApplicationContext(), "Success writing new name url", Toast.LENGTH_LONG).show();
                                                                            o_imgNameRef.delete();
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(getApplicationContext(), "Failed writing new name url " + "/n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });

                                                        }

                                                    }
                                                });

                                            }
                                        })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Failed Putting Bytes " + "/n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed Getting Bytes " + Imgurl + "/n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private boolean validateImageSize() {


        if (UtilClassName.getFileSize(mImageUri,this)>=500){
            Toast.makeText(this, "Image is too large" + "\n" + "Maximum image file size allowed: 500kb", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(this, "Image size OK", Toast.LENGTH_SHORT).show();
            return true;
        }



    }

    private boolean validateTitle() {


        String val = edititle.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            edititle.setError(getText(R.string.val_not_empty));
            return false;
        } else if (val.length() < 2) {
            edititle.setError(getText(R.string.val_too_short));
            return false;
        } else {
            edititle.setError(null);
            edititle.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDescription() {


        String val = editdescription.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            editdescription.setError(getText(R.string.val_not_empty));
            return false;
        } else if (val.length() < 10) {
            editdescription.setError(getText(R.string.val_too_short));
            return false;
        } else {
            editdescription.setError(null);
            editdescription.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {


        String val = editcall.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            useOwnerPhoneDialog();
            editcall.setError(getText(R.string.val_not_empty));
            return false;

        } else {
            editcall.setError(null);
            editcall.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateMapLink() {


        String val = editmaplink.getEditText().getText().toString().trim();
        String start = val.substring(0, Math.min(val.length(), 8));

        if (val.isEmpty()) {
            editmaplink.setError(getText(R.string.val_not_empty));
            return false;
        } else if (!start.equals("https://")
//                | !val.contains("map")
        ) {
            editmaplink.setError(getText(R.string.val_maplink));
            return false;
        } else {
            editmaplink.setError(null);
            editmaplink.setErrorEnabled(false);
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
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        finishAffinity();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    //Custom Dialog for Owner phone use
    private void useOwnerPhoneDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.use_owner_phone) + Owner + " ?")
                .setCancelable(true)
                .setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editcall.getEditText().setText(Owner);
                    }
                })
                .setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    public void CancelEdit(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.cancel_edit))
                .setCancelable(true)
                .setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Place_Activity.super.onBackPressed();
                    }
                })
                .setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();


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


    public void LikersList(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.likers) + String.valueOf(Likers))
                .setCancelable(true)
                .setNegativeButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

}