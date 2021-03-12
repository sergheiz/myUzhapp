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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityguide.HelperClasses.Models.Place;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.HelperClasses.Models.fsPlace;
import com.example.cityguide.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FeaturedAdapter extends FirestoreRecyclerAdapter<fsPlace, FeaturedAdapter.featuredviewholder> {

    private Context mContext;



    public FeaturedAdapter(Context mContext, FirestoreRecyclerOptions<fsPlace> options) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull FeaturedAdapter.featuredviewholder holder, int position, @NonNull fsPlace fsPlace) {
        holder.feat_title.setText(fsPlace.getName());
        Glide.with(holder.feat_img.getContext()).load(fsPlace.getImgurl()).into(holder.feat_img);
        holder.feat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Place_Activity.class);

                // passing data to the Place activity
                intent.putExtra("Title", fsPlace.getName());
                intent.putExtra("Description", fsPlace.getDescription());
                intent.putExtra("MapLink", fsPlace.getMaplink());
                intent.putExtra("Group", fsPlace.getGroup());
                intent.putExtra("Imgurl", fsPlace.getImgurl());
                intent.putExtra("DocumentID", fsPlace.getDocumentId());
                // start the activity

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
        TextView feat_title, feat_group;
        ImageView feat_img;
        Button feat_favBtn;
        MaterialCardView feat_card;

        public featuredviewholder(@NonNull View itemView) {
            super(itemView);

            feat_group = (TextView) itemView.findViewById(R.id.feat_group_id);


            feat_title = (TextView) itemView.findViewById(R.id.feat_place_title_id);
            feat_title.setHorizontallyScrolling(true);
            feat_title.setSelected(true);


            feat_img = (ImageView) itemView.findViewById(R.id.feat_place_img_id);
            feat_card = (MaterialCardView) itemView.findViewById(R.id.cardview_featured_place);
        }
    }
}
//
//        extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {
//
//
//    private Context mContext;
//    private List<Place> mData;
//
//
//    public FeaturedAdapter(Context mContext, List<Place> mData) {
//        this.mContext = mContext;
//        this.mData = mData;
//    }
//
//    @NonNull
//    @Override
//    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design,
//                parent, false);
//        return new FeaturedViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
//
//
//        holder.image.setImageResource(mData.get(position).getPlaceThumbnail());
//        holder.title.setText(mData.get(position).getPlaceTitle());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext, Place_Activity.class);
//
//                // passing data to the book activity
//                intent.putExtra("Thumbnail", mData.get(position).getPlaceThumbnail());
//                intent.putExtra("Title", mData.get(position).getPlaceTitle());
//                intent.putExtra("MapLink", mData.get(position).getPlaceMapLink());
//                intent.putExtra("Description", mData.get(position).getPlaceDescription());
//                // start the activity
//
//                Pair[] pairs = new Pair[1];
//                pairs[0] = new Pair(v.findViewById(R.id.cardview_featured_place), "place_transition");
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
//                mContext.startActivity(intent, options.toBundle());
//
//                //mContext.startActivity(intent);
//
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//
//    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {
//
//
//        ImageView image;
//        TextView title;
//        MaterialCardView cardView;
//
//
//        public FeaturedViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            //hooks
//            image = itemView.findViewById(R.id.place_img_id);
//
//
//            title = itemView.findViewById(R.id.place_title_id);
//            title.setHorizontallyScrolling(true);
//            title.setSelected(true);
//
//
//
//            cardView = (MaterialCardView) itemView.findViewById(R.id.cardview_featured_place);
//
//        }
//
//    }
//
//
//}
