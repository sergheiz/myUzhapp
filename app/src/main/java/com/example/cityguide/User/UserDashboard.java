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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.cityguide.Common.Categories.AllCategories;
import com.example.cityguide.Common.Categories.Entertainment.MainEntertainment;
import com.example.cityguide.Common.Categories.FoodAndDrink.MainFoodAndDrink;
import com.example.cityguide.Common.Categories.MyFavorites.MyFavorites;
import com.example.cityguide.Common.Categories.Residence.MainResidence;
import com.example.cityguide.Common.Categories.Transport.TransportCategory;
import com.example.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.cityguide.Common.Place.Place;
import com.example.cityguide.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.example.cityguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.cityguide.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables

    static final float END_SCALE = 0.7f;

    RecyclerView  categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4, gradient5;
    ImageView menuIcon, expandIcon;
    RelativeLayout moreCategories;
    List<Place> lstPlace ;

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
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menuIcon = findViewById(R.id.menu_icon);

        expandIcon = findViewById(R.id.expand_more_categories);
        moreCategories = findViewById(R.id.more_lines);


        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);



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

        switch (item.getItemId()) {

            case R.id.nav_my_favorites:
                startActivity(new Intent(getApplicationContext(), MyFavorites.class));
                break;
        }

        return true;

    }


    //Recycler functions
    private void featuredRecycler() {

        lstPlace = new ArrayList<>();
        lstPlace.add(new Place("0","Chicken Hut", R.drawable.chicken_hut_photo, "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on", "<a href=\"https://goo.gl/maps/zUaAC1J9KnFrCXtbA\">Show on Google maps</a>","0"));
        lstPlace.add(new Place("1","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("2","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("3","Chicken Hut", R.drawable.chicken_hut_photo, "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on", "<a href=\"https://goo.gl/maps/zUaAC1J9KnFrCXtbA\">Show on Google maps</a>","0"));
        lstPlace.add(new Place("4","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("5","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("6","Chicken Hut", R.drawable.chicken_hut_photo, "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on", "<a href=\"https://goo.gl/maps/zUaAC1J9KnFrCXtbA\">Show on Google maps</a>","0"));
        lstPlace.add(new Place("7","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("8","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));

        RecyclerView featRV = (RecyclerView) findViewById(R.id.featured_recycler);
        adapter = new FeaturedAdapter(this,lstPlace);
        featRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        featRV.setAdapter(adapter);
        featRV.setHasFixedSize(true);



        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }

    private void mostViewedRecycler() {

        lstPlace = new ArrayList<>();
        lstPlace.add(new Place("0","Chicken Hut", R.drawable.chicken_hut_photo, "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on", "<a href=\"https://goo.gl/maps/zUaAC1J9KnFrCXtbA\">Show on Google maps</a>","0"));
        lstPlace.add(new Place("1","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("2","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("3","Chicken Hut", R.drawable.chicken_hut_photo, "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on", "<a href=\"https://goo.gl/maps/zUaAC1J9KnFrCXtbA\">Show on Google maps</a>","0"));
        lstPlace.add(new Place("4","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("5","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("6","Chicken Hut", R.drawable.chicken_hut_photo, "Yummy fast foods. In uzhhorod city there is no KFC or McDonald... So this this the only fast food chain shop to rely on", "<a href=\"https://goo.gl/maps/zUaAC1J9KnFrCXtbA\">Show on Google maps</a>","0"));
        lstPlace.add(new Place("7","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("8","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));

        RecyclerView mvRV = (RecyclerView) findViewById(R.id.most_viewed_recycler);
        adapter = new MostViewedAdapter(this,lstPlace);
        mvRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mvRV.setAdapter(adapter);
        mvRV.setHasFixedSize(true);


    }

    private void categoriesRecycler() {

        //All Gradients
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xffd4cbe5, 0xfffff});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xff7adccf, 0xfffff});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xfff7c59f, 0xfffff});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xffb8d7f5, 0xfffff});
        gradient5 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xffF1E9CE, 0xfffff});


        ArrayList<CategoriesHelperClass> Categories = new ArrayList<>();
        Categories.add(new CategoriesHelperClass(R.drawable.school_image, "Education", gradient1));
        Categories.add(new CategoriesHelperClass(R.drawable.hospital_image, "HOSPITAL", gradient2));
        Categories.add(new CategoriesHelperClass(R.drawable.restaurant_image, "Restaurant", gradient3));
        Categories.add(new CategoriesHelperClass(R.drawable.shopping_image, "Shopping", gradient4));
        Categories.add(new CategoriesHelperClass(R.drawable.transport_image, "Transport", gradient5));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(Categories);
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


    public void moreCategories(View view) {
        moreCategories.setVisibility(View.VISIBLE);
        expandIcon.setVisibility(View.GONE);
    }

    public void lessCategories(View view) {
        moreCategories.setVisibility(View.GONE);
        expandIcon.setVisibility(View.VISIBLE);
    }


    public void callFoodAndDrink(View view) {

        Intent intent = new Intent(getApplicationContext(), MainFoodAndDrink.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.card1), "transition_dashboard_food_and_drink");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());

    }


    public void callResidence(View view) {

        Intent intent = new Intent(getApplicationContext(), MainResidence.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.card2), "transition_residence");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());


    }

    public void callTransport(View view) {

        Intent intent = new Intent(getApplicationContext(), TransportCategory.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.card3), "transition_dashboard_transport");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());

    }


    public void callEntertainment(View view) {

        Intent intent = new Intent(getApplicationContext(), MainEntertainment.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.card4), "transition_entertainment");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
        startActivity(intent, options.toBundle());

    }
}