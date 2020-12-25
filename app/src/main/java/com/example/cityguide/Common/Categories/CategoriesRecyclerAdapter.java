package com.example.cityguide.Common.Categories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.Common.Place.Place;
import com.example.cityguide.Common.Place.Place_Activity;
import com.example.cityguide.R;

import java.util.List;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder> {

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

        holder.tv_book_title.setText(mData.get(position).getPlaceTitle());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getPlaceThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Place_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",mData.get(position).getPlaceTitle());
                intent.putExtra("Description",mData.get(position).getPlaceDescription());
                intent.putExtra("Thumbnail",mData.get(position).getPlaceThumbnail());
                // start the activity
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.place_title_id) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.place_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_place);


        }
    }


}
