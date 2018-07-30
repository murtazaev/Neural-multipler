package com.example.user.vkclient.retrofit;


import com.example.user.vkclient.models.VerificationClasses.CheckingToken;
import com.example.user.vkclient.models.VerificationClasses.ServiceAccessToken;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VKApiSecureMethods {

    @GET("oauth/access_token")
    Single<ServiceAccessToken> getServiceAccessToken(@Query("client_id") int VKApplicationId,
                                                     @Query("client_secret") String clientSecretCode, @Query("grant_type") String grantedType);

    @GET("method/secure.checkToken")
    Single<CheckingToken> checkToken(@Query("token") String userToken,
                                           @Query("client_secret") String clientSecretCode, @Query("access_token") String serviceAccessToken);
}
