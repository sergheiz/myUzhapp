package com.example.cityguide.HelperClasses.HomeAdapter;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.Common.Place.Place;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.R;

import java.util.List;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MostViewedViewHolder> {

    private Context mContext;
    private List<Place> mData;

    public MostViewedAdapter(Context mContext, List<Place> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MostViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.most_viewed_card_design, parent, false);
        return new MostViewedAdapter.MostViewedViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MostViewedViewHolder holder, int position) {


        holder.image.setImageResource(mData.get(position).getPlaceThumbnail());
        holder.title.setText(mData.get(position).getPlaceTitle());
        holder.desc.setText(mData.get(position).getPlaceDescription());
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
                pairs[0] = new Pair(v.findViewById(R.id.most_viewed_card), "place_transition");
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

    public static class MostViewedViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, desc;
        CardView cardView;

        public MostViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.mv_image);

            title = itemView.findViewById(R.id.mv_title);
            title.setHorizontallyScrolling(true);
            title.setSelected(true);

            desc = itemView.findViewById(R.id.mv_desc);

            cardView = (CardView) itemView.findViewById(R.id.most_viewed_card);

        }
    }
}
