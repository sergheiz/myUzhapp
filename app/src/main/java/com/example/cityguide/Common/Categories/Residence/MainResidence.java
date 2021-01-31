package com.example.cityguide.Common.Categories.Residence;

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

public class MainResidence extends AppCompatActivity {

    List<Place> lstPlace ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_residence);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        lstPlace = new ArrayList<>();
        lstPlace.add(new Place("0","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("1","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("2","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("3","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("4","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("5","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("6","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("7","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("8","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
        lstPlace.add(new Place("9","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));


        RecyclerView rrv = (RecyclerView) findViewById(R.id.residence_recycler);
        CategoryRecyclerAdapter myAdapter = new CategoryRecyclerAdapter(this,lstPlace);
        rrv.setLayoutManager(new LinearLayoutManager(this));
        rrv.setAdapter(myAdapter);

    }
}