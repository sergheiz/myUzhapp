package com.example.cityguide.Common.Categories.FoodAndDrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;

import com.example.cityguide.Common.Categories.CategoriesRecyclerAdapter;
import com.example.cityguide.Common.Place.Place;
import com.example.cityguide.R;

import java.util.ArrayList;
import java.util.List;

public class MainFoodAndDrink extends AppCompatActivity {


    List<Place> lstPlace ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_food_and_drink);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        lstPlace = new ArrayList<>();
        lstPlace.add(new Place("Uzhhorod CastleUzhhorod CastleUzhhorod CastleUzhhorod Castle ", "<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod Castle ","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod CastleUzhhorod CastleUzhhorod CastleUzhhorod Castle ", "<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod Castle ","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod CastleUzhhorod CastleUzhhorod CastleUzhhorod Castle ", "<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod Castle ","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod CastleUzhhorod CastleUzhhorod CastleUzhhorod Castle ", "<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod Castle ","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod CastleUzhhorod CastleUzhhorod CastleUzhhorod Castle ", "<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod Castle ","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod CastleUzhhorod CastleUzhhorod CastleUzhhorod Castle ", "<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));
        lstPlace.add(new Place("Uzhhorod Castle ","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>", getResources().getString(R.string.test_place_description), R.drawable.uzh_castle_photo));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.food_and_drink_recycler);
        CategoriesRecyclerAdapter myAdapter = new CategoriesRecyclerAdapter(this,lstPlace);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);

    }
}