package com.example.cityguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.Common.Transport.TransportActivityMain;
import com.example.cityguide.HelperClasses.Adapters.FeaturedAdapter;
import com.example.cityguide.HelperClasses.Adapters.dbAdapter;
import com.example.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.cityguide.HelperClasses.Adapters.fsAdapter;
import com.example.cityguide.HelperClasses.Models.Place;
import com.example.cityguide.HelperClasses.Models.dbPlace;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.HelperClasses.SessionManager;
import com.example.cityguide.HelperClasses.Adapters.CategoriesAdapter;
import com.example.cityguide.HelperClasses.Models.Category;
import com.example.cityguide.HelperClasses.Adapters.MostViewedAdapter;
import com.example.cityguide.LocationOwner.RetailerDashboard;
import com.example.cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables


    FeaturedAdapter featuredAdapter;

    fsAdapter foodAdapter, residAdapter, entertAdapter, mvAdapter, updatedAdapter;

    ImageView menuIcon;
    ScrollView scrollViewMain;
    RelativeLayout card1, card1x, food_and_drink, card2, card2x, residence, card4, card4x, entertainment;
    Button likefeat, dlikefeat;


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference placesRef = firebaseFirestore.collection("Places");


    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        //Hooks
        likefeat = findViewById(R.id.like_btn_feat);
        dlikefeat = findViewById(R.id.dlike_btn_feat);

        card1 = findViewById(R.id.card1);
        card1x = findViewById(R.id.card1x);
        food_and_drink = findViewById(R.id.food_drink);


        card2 = findViewById(R.id.card2);
        card2x = findViewById(R.id.card2x);
        residence = findViewById(R.id.residence);


        card4 = findViewById(R.id.card4);
        card4x = findViewById(R.id.card4x);
        entertainment = findViewById(R.id.entertainment);

        menuIcon = findViewById(R.id.menu_icon);

        scrollViewMain = findViewById(R.id.main_scroll);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);





        navigationDrawer();


        // Recycler Function Calls
        featuredRecycler();
        mostViewedRecycler();
        updatedRecycler();


        foodRecycler();
        residenceRecycler();
        entertainmentRecycler();

    }

    @Override
    protected void onStart() {
        super.onStart();

        featuredAdapter.startListening();
        mvAdapter.startListening();
        updatedAdapter.startListening();

        foodAdapter.startListening();
        residAdapter.startListening();
        entertAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        featuredAdapter.stopListening();
        mvAdapter.stopListening();
        updatedAdapter.stopListening();

        foodAdapter.stopListening();
        residAdapter.stopListening();
        entertAdapter.stopListening();
    }


    private void featuredRecycler() {

        RecyclerView featuredRV = (RecyclerView) findViewById(R.id.featured_recycler);
        featuredRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Query query = placesRef.whereNotEqualTo("name",null).orderBy("name", Query.Direction.ASCENDING).limit(3);

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        featuredAdapter = new FeaturedAdapter(this, options);
        featuredRV.setAdapter(featuredAdapter);
        featuredRV.setHasFixedSize(true);

    }


    private void mostViewedRecycler() {

        RecyclerView mvRV = (RecyclerView) findViewById(R.id.most_viewed_recycler);
        mvRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Query query = placesRef.orderBy("name", Query.Direction.DESCENDING).limit(5);

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        mvAdapter = new fsAdapter(this, options);
        mvRV.setAdapter(mvAdapter);

    }

    private void updatedRecycler() {

        RecyclerView updRV = (RecyclerView) findViewById(R.id.updated_recycler);
        updRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Query query = placesRef.orderBy("updated", Query.Direction.DESCENDING).limit(5);

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        updatedAdapter = new fsAdapter(this, options);
        updRV.setAdapter(updatedAdapter);

    }


    private void foodRecycler() {

        RecyclerView foodRV = (RecyclerView) findViewById(R.id.food_and_drink_recycler);
        foodRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Food and Drink");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        foodAdapter = new fsAdapter(this, options);
        foodRV.setAdapter(foodAdapter);

    }


    private void residenceRecycler() {

        RecyclerView residRV = (RecyclerView) findViewById(R.id.residence_recycler);
        residRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Residence");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        residAdapter = new fsAdapter(this, options);
        residRV.setAdapter(residAdapter);

    }

    private void entertainmentRecycler() {

        RecyclerView entertRV = (RecyclerView) findViewById(R.id.entertainment_recycler);
        entertRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Entertainment");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        entertAdapter = new fsAdapter(this, options);
        entertRV.setAdapter(entertAdapter);
    }







    public void callRetailerScreens(View view) {


        Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.retailer_startup), "transition_retailer");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());
    }


    public void callFoodAndDrink(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.card1));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        food_and_drink.setVisibility(View.VISIBLE);
        residence.setVisibility(View.GONE);
        entertainment.setVisibility(View.GONE);
        card1x.setVisibility(View.VISIBLE);
        card2x.setVisibility(View.INVISIBLE);
        card4x.setVisibility(View.INVISIBLE);
        card1x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1x.setVisibility(View.INVISIBLE);
                card1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                food_and_drink.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);
            }
        });

    }


    public void callResidence(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.card2));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        food_and_drink.setVisibility(View.GONE);
        residence.setVisibility(View.VISIBLE);
        entertainment.setVisibility(View.GONE);
        card1x.setVisibility(View.INVISIBLE);
        card2x.setVisibility(View.VISIBLE);
        card4x.setVisibility(View.INVISIBLE);
        card2x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card2x.setVisibility(View.INVISIBLE);
                card2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                residence.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);

            }
        });


    }

    public void callTransport(View view) {

        Intent intent = new Intent(getApplicationContext(), TransportActivityMain.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.card3), "transition_dashboard_transport");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());

    }


    public void callEntertainment(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.card4));
        food_and_drink.setVisibility(View.GONE);
        residence.setVisibility(View.GONE);
        entertainment.setVisibility(View.VISIBLE);
        card1x.setVisibility(View.INVISIBLE);
        card2x.setVisibility(View.INVISIBLE);
        card4x.setVisibility(View.VISIBLE);
        card4x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card4x.setVisibility(View.INVISIBLE);
                card4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                entertainment.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);

            }
        });

    }


    //Navigation drawer Functions
    private void navigationDrawer() {
        //Navigation drawer


        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


        menuIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.nav_add_missing_place) {

            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            if (mAuth.getCurrentUser() != null) {

                Intent intent = new Intent(getApplicationContext(), Place_Activity.class);

                // passing data to the Place activity
                intent.putExtra("WhatToDo", "Create New Place");

                intent.putExtra("Title", "");
                intent.putExtra("Owner", "");
                intent.putExtra("Description", "");
                intent.putExtra("Phone", "");
                intent.putExtra("MapLink", "");
                intent.putExtra("Group", "");
                intent.putExtra("Imgurl", "");
                intent.putExtra("Likers", "");
                intent.putExtra("LikesNum", 0);

                // start the activity
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(findViewById(R.id.nav_add_missing_place), "place_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(findViewById(R.id.nav_add_missing_place), "transition_retailer");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
                startActivity(intent, options.toBundle());
            }



        }

        if (item.getItemId() == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
        }


        if (item.getItemId() == R.id.nav_logout) {
            SessionManager logout = new SessionManager(UserDashboard.this, SessionManager.SESSION_USERSLOGIN);
            logout.logoutUserFromSession();
            FirebaseAuth.getInstance().signOut();
//            ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
//            am.clearApplicationUserData();


            Toast.makeText(UserDashboard.this, "Logout Success ", Toast.LENGTH_SHORT).show();


        }

        return true;

    }



}