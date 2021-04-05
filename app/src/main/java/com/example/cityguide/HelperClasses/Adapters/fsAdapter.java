package com.example.cityguide.HelperClasses.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.HelperClasses.Models.dbPlace;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class fsAdapter extends FirestoreRecyclerAdapter<fsPlace, fsAdapter.myviewholder> {

    private Context mContext;

    /**
     //firestore recycler in fragmnet not updating when online
     */

    public fsAdapter(Context mContext, FirestoreRecyclerOptions<fsPlace> options) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull fsAdapter.myviewholder holder, int position, @NonNull fsPlace fsPlace) {
        int likes_num;
        List<String> likers = fsPlace.getLikes();
        likes_num = likers.size();
        String currentUserPhone;
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (mAuth == null) {
            currentUserPhone = null;
        } else {
            currentUserPhone = mAuth.getPhoneNumber();
        }


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = FirebaseFirestore.getInstance()
                        .collection("Places").whereEqualTo("name", fsPlace.getName());

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                            FirebaseFirestore.getInstance()
                                    .collection("Places").document(documentSnapshot.getId())
                                    .update("likes", FieldValue.arrayUnion(currentUserPhone));


                            holder.like.setVisibility(View.INVISIBLE);
                            holder.dlike.setVisibility(View.VISIBLE);
                        }

                    }
                });

            }
        });

        holder.dlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = FirebaseFirestore.getInstance()
                        .collection("Places").whereEqualTo("name", fsPlace.getName());

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                            FirebaseFirestore.getInstance()
                                    .collection("Places").document(documentSnapshot.getId())
                                    .update("likes", FieldValue.arrayRemove(currentUserPhone));

                            holder.like.setVisibility(View.VISIBLE);
                            holder.dlike.setVisibility(View.INVISIBLE);

                        }

                    }
                });

            }
        });

        if (mAuth == null) {
            holder.like.setClickable(false);
            holder.dlike.setClickable(false);
        } else {
            holder.like.setClickable(true);
            holder.dlike.setClickable(true);
        }

        if (likers.contains(currentUserPhone)) {
            holder.like.setVisibility(View.INVISIBLE);
            holder.dlike.setVisibility(View.VISIBLE);
        } else {
            holder.like.setVisibility(View.VISIBLE);
            holder.dlike.setVisibility(View.INVISIBLE);
        }

        holder.likes_count.setText(String.valueOf(likes_num));
        holder.list_place_title.setText(fsPlace.getName());
        Glide.with(holder.list_place_img.getContext()).load(fsPlace.getImgurl()).into(holder.list_place_img);
        holder.list_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, Place_Activity.class);

                // passing data to the Place activity
                intent.putExtra("Title", fsPlace.getName());
                intent.putExtra("Owner", fsPlace.getOwner());
                intent.putExtra("Description", fsPlace.getDescription());
                intent.putExtra("Phone", fsPlace.getPhone());
                intent.putExtra("MapLink", fsPlace.getMaplink());
                intent.putExtra("Group", fsPlace.getGroup());
                intent.putExtra("Imgurl", fsPlace.getImgurl());
                intent.putExtra("Likers", fsPlace.getLikes().toString());
                intent.putExtra("LikesNum", likes_num);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(v.findViewById(R.id.list_anim_id), "place_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
                mContext.startActivity(intent, options.toBundle());


            }
        });
    }

    @NonNull
    @Override
    public fsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_place, parent, false);
        return new myviewholder(view);
    }

    static class myviewholder extends RecyclerView.ViewHolder {
        TextView list_place_title, likes_count;
        ImageView list_place_img;
        Button like, dlike;
        MaterialCardView list_cardView;

        public myviewholder(@NonNull View itemView) {
            super(itemView);


            like = itemView.findViewById(R.id.like_btn);
            dlike = itemView.findViewById(R.id.dlike_btn);

            likes_count = (TextView) itemView.findViewById(R.id.likes_count);


            list_place_title = (TextView) itemView.findViewById(R.id.list_place_title);
            list_place_title.setHorizontallyScrolling(true);
            list_place_title.setSelected(true);


            list_place_img = (ImageView) itemView.findViewById(R.id.list_place_img);
            list_cardView = (MaterialCardView) itemView.findViewById(R.id.list_cardview);
        }
    }
}
