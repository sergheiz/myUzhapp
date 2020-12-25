package com.example.cityguide.Common.Categories.FoodAndDrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));
        lstPlace.add(new Place("Chicken Hut","Categorie Book","Description book",R.drawable.chicken_hut_photo));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.food_and_drink_recycler);
        CategoriesRecyclerAdapter myAdapter = new CategoriesRecyclerAdapter(this,lstPlace);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);

    }
}