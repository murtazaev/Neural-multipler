package com.example.user.vkclient;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String ACCESS_TOKEN_KEY = "This is SPARTA!!!";
    private static final String USER_ID = "FUS RO DA!!!!!";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    SharedPreferencesHelper(SharedPreferences preferences){
        SharedPreferencesHelper.preferences = preferences;
    }

    public void saveToken(String token){
        editor = preferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, token);
        editor.apply();
    }

    public String getAccessToken(){
        return preferences.getString(ACCESS_TOKEN_KEY, "token is null");
    }

    public void saveUserId(int userId){
        editor = preferences.edit();
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    public int getUserId(){
        return preferences.getInt(USER_ID, -1);
    }
}
