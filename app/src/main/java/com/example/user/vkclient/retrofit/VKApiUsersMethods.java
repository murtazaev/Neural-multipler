package com.example.user.vkclient.retrofit;

import com.example.user.vkclient.models.UserProfile;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VKApiUsersMethods {
    @GET("method/users.get")
    Single<UserProfile> getUserId(@Query("user_ids") int ids, @Query("fields") String fields, @Query("name_case") String nameCase);

}
