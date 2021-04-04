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
import com.example.cityguide.HelperClasses.Models.Place;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.List;

public class FeaturedAdapter extends FirestoreRecyclerAdapter<fsPlace, FeaturedAdapter.featuredviewholder> {

    private Context mContext;


    public FeaturedAdapter(Context mContext, FirestoreRecyclerOptions<fsPlace> options) {
        super(options);
        this.mContext = mContext;

    }

    @Override
    protected void onBindViewHolder(@NonNull FeaturedAdapter.featuredviewholder holder, int position, @NonNull fsPlace fsPlace) {
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

        holder.likefeat.setOnClickListener(new View.OnClickListener() {
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

                            holder.likefeat.setVisibility(View.INVISIBLE);
                            holder.dlikefeat.setVisibility(View.VISIBLE);

                        }

                    }
                });

            }
        });

        holder.dlikefeat.setOnClickListener(new View.OnClickListener() {
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

                            holder.likefeat.setVisibility(View.VISIBLE);
                            holder.dlikefeat.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });

        if (mAuth == null) {
            holder.likefeat.setClickable(false);
            holder.dlikefeat.setClickable(false);
        } else {
            holder.likefeat.setClickable(true);
            holder.dlikefeat.setClickable(true);
        }


        if (likers.contains(currentUserPhone)) {
            holder.likefeat.setVisibility(View.INVISIBLE);
            holder.dlikefeat.setVisibility(View.VISIBLE);
        } else {
            holder.likefeat.setVisibility(View.VISIBLE);
            holder.dlikefeat.setVisibility(View.INVISIBLE);
        }


        holder.likes_count.setText(String.valueOf(likes_num));
        holder.feat_title.setText(fsPlace.getName());
        Glide.with(holder.feat_img.getContext()).load(fsPlace.getImgurl()).into(holder.feat_img);
        holder.feat_card.setOnClickListener(new View.OnClickListener() {
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
                pairs[0] = new Pair(v.findViewById(R.id.f_anim_id), "place_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
                mContext.startActivity(intent, options.toBundle());


            }
        });
    }

    @NonNull
    @Override
    public FeaturedAdapter.featuredviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);
        return new FeaturedAdapter.featuredviewholder(view);
    }

    class featuredviewholder extends RecyclerView.ViewHolder {
        TextView feat_title, feat_group, likes_count;
        ImageView feat_img;
        Button likefeat, dlikefeat;
        MaterialCardView feat_card;


        public featuredviewholder(@NonNull View itemView) {
            super(itemView);


            likefeat = itemView.findViewById(R.id.like_btn_feat);
            dlikefeat = itemView.findViewById(R.id.dlike_btn_feat);

            feat_group = (TextView) itemView.findViewById(R.id.feat_group_id);
            likes_count = (TextView) itemView.findViewById(R.id.likes_count);


            feat_title = (TextView) itemView.findViewById(R.id.feat_place_title_id);
            feat_title.setHorizontallyScrolling(true);
            feat_title.setSelected(true);


            feat_img = (ImageView) itemView.findViewById(R.id.feat_place_img_id);
            feat_card = (MaterialCardView) itemView.findViewById(R.id.cardview_featured_place);


        }
    }
}
