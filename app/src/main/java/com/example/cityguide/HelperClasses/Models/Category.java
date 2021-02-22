package com.example.cityguide.HelperClasses.Models;

import android.graphics.drawable.Drawable;

public class Category {

    int image;
    String title;
    Drawable gradient;

    public Category(int image, String title, Drawable gradient) {
        this.image = image;
        this.title = title;
        this.gradient = gradient;
    }


    public int getImage() {
        return image;
    }

    public String getTitile() {
        return title;
    }

    public Drawable getGradient() {
        return gradient;
    }
}
