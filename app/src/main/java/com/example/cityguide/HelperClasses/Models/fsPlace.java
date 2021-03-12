package com.example.cityguide.HelperClasses.Models;

import com.google.firebase.firestore.DocumentId;

public class fsPlace {

    String documentId;
    String name;
    String description;
    String maplink;
    String imgurl;
    String group;

    fsPlace() {

    }

    public fsPlace(String name, String description, String maplink, String imgurl, String group) {
        this.name = name;
        this.description = description;
        this.maplink = maplink;
        this.imgurl = imgurl;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


}
