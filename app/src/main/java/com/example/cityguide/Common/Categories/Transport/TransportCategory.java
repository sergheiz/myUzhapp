package com.example.cityguide.Common.Categories.Transport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.transition.Fade;

import com.example.cityguide.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class TransportCategory extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_category);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        chipNavigationBar = findViewById(R.id.transport_menu);
        chipNavigationBar.setItemSelected(R.id.transport_menu_buses,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TransportBusesFragment()).commit();
        transportMenu();

    }

    private void transportMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.transport_menu_buses:
                        fragment = new TransportBusesFragment();
                        break;
                    case R.id.transport_menu_taxi:
                        fragment = new TransportTaxiFragment();
                        break;
                    case R.id.transport_menu_trains:
                        fragment = new TransportTrainsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });

    }


}