package com.example.cityguide.Common.Place;

public class Place {

    private String PlaceTitle;
    private String KeyID;
    private int PlaceThumbnail ;
    private String PlaceDescription ;
    private String PlaceMapLink ;
    private String FavStatus ;


    public Place(String key_id, String title, int thumbnail, String description, String maplink, String favStatus ) {

        KeyID = key_id;
        PlaceTitle = title;
        PlaceThumbnail = thumbnail;
        PlaceDescription = description;
        PlaceMapLink = maplink;
        FavStatus = favStatus;
    }


    public String getKeyID() {
        return KeyID;
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

    public String getPlaceFavStatus() {
        return FavStatus;
    }

    public int getPlaceThumbnail() {
        return PlaceThumbnail;
    }


    public void setKeyID(String key_id) {
        PlaceTitle = key_id;
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

    public void setPlaceFavStatus(String favStatus) {
        FavStatus = favStatus;
    }

    public void setPlaceThumbnail(int thumbnail) {
        PlaceThumbnail = thumbnail;
    }
}