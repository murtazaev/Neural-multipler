package com.example.user.vkclient.mvp.UserPageActivityMvp;

import android.widget.FrameLayout;

import com.example.user.vkclient.models.FriendsModel;
import com.example.user.vkclient.models.LastCommentModel;
import com.example.user.vkclient.models.UserPostModel;
import com.example.user.vkclient.models.UserProfile;
import com.example.user.vkclient.models.VKFeedResponse;
import com.example.user.vkclient.mvp.global.MvpView;

import java.util.ArrayList;

import io.reactivex.Single;
import okhttp3.ResponseBody;

public interface UserPageMvp {
    interface View extends MvpView{
        void acceptUserProfile(UserProfile userProfile);
        void acceptUserFriends(FriendsModel friendsModel);
        void acceptUserPosts(UserPostModel userPostModel);
        void acceptMoreUserPosts(UserPostModel userPostModel);
        void networkError(String error);
    }

    interface DataManager{
        Single<UserProfile> getUserProfile(int ids, String fields, String nameCase);
        Single<FriendsModel> getUserFriends(int id, String fields);
        Single<UserPostModel> getUserPosts(int ownerId, int extended, String fields, int offset);
        Single<UserPostModel> getMoreUserPosts(int ownerId, int extended, String fields, int offset);
    }
}
