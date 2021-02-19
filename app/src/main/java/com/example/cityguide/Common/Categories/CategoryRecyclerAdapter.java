package com.example.cityguide.Common.Categories;

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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.Common.Place.Place;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.R;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Place> mData;


    public CategoryRecyclerAdapter(Context mContext, List<Place> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_place,
                parent, false);
        return new MyViewHolder(view);

    }




    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        holder.img_place_thumbnail.setImageResource(mData.get(position).getPlaceThumbnail());
        holder.tv_place_title.setText(mData.get(position).getPlaceTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Place_Activity.class);

                // passing data to the Place activity
                intent.putExtra("KeyID", mData.get(position).getKeyID());
                intent.putExtra("Title", mData.get(position).getPlaceTitle());
                intent.putExtra("Thumbnail", mData.get(position).getPlaceThumbnail());
                intent.putExtra("Description", mData.get(position).getPlaceDescription());
                intent.putExtra("MapLink", mData.get(position).getPlaceMapLink());
                intent.putExtra("FavStatus", mData.get(position).getPlaceFavStatus());
                // start the activity

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(v.findViewById(R.id.cardview_place), "place_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
                mContext.startActivity(intent, options.toBundle());


            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_place_title;
        ImageView img_place_thumbnail;
        Button favBtn;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_place_title = (TextView) itemView.findViewById(R.id.place_title_id);
            tv_place_title.setHorizontallyScrolling(true);
            tv_place_title.setSelected(true);


            img_place_thumbnail = (ImageView) itemView.findViewById(R.id.place_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_place);




        }
    }





}
