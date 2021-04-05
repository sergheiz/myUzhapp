package com.example.cityguide.LocationOwner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cityguide.HelperClasses.Adapters.fsAdapter;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class RetailerManageFragment extends Fragment {

    fsAdapter manageAdapter;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference placesRef = firebaseFirestore.collection("Places");

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_retailer_manage, container, false);

        String owner = mAuth.getCurrentUser().getPhoneNumber();

        RecyclerView manageRV = (RecyclerView) v.findViewById(R.id.myplaces_recycler);
        manageRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        Query query = placesRef.whereEqualTo("owner",owner);

        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();

        manageAdapter = new fsAdapter(getContext(), options);
        manageRV.setAdapter(manageAdapter);

        return v;





    }

    @Override
    public void onStart() {
        super.onStart();
        manageAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        manageAdapter.stopListening();

    }




}