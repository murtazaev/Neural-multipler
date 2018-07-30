package com.example.user.vkclient.retrofit;

import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.UserPostModel;

import java.net.ResponseCache;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VKApiWallMethods {
    @GET("method/wall.getComments")
    Single<CommentsModel> getPostComments(@Query("owner_id") int ownerId, @Query("post_id") int postId,
                                          @Query("need_likes") int needLikes, @Query("sort") String sort,
                                          @Query("extended") int extended, @Query("start_comment_id") int startComm, @Query("offset") int offset);

    @POST("method/wall.createComment")
    Single<ResponseBody> sendMassage(@Query("owner_id") int ownerId, @Query("post_id") int post_id,
                                     @Query("message") String message, @Query("reply_to_comment") int replyToComm);

    @POST("method/wall.deleteComment")
    Single<ResponseBody> deleteComment(@Query("owner_id") int ownerId, @Query("comment_id") int commId);


    @POST("method/wall.editComment")
    Single<ResponseBody> editComment(@Query("owner_id") int ownerId, @Query("comment_id") int commId, @Query("message") String message);


    @POST("method/wall.repost")
    Single<ResponseBody> repost(@Query("object") String object, @Query("message") String messages);

    @GET("method/wall.get")
    Single<UserPostModel> getUserPosts(@Query("owner_id") int ownerId, @Query("extended") int extended, @Query("fields") String fields, @Query("offset") int offset);
}
