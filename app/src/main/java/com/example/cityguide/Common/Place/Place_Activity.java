package com.example.cityguide.Common.Place;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.TransformationMethod;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.widget.ImageView;
import android.widget.TextView;

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
        tvdescription.setMovementMethod(LinkMovementMethod.getInstance());

        // Recieve data
        Intent intent = getIntent();
        int image = intent.getExtras().getInt("Thumbnail");
        String Title = intent.getExtras().getString("Title");
        String MapLink = intent.getExtras().getString("MapLink");
        String Description = intent.getExtras().getString("Description");

        // Setting values
        img.setImageResource(image);
        //img.setClipToOutline(true);

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