package com.example.user.vkclient.mvp.MainMvp;

import com.example.user.vkclient.models.VerificationClasses.AccessLongPull;
import com.example.user.vkclient.models.VerificationClasses.CheckingToken;
import com.example.user.vkclient.models.LastCommentModel;
import com.example.user.vkclient.models.VerificationClasses.ServiceAccessToken;
import com.example.user.vkclient.models.VKFeedResponse;
import com.example.user.vkclient.mvp.global.MvpView;

import java.util.ArrayList;

import io.reactivex.Single;
import okhttp3.ResponseBody;


public interface MainActivityMVP {
        interface View extends MvpView{
            void handleResponseFromCheckingUserAccessToken(int success);
            void handleResponseFromUserFeed(VKFeedResponse vkFeedResponse, ArrayList<LastCommentModel> lastComm);
            void acceptPaginationFeedArray(VKFeedResponse vkFeedResponse, ArrayList<LastCommentModel> lastComm);
            void networkError(String error);
        }

        interface DataManager{
            Single<ServiceAccessToken> getServiceAccessToken();
            Single<CheckingToken> checkUserAccessToken(String userToken, String serviceAccessToken);
            Single<ResponseBody> setLike(String type, int ownerId, int itemId, String accessKey);
            Single<ResponseBody> deleteLike(String type, int ownerId, int itemId);
            Single<AccessLongPull> getLongPollServer(int pts, String groupId, int lpVersion);
            Single<ResponseBody> longPollRequest(String server, String act, String key, int ts, int wait, int mode, int version);
            Single<ResponseBody> getPostsWithLastComm(String code);
        }
}
