package com.example.user.vkclient.mvp.PostActivityMvp;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.user.vkclient.activities.PostActivity;
import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.mvp.global.MvpPresenter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@SuppressLint("CheckResult")
public class PostActivityPresenter extends MvpPresenter<PostActivityMVP.View> {
    private PostActivityMVP.View view;
    private PostActivityMVP.DataManager dataManager;

    public PostActivityPresenter(PostActivity activity) {
        view = activity;
        dataManager = new PostActivityDataManager();
    }

    public void getComments(int ownerId, int postId, int needLikes, String sort, int extended, int startComm, int offset) {
        dataManager.getPostComments(ownerId, postId, needLikes, sort, extended, startComm, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommentsModel>() {
                               @Override
                               public void accept(CommentsModel commentsModel) throws Exception {
                                   view.setComments(commentsModel);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.networkError("Нет интернета");
                            }
                        });
    }

    public void loadMorecomments(int ownerId, int postId, int needLikes, String sort, int extended, int startComm, int offset){
        dataManager.getPostComments(ownerId, postId, needLikes, sort, extended, startComm, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommentsModel>() {
                    @Override
                    public void accept(CommentsModel commentsModel) throws Exception {
                        view.acceptMoreComments(commentsModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }

    public void sendMessage(int ownerId, int post_id, final String message, int replyToComm) {
        dataManager.sendMassage(ownerId, post_id, message, replyToComm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) {
                                   try {
                                       JSONObject object = new JSONObject(responseBody.string());
                                       JSONObject object1 = new JSONObject(object.get("response").toString());
                                       int i = Integer.parseInt(object1.get("comment_id").toString());
                                       if (i > 0) {
                                           view.isSuccessfulSend(message, i);
                                       }
                                   } catch (JSONException i) {
                                       view.networkError("Ошибка во время запроса");
                                   } catch (IOException i) {
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

    public void editComment(int ownerId, int commId, final String message, final CheckCallback.CommCheck commCheck, final int position) {
        dataManager.editComment(ownerId, commId, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) {
                                   try {
                                       JSONObject object = new JSONObject(responseBody.string());
                                       int i = Integer.parseInt(object.get("response").toString());
                                       if (i == 1) {
                                           commCheck.isSuccessfulEdit(position, message);
                                       }
                                   } catch (JSONException i) {
                                       view.networkError("Ошибка во время запроса");
                                   } catch (IOException i) {
                                       view.networkError("Ошибка во время запроса");
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.networkError("Нет интернета");
                            }
                        });
    }

    public void deleteComment(int ownerId, int commId, final CheckCallback.CommCheck commCheck, final int position) {
        dataManager.deleteComment(ownerId, commId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) throws Exception {
                                   try {
                                       JSONObject object = new JSONObject(responseBody.string());
                                       int i = Integer.parseInt(object.get("response").toString());
                                       if (i == 1) {
                                           commCheck.isSuccessfulDelete(position);
                                       }
                                   } catch (JSONException i) {
                                       view.networkError("Ошибка во время запроса");
                                   } catch (IOException i) {
                                       view.networkError("Ошибка во время запроса");
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.networkError("Нет интернета");
                            }
                        });
    }


    public void setLike(String type, final int ownerId, int itemId, String accessKey, final CheckCallback checkCallback) {
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
                            if (i > 0) {
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
                    public void accept(ResponseBody responseBody) throws Exception {
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

    public RequestCreator loadImage(String url) {
        return Picasso.with((Context) getView())
                .load(url);
    }

    @Override
    public void detachView() {
        super.detachView();
        view = null;
    }
}
