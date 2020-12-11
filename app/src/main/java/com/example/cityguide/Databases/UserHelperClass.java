package com.example.cityguide.Databases;


public class UserHelperClass {

    String phoneNo, email, username, fullName, password, date, gender;

    public UserHelperClass(){}

    public UserHelperClass(String phoneNo, String email, String username, String fullName, String password, String date, String gender) {
        this.phoneNo = phoneNo;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.date = date;
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
