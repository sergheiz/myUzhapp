package com.example.cityguide.Common.Categories.Entertainment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.Fade;
import android.widget.Toast;

import com.example.cityguide.Common.Categories.CategoryRecyclerAdapter;
import com.example.cityguide.Common.LoginSignup.VerifyOTP;
import com.example.cityguide.Common.Place.Place;
import com.example.cityguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainEntertainment extends AppCompatActivity {

    List<Place> lstPlace ;
    RecyclerView entertRV;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayMan;

    DatabaseReference reference;
    FirebaseDatabase rootNode;
    String hodynka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_entertainment);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        hodynka = "Hodynka";

        rootNode = FirebaseDatabase.getInstance();
        reference  = rootNode.getReference().child("Places");


        lstPlace = new ArrayList<>();

        


        //Get data from Database
        Query getPlaceDB = reference.orderByChild("Title").equalTo(hodynka);
        getPlaceDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    assert hodynka != null;
                    String titleFromDB = snapshot.child(hodynka).child("Title").getValue(String.class);
                    String descrFromDB = snapshot.child(hodynka).child("Description").getValue(String.class);


                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
                    lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));


                }

                buildEntertainmentRV();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


// createEntertainmentList();



    }



//    public void createEntertainmentList() {
//
//        lstPlace = new ArrayList<>();
//        lstPlace.add(new Place("0",titleFromDB, R.drawable.uzh_castle_photo, String.valueOf(descrFromDB),"<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("1","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("2","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("3","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("4","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("5","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("6","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("7","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("8","Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://goo.gl/maps/Y6Q7Vqh8Z1TS1ctZ8\">Show on Google maps</a>","0" ));
//        lstPlace.add(new Place("9","Uzhhorod Castle Uzhhorod Castle Uzhhorod Castle", R.drawable.uzh_castle_photo, "Landmark stone castle housing multiple museums with collections of instruments, clothing & artwork","<a href=\"https://www.google.com/maps/search/?api=1&query=48.6245158,22.2890745&query_place_id=ChIJH7EjCboZOUcRIZPfUl0PQIg\">Show on Google maps</a>","0" ));
//
//    }

    public void buildEntertainmentRV() {

        entertRV = (RecyclerView) findViewById(R.id.entertainment_recycler);
        mAdapter = new CategoryRecyclerAdapter(this,lstPlace);
        mLayMan = new LinearLayoutManager(this);
        entertRV.setAdapter(mAdapter);
        entertRV.setLayoutManager(mLayMan);

    }
}