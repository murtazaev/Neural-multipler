package com.example.user.vkclient.interfaces;

import com.example.user.vkclient.models.VKFeedResponse;

public interface FragmentsBackPressed {
    interface ReturnVKFeedObject{
        VKFeedResponse.Response.VKFeedObject returnVKFeedObject();
    }
}
