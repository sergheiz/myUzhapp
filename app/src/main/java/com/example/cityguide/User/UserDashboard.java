package com.example.cityguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
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
import com.example.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.cityguide.HelperClasses.Adapters.fsAdapter;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.HelperClasses.SessionManager;
import com.example.cityguide.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables


    FeaturedAdapter featuredAdapter;

    fsAdapter card1Adapter, card2Adapter, card4Adapter, card5Adapter, card6Adapter, mlAdapter, updatedAdapter;

    ImageView menuIcon;
    ScrollView scrollViewMain;
    RelativeLayout card1, card1x, card1rl, card2, card2x, card2rl, card4, card4x, card4rl, card5, card5x, card5rl, card6, card6x, card6rl;



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

        card1 = findViewById(R.id.card1);
        card1x = findViewById(R.id.card1x);
        card1rl = findViewById(R.id.card1rl);


        card2 = findViewById(R.id.card2);
        card2x = findViewById(R.id.card2x);
        card2rl = findViewById(R.id.card2rl);


        card4 = findViewById(R.id.card4);
        card4x = findViewById(R.id.card4x);
        card4rl = findViewById(R.id.card4rl);


        card5 = findViewById(R.id.card5);
        card5x = findViewById(R.id.card5x);
        card5rl = findViewById(R.id.card5rl);


        card6 = findViewById(R.id.card6);
        card6x = findViewById(R.id.card6x);
        card6rl = findViewById(R.id.card6rl);


        menuIcon = findViewById(R.id.menu_icon);

        scrollViewMain = findViewById(R.id.main_scroll);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);





        navigationDrawer();


        // Recycler Function Calls
        featuredRecycler();
        mostLikedRecycler();
        updatedRecycler();


        card1Recycler();
        card2Recycler();
        card4Recycler();
        card5Recycler();
        card6Recycler();

    }

    @Override
    protected void onStart() {
        super.onStart();

        featuredAdapter.startListening();
        mlAdapter.startListening();
        updatedAdapter.startListening();

        card1Adapter.startListening();
        card2Adapter.startListening();
        card4Adapter.startListening();
        card5Adapter.startListening();
        card6Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        featuredAdapter.stopListening();
        mlAdapter.stopListening();
        updatedAdapter.stopListening();

        card1Adapter.stopListening();
        card2Adapter.stopListening();
        card4Adapter.stopListening();
        card5Adapter.stopListening();
        card6Adapter.stopListening();
    }


    private void featuredRecycler() {

        RecyclerView featuredRV = (RecyclerView) findViewById(R.id.featured_recycler);
        featuredRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Query query = placesRef.whereEqualTo("featured","yes").limit(5);

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        featuredAdapter = new FeaturedAdapter(this, options);
        featuredRV.setAdapter(featuredAdapter);
        featuredRV.setHasFixedSize(true);

    }


    private void mostLikedRecycler() {

        RecyclerView mlRV = (RecyclerView) findViewById(R.id.most_liked_recycler);
        mlRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Query query = placesRef.orderBy( String.valueOf("likesNum"), Query.Direction.DESCENDING).limit(10);

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        mlAdapter = new fsAdapter(this, options);
        mlRV.setAdapter(mlAdapter);

    }

    private void updatedRecycler() {

        RecyclerView updRV = (RecyclerView) findViewById(R.id.updated_recycler);
        updRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Query query = placesRef.orderBy("updated", Query.Direction.DESCENDING).limit(10);

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        updatedAdapter = new fsAdapter(this, options);
        updRV.setAdapter(updatedAdapter);

    }


    private void card1Recycler() {

        RecyclerView card1RV = (RecyclerView) findViewById(R.id.card1_recycler);
        card1RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Food and Drink");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        card1Adapter = new fsAdapter(this, options);
        card1RV.setAdapter(card1Adapter);

    }


    private void card2Recycler() {

        RecyclerView card2RV = (RecyclerView) findViewById(R.id.card2_recycler);
        card2RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Residence");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        card2Adapter = new fsAdapter(this, options);
        card2RV.setAdapter(card2Adapter);

    }

    private void card4Recycler() {

        RecyclerView card4RV = (RecyclerView) findViewById(R.id.card4_recycler);
        card4RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Entertainment");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        card4Adapter = new fsAdapter(this, options);
        card4RV.setAdapter(card4Adapter);
    }

    private void card5Recycler() {

        RecyclerView card5RV = (RecyclerView) findViewById(R.id.card5_recycler);
        card5RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Shops");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        card5Adapter = new fsAdapter(this, options);
        card5RV.setAdapter(card5Adapter);
    }

    private void card6Recycler() {

        RecyclerView card6RV = (RecyclerView) findViewById(R.id.card6_recycler);
        card6RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("group", "Other");

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        card6Adapter = new fsAdapter(this, options);
        card6RV.setAdapter(card6Adapter);
    }







    public void callRetailerScreens(View view) {


        Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.retailer_startup), "transition_retailer");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());
    }


    public void callCard1(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.card1));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card5.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card6.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card1rl.setVisibility(View.VISIBLE);
        card2rl.setVisibility(View.GONE);
        card4rl.setVisibility(View.GONE);
        card5rl.setVisibility(View.GONE);
        card6rl.setVisibility(View.GONE);
        card1x.setVisibility(View.VISIBLE);
        card2x.setVisibility(View.INVISIBLE);
        card4x.setVisibility(View.INVISIBLE);
        card5x.setVisibility(View.INVISIBLE);
        card6x.setVisibility(View.INVISIBLE);
        card1x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1x.setVisibility(View.INVISIBLE);
                card1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                card1rl.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);
            }
        });

    }


    public void callCard2(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.card2));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card5.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card6.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card1rl.setVisibility(View.GONE);
        card2rl.setVisibility(View.VISIBLE);
        card4rl.setVisibility(View.GONE);
        card5rl.setVisibility(View.GONE);
        card6rl.setVisibility(View.GONE);
        card1x.setVisibility(View.INVISIBLE);
        card2x.setVisibility(View.VISIBLE);
        card4x.setVisibility(View.INVISIBLE);
        card5x.setVisibility(View.INVISIBLE);
        card6x.setVisibility(View.INVISIBLE);
        card2x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card2x.setVisibility(View.INVISIBLE);
                card2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                card2rl.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);

            }
        });


    }

    public void callCard3(View view) {

        Intent intent = new Intent(getApplicationContext(), TransportActivityMain.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.card3), "transition_dashboard_transport");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());

    }


    public void callCard4(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.card4));
        card5.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card6.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card1rl.setVisibility(View.GONE);
        card2rl.setVisibility(View.GONE);
        card4rl.setVisibility(View.VISIBLE);
        card5rl.setVisibility(View.GONE);
        card6rl.setVisibility(View.GONE);
        card1x.setVisibility(View.INVISIBLE);
        card2x.setVisibility(View.INVISIBLE);
        card4x.setVisibility(View.VISIBLE);
        card5x.setVisibility(View.INVISIBLE);
        card6x.setVisibility(View.INVISIBLE);
        card4x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card4x.setVisibility(View.INVISIBLE);
                card4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                card4rl.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);

            }
        });

    }

    public void callCard5(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card5.setBackgroundColor(ContextCompat.getColor(this, R.color.card5));
        card6.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card1rl.setVisibility(View.GONE);
        card2rl.setVisibility(View.GONE);
        card4rl.setVisibility(View.GONE);
        card5rl.setVisibility(View.VISIBLE);
        card6rl.setVisibility(View.GONE);
        card1x.setVisibility(View.INVISIBLE);
        card2x.setVisibility(View.INVISIBLE);
        card4x.setVisibility(View.INVISIBLE);
        card5x.setVisibility(View.VISIBLE);
        card6x.setVisibility(View.INVISIBLE);
        card5x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card5x.setVisibility(View.INVISIBLE);
                card5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                card5rl.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);

            }
        });
    }

    public void callCard6(View view) {

        scrollViewMain.setVisibility(View.GONE);

        card1.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card2.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card4.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card5.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        card6.setBackgroundColor(ContextCompat.getColor(this, R.color.card6));
        card1rl.setVisibility(View.GONE);
        card2rl.setVisibility(View.GONE);
        card4rl.setVisibility(View.GONE);
        card5rl.setVisibility(View.GONE);
        card6rl.setVisibility(View.VISIBLE);
        card1x.setVisibility(View.INVISIBLE);
        card2x.setVisibility(View.INVISIBLE);
        card4x.setVisibility(View.INVISIBLE);
        card5x.setVisibility(View.INVISIBLE);
        card6x.setVisibility(View.VISIBLE);
        card6x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card6x.setVisibility(View.INVISIBLE);
                card6.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                card6rl.setVisibility(View.GONE);
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

        if (item.getItemId() == R.id.nav_my_favorites) {
            startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
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