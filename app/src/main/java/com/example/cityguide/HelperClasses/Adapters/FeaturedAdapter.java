package com.example.cityguide.HelperClasses.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.HelperClasses.Models.Place;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {


    private Context mContext;
    private List<Place> mData;


    public FeaturedAdapter(Context mContext, List<Place> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design,
                parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {


        holder.image.setImageResource(mData.get(position).getPlaceThumbnail());
        holder.title.setText(mData.get(position).getPlaceTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Place_Activity.class);

                // passing data to the book activity
                intent.putExtra("Thumbnail", mData.get(position).getPlaceThumbnail());
                intent.putExtra("Title", mData.get(position).getPlaceTitle());
                intent.putExtra("MapLink", mData.get(position).getPlaceMapLink());
                intent.putExtra("Description", mData.get(position).getPlaceDescription());
                // start the activity

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(v.findViewById(R.id.cardview_featured_place), "place_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
                mContext.startActivity(intent, options.toBundle());

                //mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title;
        MaterialCardView cardView;


        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            //hooks
            image = itemView.findViewById(R.id.place_img_id);


            title = itemView.findViewById(R.id.place_title_id);
            title.setHorizontallyScrolling(true);
            title.setSelected(true);



            cardView = (MaterialCardView) itemView.findViewById(R.id.cardview_featured_place);

        }

    }


}
