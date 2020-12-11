package com.example.cityguide.Databases;

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

    //User session vars
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    //Remember me vars
    private static final String IS_REMEMBERME = "IsRememberMe";

    public static final String KEY_REMEMBERMEPHONENUMBER = "phoneNumber";
    public static final String KEY_REMEMBERMEPASSWORD = "password";


    //Constructor
    public SessionManager(Context _context, String sessionName) {

        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    // Users Login session
    public void createLoginSession(String fullName, String username, String email, String phoneNumber, String password, String date, String gender) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_GENDER, gender);

        editor.commit();

    }

    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return userData;
    }


    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, true)) {
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

        editor.commit();

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


}
