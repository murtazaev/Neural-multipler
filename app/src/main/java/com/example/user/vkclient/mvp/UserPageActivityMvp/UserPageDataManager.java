package com.example.user.vkclient.mvp.UserPageActivityMvp;

import com.example.user.vkclient.models.FriendsModel;
import com.example.user.vkclient.models.UserPostModel;
import com.example.user.vkclient.models.UserProfile;
import com.example.user.vkclient.retrofit.ServiceGenerator;
import com.example.user.vkclient.retrofit.VKApiFriendsMethods;
import com.example.user.vkclient.retrofit.VKApiUsersMethods;
import com.example.user.vkclient.retrofit.VKApiWallMethods;

import io.reactivex.Single;
import okhttp3.ResponseBody;

public class UserPageDataManager implements UserPageMvp.DataManager {
    @Override
    public Single<UserProfile> getUserProfile(int ids, String fields, String nameCase) {
        return ServiceGenerator.createService(VKApiUsersMethods.class)
                .getUserId(ids, fields, nameCase);
    }

    @Override
    public Single<FriendsModel> getUserFriends(int id, String fields) {
        return ServiceGenerator.createService(VKApiFriendsMethods.class)
                .getUserFriends(id, fields);
    }

    @Override
    public Single<UserPostModel> getUserPosts(int ownerId, int extended, String fields, int offset) {
        return ServiceGenerator.createService(VKApiWallMethods.class)
                .getUserPosts(ownerId, extended, fields, offset);
    }

    @Override
    public Single<UserPostModel> getMoreUserPosts(int ownerId, int extended, String fields, int offset) {
        return ServiceGenerator.createService(VKApiWallMethods.class)
        .getUserPosts(ownerId, extended, fields, offset);
    }
}
