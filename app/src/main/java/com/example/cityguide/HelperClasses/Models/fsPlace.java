package com.example.cityguide.HelperClasses.Models;

import com.google.firebase.firestore.DocumentId;

import java.util.List;

public class fsPlace {

    String name;
    String owner;
    String description;
    String maplink;
    String phone;
    String imgurl;
    String group;
    List<String> likes;

    fsPlace() {

    }

    public fsPlace(String name, String owner, String description, String phone, String maplink, String imgurl, String group, List<String> likes) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.maplink = maplink;
        this.phone = phone;
        this.imgurl = imgurl;
        this.group = group;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}
