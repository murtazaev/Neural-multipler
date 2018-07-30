package com.example.user.vkclient.mvp.RepostDialogMvp;

import android.annotation.SuppressLint;

import com.example.user.vkclient.dialogs.RepostDialog;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.ConversationModel;
import com.example.user.vkclient.mvp.global.MvpPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@SuppressLint("CheckResult")
public class RepostDialogPresenter extends MvpPresenter<RepostDialogMvp.View> {

    private RepostDialogMvp.View view;
    private RepostDialogMvp.DataManager dataManager;

    public RepostDialogPresenter(RepostDialog dialog) {
        view = dialog;
        dataManager = new RepostDialogDataManager();
    }


    public void getConversations(int offset, int count, String filter, int extended) {
        dataManager.getConversations(offset, count, filter, extended)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ConversationModel>() {
                    @Override
                    public void accept(ConversationModel conversationModel) throws Exception {
                        for (ConversationModel.Response.Item item : conversationModel.getResponse().getConversations()) {
                            for (CommentsModel.Comments.Profiles profiles : conversationModel.getResponse().getProfiles()) {
                                if (item.getConversation().getPeer().getId() == profiles.getId()) {
                                    item.setrName(profiles.getName());
                                    item.setrPhoto(profiles.getPhoto_100());
                                }
                            }
                            for (CommentsModel.Comments.Groups groups : conversationModel.getResponse().getGroups()) {
                                if (item.getConversation().getPeer().getId() == -groups.getId()) {
                                    item.setrName(groups.getName());
                                    item.setrPhoto(groups.getPhoto_100());
                                }
                            }
                        }
                        if (view != null) {
                            view.acceptConversations(conversationModel);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError("Нет интернета");
                    }
                });
    }

    public void repost(String object, String message) {
        dataManager.repost(object, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        try {
                            JSONObject object = new JSONObject(responseBody.string());
                            JSONObject object1 = new JSONObject(object.get("response").toString());
                            int i = Integer.parseInt(object1.get("success").toString());
                            if (i == 1) {
                                view.shareIsSuccessful();
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


    public void sahrePost(StringBuffer ids, StringBuffer types, String postUrl, String message) {
        dataManager.sharePost(ids, types, postUrl, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        try {
                            JSONObject object = new JSONObject(responseBody.string());
                            int i = Integer.parseInt(object.get("response").toString());
                            if (i > 0) {
                                view.shareIsSuccessful();
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

    public void buildArrays(ArrayList<Integer> index, ArrayList<ConversationModel.Response.Item> items, StringBuffer ids, StringBuffer types) {
        for (Integer integer : index) {
            ConversationModel.Response.Item i = items.get(integer);
            if (integer == index.get(index.size() - 1)) {
                if (i.getConversation().getPeer().getType().equals("user")) {
                    ids.append(i.getConversation().getPeer().getId());
                    types.append("\"user\"");
                } else {
                    ids.append(i.getConversation().getPeer().getId() - 2000000000);
                    types.append("\"chat\"");
                }
            } else {
                if (i.getConversation().getPeer().getType().equals("user")) {
                    ids.append(i.getConversation().getPeer().getId()).append(",");
                    types.append("\"user\"").append(",");
                } else {
                    ids.append(i.getConversation().getPeer().getId() - 2000000000).append(",");
                    types.append("\"chat\"").append(",");
                }
            }
        }
        addSquareBrackets(ids);
        addSquareBrackets(types);
    }

    private void addSquareBrackets(StringBuffer buffer) {
        buffer.insert(0, "[");
        buffer.insert(buffer.length(), "]");
    }

    @Override
    public void detachView() {
        super.detachView();
        view = null;
    }
}
