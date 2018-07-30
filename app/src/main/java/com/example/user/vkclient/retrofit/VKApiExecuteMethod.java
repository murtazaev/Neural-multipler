package com.example.user.vkclient.retrofit;

import java.util.ArrayList;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VKApiExecuteMethod {
    @GET("method/execute")
    Single<ResponseBody> getPostsWithLastComm(@Query("code") String code);

    @GET("method/execute")
    Single<ResponseBody> sharePost(@Query("code") String code);
}
