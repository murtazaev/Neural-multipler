package com.example.user.vkclient.mvp.MainMvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.example.user.vkclient.App;
import com.example.user.vkclient.Utils;
import com.example.user.vkclient.activities.MainActivity;
import com.example.user.vkclient.fragments.UserFeedFragment;
import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.models.LastCommentModel;
import com.example.user.vkclient.models.VerificationClasses.AccessLongPull;
import com.example.user.vkclient.models.VerificationClasses.CheckingToken;
import com.example.user.vkclient.models.VerificationClasses.ServiceAccessToken;
import com.example.user.vkclient.models.UserFeed;
import com.example.user.vkclient.models.VKFeedResponse;
import com.example.user.vkclient.mvp.global.MvpPresenter;
import com.google.gson.Gson;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


@SuppressLint("CheckResult")
public class MainPresenter extends MvpPresenter<MainActivityMVP.View> {

    private MainActivityMVP.View view;
    private MainActivityMVP.DataManager dataManager;

    public MainPresenter(MainActivityMVP.View activity) {
        view = activity;
        dataManager = new MainDataManager();
    }

    public void VKLogin() {
        VKSdk.login((Activity) getView(), VKScope.WALL, VKScope.FRIENDS, VKScope.MESSAGES);
    }

    public void onCheckToken() {
        dataManager
                .getServiceAccessToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ServiceAccessToken, Single<CheckingToken>>() {
                    @Override
                    public Single<CheckingToken> apply(ServiceAccessToken serviceAccessToken) {
                        return dataManager
                                .checkUserAccessToken(App.getSharedPrefHelper().getAccessToken(), serviceAccessToken.getAccessToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());

                    }
                })
                .subscribe(new Consumer<CheckingToken>() {
                               @Override
                               public void accept(CheckingToken checkingToken) {
                                   view.handleResponseFromCheckingUserAccessToken(checkingToken.getResponse().getSuccess());
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                view.networkError("Нет интернета");
                            }
                        });
    }

    public void loadMoreUserFeedItems() {
        final UserFeed u = new UserFeed();
        String o = App.getNextFrom();
        dataManager.getPostsWithLastComm(Utils.getLastCommCode(App.getNextFrom()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) {
                                   Gson gson = new Gson();
                                   JSONObject object = null;
                                   try {
                                       object = new JSONObject(responseBody.string());
                                       JSONObject object1 = new JSONObject(object.getJSONObject("response").toString());
                                       VKFeedResponse vkFeedResponse = gson.fromJson(object1.toString(), VKFeedResponse.class);
                                       App.setNextFrom(vkFeedResponse.getUser_feed().getNext_from());
                                       u.setVkFeedResponse(vkFeedResponse);
                                       u.setLastComm(parse(object1.getJSONArray("lasts_comm_in_feed")));
                                       view.acceptPaginationFeedArray(u.getVkFeedResponse(), u.getLastComm());
                                   } catch (JSONException e) {
                                       view.networkError("Ошибка во время запроса");
                                   } catch (IOException e) {
                                       view.networkError("Ошибка во время запроса");
                                   }

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                view.networkError("Нет интернета");
                            }
                        });
    }

    public void getPostsWithLastComm() {
        dataManager.getPostsWithLastComm(Utils.getLastCommCode(App.getNextFrom()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) {
                                   Gson gson = new Gson();
                                   JSONObject object = null;
                                   try {
                                       object = new JSONObject(responseBody.string());
                                       JSONObject object1 = new JSONObject(object.getJSONObject("response").toString());
                                       VKFeedResponse vkFeedResponse = gson.fromJson(object1.toString(), VKFeedResponse.class);
                                       App.setNextFrom(vkFeedResponse.getUser_feed().getNext_from());
                                       view.handleResponseFromUserFeed(vkFeedResponse, parse(object1.getJSONArray("lasts_comm_in_feed")));
                                   } catch (JSONException e) {
                                       view.networkError("Ошибка во время запроса");
                                   } catch (IOException e) {
                                       view.networkError("Ошибка во время запроса");
                                   }

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                view.networkError("Нет интернета");
                            }
                        });
    }


    public void setLike(String type, int ownerId, final int itemId, String accessKey, final CheckCallback checkCallback) {
        dataManager.setLike(type, ownerId, itemId, accessKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        try {
                            JSONObject object = new JSONObject(responseBody.string());
                            JSONObject object1 = new JSONObject(object.get("response").toString());
                            int i = Integer.parseInt(object1.get("likes").toString());
                            if (i >= 0) {
                                checkCallback.isSuccessful(1);
                            }
                        } catch (JSONException i) {
                            view.networkError("Ошибка во время запроса");
                        } catch (IOException i) {
                            view.networkError("Ошибка во время запроса");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }

    public void deleteLike(String type, int ownerId, int itemId, final CheckCallback checkCallback) {
        dataManager.deleteLike(type, ownerId, itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        try {
                            JSONObject object = new JSONObject(responseBody.string());
                            JSONObject object1 = new JSONObject(object.get("response").toString());
                            int i = Integer.parseInt(object1.get("likes").toString());
                            if (i >= 0) {
                                checkCallback.isSuccessful(0);
                            }
                        } catch (JSONException i) {
                            view.networkError("Ошибка во время запроса");
                        } catch (IOException i) {
                            view.networkError("Ошибка во время запроса");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }


    public void longPollRequest() {
        dataManager.getLongPollServer(0, "", 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<AccessLongPull, Single<ResponseBody>>() {
                    @Override
                    public Single<ResponseBody> apply(AccessLongPull accessLongPull) {
                        return dataManager
                                .longPollRequest("https://" +
                                                accessLongPull
                                                        .getResponse()
                                                        .getServer(),
                                        "a_check",
                                        accessLongPull.getResponse().getKey(),
                                        accessLongPull.getResponse().getTs(),
                                        10, 8, 3)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) {
                                   try {
                                       view.networkError(responseBody.string());
                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) {
                                   view.networkError("Нет интернета");
                               }
                           }
                );
    }

    @Override
    public void detachView() {
        super.detachView();
        view = null;
    }


    public static ArrayList<LastCommentModel> parse(JSONArray array) {
        ArrayList<LastCommentModel> lastComm;
        lastComm = new ArrayList<>();
        try {
            lastComm = new ArrayList<>(array.length());
            for (int i = 0; i < array.length(); i++) {
                LastCommentModel lastCommentModel = new LastCommentModel();
                try {
                    JSONArray comment = new JSONArray(array.get(i).toString());
                    lastCommentModel.setPhotoURL(comment.get(0).toString());
                    lastCommentModel.setName(comment.get(1).toString());
                    lastCommentModel.setText(comment.get(2).toString());
                    lastComm.add(lastCommentModel);
                } catch (JSONException q) {
                    lastCommentModel.setText(array.get(i).toString());
                    lastComm.add(lastCommentModel);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lastComm;
    }
}
