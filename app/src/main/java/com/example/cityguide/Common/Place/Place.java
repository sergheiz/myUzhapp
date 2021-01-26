package com.example.cityguide.Common.Place;

import android.widget.TextView;

public class Place {

    private String PlaceTitle;
    private String PlaceMapLink ;
    private String PlaceDescription ;
    private int PlaceThumbnail ;


    public Place(String title, String maplink, String description, int thumbnail) {
        PlaceTitle = title;
        PlaceMapLink = maplink;
        PlaceDescription = description;
        PlaceThumbnail = thumbnail;
    }


    public String getPlaceTitle() {
        return PlaceTitle;
    }

    public String getPlaceMapLink() {
        return PlaceMapLink;
    }

    public String getPlaceDescription() {
        return PlaceDescription;
    }

    public int getPlaceThumbnail() {
        return PlaceThumbnail;
    }


    public void setPlaceTitle(String title) {
        PlaceTitle = title;
    }

    public void setPlaceMapLink(String maplink) {
        PlaceMapLink = maplink;
    }

    public void setPlaceDescription(String description) {
        PlaceDescription = description;
    }

    public void setPlaceThumbnail(int thumbnail) {
        PlaceThumbnail = thumbnail;
    }
}