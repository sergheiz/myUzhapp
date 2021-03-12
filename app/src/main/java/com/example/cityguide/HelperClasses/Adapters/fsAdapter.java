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
import com.google.android.material.card.MaterialCardView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class fsAdapter extends FirestoreRecyclerAdapter<fsPlace, fsAdapter.myviewholder> {

    private Context mContext;



    public fsAdapter(Context mContext, FirestoreRecyclerOptions<fsPlace> options) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull fsAdapter.myviewholder holder, int position, @NonNull fsPlace fsPlace) {
        holder.list_place_title.setText(fsPlace.getName());
        Glide.with(holder.list_place_img.getContext()).load(fsPlace.getImgurl()).into(holder.list_place_img);
        holder.list_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Place_Activity.class);

                // passing data to the Place activity
                intent.putExtra("Title", fsPlace.getName());
                intent.putExtra("Description", fsPlace.getDescription());
                intent.putExtra("MapLink", fsPlace.getMaplink());
                intent.putExtra("Group", fsPlace.getGroup());
                intent.putExtra("DocumentID", fsPlace.getDocumentId());

                intent.putExtra("Imgurl", fsPlace.getImgurl());
                // start the activity

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(v.findViewById(R.id.list_anim_view), "place_transition");
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
        TextView list_place_title, list_group;
        ImageView list_place_img;
        Button favBtn;
        MaterialCardView list_cardView;

        public myviewholder(@NonNull View itemView) {
            super(itemView);


            list_group = (TextView) itemView.findViewById(R.id.list_group_id);


            list_place_title = (TextView) itemView.findViewById(R.id.list_place_title);
            list_place_title.setHorizontallyScrolling(true);
            list_place_title.setSelected(true);


            list_place_img = (ImageView) itemView.findViewById(R.id.list_place_img);
            list_cardView = (MaterialCardView) itemView.findViewById(R.id.list_cardview);
        }
    }
}
