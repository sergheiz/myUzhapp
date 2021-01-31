package com.example.cityguide.Common.Categories.FoodAndDrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.Fade;

import com.example.cityguide.Common.Categories.CategoryRecyclerAdapter;
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
        lstPlace.add(new Place("a","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("b","Hodynka Hodynka Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("c","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("d","Hodynka Hodynka Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("4","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("5","Hodynka Hodynka Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("6","Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        lstPlace.add(new Place("7","Hodynka Hodynka Hodynka",R.drawable.hodynka_photo, "Amazing selection of beers, good place to chill out and with great service. Also you will have a great view on River and city centre. Prices reasonable, service very good", "<a href=\"https://www.google.com/maps/search/?api=1&query=48.6223666,22.297354&query_place_id=ChIJ8-VCZrEZOUcRAUKb6_Ao6rM\">Show on Google maps</a>", "0"));
        RecyclerView fdRV = (RecyclerView) findViewById(R.id.food_and_drink_recycler);
        CategoryRecyclerAdapter myAdapter = new CategoryRecyclerAdapter(this,lstPlace);
        fdRV.setLayoutManager(new LinearLayoutManager(this));
        fdRV.setAdapter(myAdapter);

    }
}