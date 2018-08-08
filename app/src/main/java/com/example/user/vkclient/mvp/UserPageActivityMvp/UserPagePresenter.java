package com.example.user.vkclient.mvp.UserPageActivityMvp;

import android.annotation.SuppressLint;

import com.example.user.vkclient.App;
import com.example.user.vkclient.Utils;
import com.example.user.vkclient.activities.UserPageActivity;
import com.example.user.vkclient.models.FriendsModel;
import com.example.user.vkclient.models.UserPostModel;
import com.example.user.vkclient.models.UserProfile;
import com.example.user.vkclient.models.VKFeedResponse;
import com.example.user.vkclient.mvp.global.MvpPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@SuppressLint("CheckResult")
public class UserPagePresenter extends MvpPresenter<UserPageMvp.View> {

    private UserPageMvp.View view;
    private UserPageMvp.DataManager dataManager;

    public UserPagePresenter(UserPageMvp.View activity){
        view = activity;
        dataManager = new UserPageDataManager();
    }

    public void getUserProfile(int ids, String fields, String nameCase){
        dataManager.getUserProfile(ids, fields, nameCase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserProfile>() {
                    @Override
                    public void accept(UserProfile userProfile) throws Exception {
                        view.acceptUserProfile(userProfile);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }

    public void getUserFriends(int id, String fields){
        dataManager.getUserFriends(id, fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FriendsModel>() {
                    @Override
                    public void accept(FriendsModel friendsModel) throws Exception {
                        view.acceptUserFriends(friendsModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }

    public void getUserPosts(int ownerId, int extended, String fields, int offset){
        dataManager.getUserPosts(ownerId, extended, fields, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserPostModel>() {
                    @Override
                    public void accept(UserPostModel userPostModel) throws Exception {
                        view.acceptUserPosts(userPostModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }


    public void getMoreUserPosts(int ownerId, int extended, String fields, int offset){
        dataManager.getMoreUserPosts(ownerId, extended, fields, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserPostModel>() {
                    @Override
                    public void accept(UserPostModel userPostModel) throws Exception {
                        view.acceptMoreUserPosts(userPostModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }


    @Override
    public void detachView() {
        super.detachView();
        view = null;
    }
}
