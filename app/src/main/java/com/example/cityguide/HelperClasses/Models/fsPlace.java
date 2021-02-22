package com.example.cityguide.HelperClasses.Models;

public class fsPlace {

    String name;
    String description;
    String maplink;
    String imgurl;
    String category;

    fsPlace()
    {

    }
    public fsPlace(String name, String description, String maplink, String imgurl, String category) {
        this.name = name;
        this.description = description;
        this.maplink = maplink;
        this.imgurl = imgurl;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaplink() {
        return maplink;
    }

    public void setMaplink(String maplink) {
        this.maplink = maplink;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }












}
