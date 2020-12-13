package com.example.cityguide.Databases;


public class UserHelperClass {

    String phoneNo, email, fullName, password;

    public UserHelperClass(){}

    public UserHelperClass(String phoneNo, String email,  String fullName, String password) {
        this.phoneNo = phoneNo;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
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

}
