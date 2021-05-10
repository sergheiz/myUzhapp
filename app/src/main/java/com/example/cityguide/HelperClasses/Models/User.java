package com.example.cityguide.HelperClasses.Models;


public class User {

    String phoneNo, fullName, password, avatarUrl;



    public User( String phoneNo,  String fullName, String password, String avatarUrl) {
        this.phoneNo = phoneNo;
        this.fullName = fullName;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
