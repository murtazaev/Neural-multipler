package com.example.user.vkclient.retrofit;

import com.example.user.vkclient.models.VKFeedResponse;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VKApiNewsfeedMethods {

    @GET("method/newsfeed.get")
    Single<VKFeedResponse> getUserFeed(@Query("filters") String filters, @Query("start_from") String startFrom,
                                   @Query("count") int count);
}
