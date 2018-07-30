package com.example.user.vkclient.retrofit;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VKApiVideoMethods {
    @GET("method/video.get")
    Single<ResponseBody> getVideo(@Query("owner_id") int ownerId, @Query("videos") String videos, @Query("extended") int i);
}
