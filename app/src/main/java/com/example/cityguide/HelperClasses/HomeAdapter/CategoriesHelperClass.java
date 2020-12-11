package com.example.cityguide.HelperClasses.HomeAdapter;

import android.graphics.drawable.Drawable;

public class CategoriesHelperClass {

    int image;
    String title;
    Drawable gradient;

    public CategoriesHelperClass(int image, String title, Drawable gradient) {
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
