package com.example.cityguide.Common.Categories;

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

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.Common.Categories.FoodAndDrink.MainFoodAndDrink;
import com.example.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.cityguide.Common.Place.Place;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;

import java.util.List;

public class CategoriesRecyclerAdapter  extends RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Place> mData ;


    public CategoriesRecyclerAdapter(Context mContext, List<Place> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_place,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_place_title.setText(mData.get(position).getPlaceTitle());
        holder.img_place_thumbnail.setImageResource(mData.get(position).getPlaceThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Place_Activity.class);

                // passing data to the book activity
                intent.putExtra("Thumbnail",mData.get(position).getPlaceThumbnail());
                intent.putExtra("Title",mData.get(position).getPlaceTitle());
                intent.putExtra("MapLink",mData.get(position).getPlaceMapLink());
                intent.putExtra("Description",mData.get(position).getPlaceDescription());
                // start the activity

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(v.findViewById(R.id.cardview_place), "place_transition");
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_place_title;
        ImageView img_place_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_place_title = (TextView) itemView.findViewById(R.id.place_title_id) ;
            tv_place_title.setHorizontallyScrolling(true);
            tv_place_title.setSelected(true);


            img_place_thumbnail = (ImageView) itemView.findViewById(R.id.place_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_place);


        }
    }


}
