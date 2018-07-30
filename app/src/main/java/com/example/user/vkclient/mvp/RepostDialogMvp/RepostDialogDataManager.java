package com.example.user.vkclient.mvp.RepostDialogMvp;

import com.example.user.vkclient.Utils;
import com.example.user.vkclient.models.ConversationModel;
import com.example.user.vkclient.retrofit.ServiceGenerator;
import com.example.user.vkclient.retrofit.VKApiExecuteMethod;
import com.example.user.vkclient.retrofit.VKApiMessagesMethods;
import com.example.user.vkclient.retrofit.VKApiWallMethods;

import io.reactivex.Single;
import okhttp3.ResponseBody;

public class RepostDialogDataManager implements RepostDialogMvp.DataManager {
    @Override
    public Single<ConversationModel> getConversations(int offset, int count, String filter, int extended) {
        return ServiceGenerator.createService(VKApiMessagesMethods.class)
                .getConversations(offset, count, filter, extended);
    }

    @Override
    public Single<ResponseBody> sharePost(StringBuffer ids, StringBuffer types, String postUrl, String message){
        return ServiceGenerator.createService(VKApiExecuteMethod.class)
                .sharePost(Utils.shareCode(ids, types, postUrl, message));

    }

    @Override
    public Single<ResponseBody> repost(String object, String message) {
        return ServiceGenerator.createService(VKApiWallMethods.class)
                .repost(object, message);
    }
}
