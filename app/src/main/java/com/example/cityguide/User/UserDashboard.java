package com.example.cityguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cityguide.Common.Categories.AllCategories;
import com.example.cityguide.Common.Categories.Transport.TransportCategory;
import com.example.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.cityguide.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.example.cityguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.cityguide.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.example.cityguide.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables

    static final float END_SCALE = 0.7f;

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4, gradient5;
    private Animation animation;
    ImageView menuIcon;
    LinearLayout contentView;

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
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slid_animation);

        //Set animation to elements
        contentView.setAnimation(animation);

        navigationDrawer();


        // Recycler Function Calls
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
    }

    //Navigation drawer Functions
    private void navigationDrawer() {
        //Navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        //drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
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

        switch (item.getItemId()) {

            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));
                break;
        }


        return true;
    }


    //Recycler functions
    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.chickenhut_img, "Chicken Hut", "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.hodynka_img, "Hodynka", "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.castle_img, "Uzhhorod Castle", "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();

        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.chickenhut_img, "Chicken Hut", "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.hodynka_img, "Hodynka", "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good."));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.castle_img, "Uzhhorod Castle", "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork."));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);


    }

    private void categoriesRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xffd4cbe5, 0xfffff});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xff7adccf, 0xfffff});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xfff7c59f, 0xfffff});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xffb8d7f5, 0xfffff});
        gradient5 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xffF1E9CE, 0xfffff});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.school_image, "Education", gradient1));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.hospital_image, "HOSPITAL", gradient2));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.restaurant_image, "Restaurant", gradient3));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.shopping_image, "Shopping", gradient4));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.transport_image, "Transport", gradient5));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    public void callRetailerScreens(View view){


        Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.retailer_startup), "transition_retailer");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());
    }

    public void callTransport(View view) {

        Intent intent = new Intent(getApplicationContext(), TransportCategory.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.transport_category_btn), "transition_dashboard_transport");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());

    }
}