package com.example.user.vkclient.retrofit;

import com.example.user.vkclient.models.VerificationClasses.AccessLongPull;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface VKApiLongPullRequest {
    @GET("method/messages.getLongPollServer")
    Single<AccessLongPull> getLongPullServer(@Query("need_pts") int pts, @Query("group_id") String groupId,
                                           @Query("lp_version") int lpVersion);

    @GET
    Single<ResponseBody> longPullRequest(@Url String server, @Query("act") String act,
                                       @Query("key") String key, @Query("ts") int ts, @Query("wait") int wait,
                                       @Query("mode") int mode, @Query("version") int version);
}
