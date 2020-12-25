package com.example.cityguide.Common.Place;

public class Place {

    private String PlaceTitle;
    private String PlaceCategory ;
    private String PlaceDescription ;
    private int PlaceThumbnail ;

    public Place() {
    }

    public Place(String title, String category, String description, int thumbnail) {
        PlaceTitle = title;
        PlaceCategory = category;
        PlaceDescription = description;
        PlaceThumbnail = thumbnail;
    }


    public String getPlaceTitle() {
        return PlaceTitle;
    }

    public String getPlaceCategory() {
        return PlaceCategory;
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

    public void setPlaceCategory(String category) {
        PlaceCategory = category;
    }

    public void setPlaceDescription(String description) {
        PlaceDescription = description;
    }

    public void setPlaceThumbnail(int thumbnail) {
        PlaceThumbnail = thumbnail;
    }
}