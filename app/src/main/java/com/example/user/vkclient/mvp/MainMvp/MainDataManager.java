package com.example.user.vkclient.mvp.MainMvp;

import android.annotation.SuppressLint;

import com.example.user.vkclient.App;
import com.example.user.vkclient.models.VerificationClasses.AccessLongPull;
import com.example.user.vkclient.models.VerificationClasses.CheckingToken;
import com.example.user.vkclient.models.VerificationClasses.ServiceAccessToken;
import com.example.user.vkclient.retrofit.ServiceGenerator;
import com.example.user.vkclient.retrofit.VKApiExecuteMethod;
import com.example.user.vkclient.retrofit.VKApiLikesMethods;
import com.example.user.vkclient.retrofit.VKApiLongPullRequest;
import com.example.user.vkclient.retrofit.VKApiSecureMethods;

import io.reactivex.Single;
import okhttp3.ResponseBody;

public class MainDataManager implements MainActivityMVP.DataManager{

    @SuppressLint("CheckResult")
    @Override
    public Single<ServiceAccessToken> getServiceAccessToken() {
        ServiceGenerator.setFlag(ServiceGenerator.REMOVE_INTERCEPTOR_FROM_REQUEST);
        return  ServiceGenerator.createService(VKApiSecureMethods.class)
                .getServiceAccessToken(App.getVK_APPLICATION_ID(), App.getCLIENT_SECRET_CODE(), "client_credentials");
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<CheckingToken> checkUserAccessToken(String userToken, String serviceAccessToken) {
        ServiceGenerator.setFlag(ServiceGenerator.REMOVE_INTERCEPTOR_FROM_REQUEST);
        return ServiceGenerator.createService(VKApiSecureMethods.class)
                .checkToken(userToken, App.getCLIENT_SECRET_CODE(), serviceAccessToken);
    }

    @Override
    public Single<ResponseBody> setLike(String type, int ownerId, int itemId, String accessKey){
        return ServiceGenerator.createService(VKApiLikesMethods.class)
                .setLike(type, ownerId, itemId, accessKey);
    }

    @Override
    public Single<ResponseBody> deleteLike(String type, int ownerId, int itemId) {
        return ServiceGenerator.createService(VKApiLikesMethods.class)
                .deleteLike(type, ownerId, itemId);
    }

    @Override
    public Single<AccessLongPull> getLongPollServer(int pts, String groupId, int lpVersion){
        return ServiceGenerator.createService(VKApiLongPullRequest.class)
                .getLongPullServer(pts, groupId, lpVersion);
    }

    @Override
    public Single<ResponseBody> longPollRequest(String server, String act, String key, int ts, int wait, int mode, int version) {
        return ServiceGenerator.createService(VKApiLongPullRequest.class)
                .longPullRequest(server, act, key, ts, wait, mode, version);
    }

    @Override
    public Single<ResponseBody> getPostsWithLastComm(String code) {
        return ServiceGenerator.createService(VKApiExecuteMethod.class)
                .getPostsWithLastComm(code);
    }
}
