package com.example.cityguide.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session names
    public static final String SESSION_USERSLOGIN = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";
    public static final String SESSION_EXISTENCE = "existence";

    //User session vars
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PASSWORD = "password";

    //Remember me vars
    private static final String IS_REMEMBERME = "IsRememberMe";

    public static final String KEY_REMEMBERMEPHONENUMBER = "phoneNumber";
    public static final String KEY_REMEMBERMEPASSWORD = "password";


    //Existence vars
    private static final String IS_EXISTENCE = "IsExistence";

    public static final String KEY_EXISTENCE = "existence";


    //Constructor
    public SessionManager(Context _context, String sessionName) {

        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    // Users Login session
    public void createLoginSession(String phoneNumber, String fullName, String email,  String password) {

        editor.putBoolean(IS_LOGIN, true);


        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);

        editor.apply();

    }

    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));

        return userData;
    }


    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {

        editor.clear();
        editor.commit();

    }

    //Remember me session function

    public void createRememberMeSession(String phoneNo, String password) {

        editor.putBoolean(IS_REMEMBERME, true);

        editor.putString(KEY_REMEMBERMEPHONENUMBER, phoneNo);
        editor.putString(KEY_REMEMBERMEPASSWORD, password);

        editor.apply();

    }

    public HashMap<String, String> getRememberMeDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_REMEMBERMEPHONENUMBER, userSession.getString(KEY_REMEMBERMEPHONENUMBER, null));
        userData.put(KEY_REMEMBERMEPASSWORD, userSession.getString(KEY_REMEMBERMEPASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe() {
        if (userSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else {
            return false;
        }
    }


    // Existence session
    public void createExistenceSession(String existence) {

        editor.putBoolean(IS_EXISTENCE, true);


        editor.putString(KEY_EXISTENCE, existence);
        editor.apply();

    }

    public HashMap<String, String> getDatafromExistence() {
        HashMap<String, String> data = new HashMap<String, String>();

        data.put(KEY_EXISTENCE, userSession.getString(KEY_EXISTENCE, null));

        return data;
    }


    public boolean checkExistence() {
        if (userSession.getBoolean(IS_EXISTENCE, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void clearExistence() {

        editor.clear();
        editor.commit();

    }


}
