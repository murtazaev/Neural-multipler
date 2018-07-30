package com.example.user.vkclient.retrofit;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VKApiLikesMethods {
    @POST("method/likes.add")
    Single<ResponseBody> setLike(@Query("type") String type, @Query("owner_id") int ownerId,
                                 @Query("item_id") int itemId, @Query("access_key") String accessKey);

    @POST("method/likes.delete")
    Single<ResponseBody> deleteLike(@Query("type") String type, @Query("owner_id") int ownerId,
                                    @Query("item_id") int itemId);
}
