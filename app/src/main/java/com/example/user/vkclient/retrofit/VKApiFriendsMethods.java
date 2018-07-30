package com.example.user.vkclient.retrofit;

import com.example.user.vkclient.models.FriendsModel;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VKApiFriendsMethods {
    @GET("method/friends.get")
    Single<FriendsModel> getUserFriends(@Query("user_id") int id, @Query("fields") String fields);
}
