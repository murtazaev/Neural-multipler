package com.example.user.vkclient.retrofit;

import com.example.user.vkclient.models.ConversationModel;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VKApiMessagesMethods {
    @GET("method/messages.getConversations")
    Single<ConversationModel> getConversations(@Query("offset") int offset, @Query("count") int count, @Query("filter") String filter, @Query("extended") int extended);
}
