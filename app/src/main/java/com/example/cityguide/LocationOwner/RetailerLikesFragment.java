package com.example.cityguide.LocationOwner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguide.HelperClasses.Adapters.fsAdapter;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;


public class RetailerLikesFragment extends Fragment implements View.OnClickListener {

    /**
     //firestore recycler in fragmnet not updating when online
     */

    fsAdapter likesAdapter;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference placesRef = firebaseFirestore.collection("Places");

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView title;
    RecyclerView likesRV;
    String owner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_retailer_likes, container, false);

        title = v.findViewById(R.id.mylikes_title);
        title.setOnClickListener(this::onClick);

        likesRV = (RecyclerView) v.findViewById(R.id.mylikes_recycler);
        owner = mAuth.getCurrentUser().getPhoneNumber();



        return v;


    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = placesRef.whereArrayContains("likes", owner).orderBy("updated", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<fsPlace> options =
                new FirestoreRecyclerOptions.Builder<fsPlace>()
                        .setQuery(query, fsPlace.class)
                        .build();
        likesAdapter = new fsAdapter(getContext(), options);
        likesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        likesRV.setAdapter(likesAdapter);

        likesAdapter.startListening();

    }


    @Override
    public void onStop() {
        super.onStop();

        likesAdapter.stopListening();

    }

    @Override
    public void onClick(View v) {


    }
}