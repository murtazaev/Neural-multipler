package com.example.user.vkclient.models.VerificationClasses;

import com.google.gson.annotations.SerializedName;

public class ServiceAccessToken {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expires_in")
    private int expires_in;


    public String getAccessToken() {
        return accessToken;
    }
}
