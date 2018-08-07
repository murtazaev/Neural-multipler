package com.example.user.vkclient;

import android.content.Intent;

import com.example.user.vkclient.activities.MainActivity;
import com.example.user.vkclient.retrofit.ServiceGenerator;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class App extends android.app.Application {

    private static String nextFrom = "";
    private static int userId;

    private static SharedPreferencesHelper preferencesHelper;

    private VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(App.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);

        preferencesHelper = new SharedPreferencesHelper(getSharedPreferences("VK Access Token", MODE_PRIVATE));
    }

    public static SharedPreferencesHelper getSharedPrefHelper(){
        return preferencesHelper;
    }



    public static String getCLIENT_SECRET_CODE() {
        return "44dNhVKi8IbhQXbPzQKG";
    }public static int getVK_APPLICATION_ID() {
        return 6621916;
    }public static String getAPI_VERSION() {
        return "5.80";
    }

    public static String getNextFrom() {
        return nextFrom;
    }
    public static void setNextFrom(String nextFrom) {
        App.nextFrom = nextFrom;
    }

    public static int getUserId() {
        return userId;
    }public static void setUserId(int userId) {
        App.userId = userId;
    }
}
