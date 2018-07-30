package com.example.user.vkclient.mvp.RepostDialogMvp;

import com.example.user.vkclient.models.ConversationModel;
import com.example.user.vkclient.mvp.global.MvpView;

import io.reactivex.Single;
import okhttp3.ResponseBody;

public interface RepostDialogMvp {
    interface View extends MvpView{
        void acceptConversations(ConversationModel conversationModel);
        void shareIsSuccessful();
        void networkError(String error);
    }

    interface DataManager{
        Single<ConversationModel> getConversations(int offset, int count, String filter, int extended);
        Single<ResponseBody> sharePost(StringBuffer ids, StringBuffer types, String postUrl, String message);
        Single<ResponseBody> repost(String object, String message);
    }

}
