package com.example.cityguide.Common.Place;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;

public class Place_Activity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvmaplink;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        img = (ImageView) findViewById(R.id.img_thumbnail);
        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvmaplink = (TextView) findViewById(R.id.map_link);
        tvdescription = (TextView) findViewById(R.id.txtDesc);

        // Recieve data
        Intent intent = getIntent();
        String KeyID = intent.getExtras().getString("KeyID");
        int Thumbnail = intent.getExtras().getInt("Thumbnail");
        String dbThumbnail = intent.getExtras().getString("dbThumbnail");
        String Title = intent.getExtras().getString("Title");
        String MapLink = intent.getExtras().getString("MapLink");
        String Category = intent.getExtras().getString("Category");
        String Description = intent.getExtras().getString("Description");
        String FavStatus = intent.getExtras().getString("FavStatus");

        // Setting values
        if (dbThumbnail != null){ Glide.with(this).load(dbThumbnail).into(img);}
        else {
            img.setImageResource(Thumbnail);
        }

        tvtitle.setText(Title);
        tvtitle.setHorizontallyScrolling(true);
        tvtitle.setSelected(true);


        tvmaplink.setText(MapLink);
        tvmaplink.setClickable(true);
        tvmaplink.setMovementMethod(LinkMovementMethod.getInstance());
        tvmaplink.setText(Html.fromHtml(MapLink));

        tvdescription.setText(Description);


    }
}