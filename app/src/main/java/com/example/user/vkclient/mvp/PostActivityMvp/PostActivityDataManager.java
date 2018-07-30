package com.example.user.vkclient.mvp.PostActivityMvp;

import com.example.user.vkclient.Utils;
import com.example.user.vkclient.activities.PostActivity;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.ConversationModel;
import com.example.user.vkclient.retrofit.ServiceGenerator;
import com.example.user.vkclient.retrofit.VKApiExecuteMethod;
import com.example.user.vkclient.retrofit.VKApiLikesMethods;
import com.example.user.vkclient.retrofit.VKApiMessagesMethods;
import com.example.user.vkclient.retrofit.VKApiWallMethods;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Query;

public class PostActivityDataManager implements PostActivityMVP.DataManager {

    @Override
    public Single<CommentsModel> getPostComments(int ownerId, int postId, int needLikes, String sort, int extended, int startComm, int offset) {
        return ServiceGenerator.createService(VKApiWallMethods.class)
                .getPostComments(ownerId, postId, needLikes, sort, extended, startComm, offset);
    }

    @Override
    public Single<ResponseBody> sendMassage(int ownerId, int post_id, String message, int replyToComm) {
        return ServiceGenerator.createService(VKApiWallMethods.class)
                .sendMassage(ownerId, post_id, message, replyToComm);
    }

    @Override
    public Single<ResponseBody> editComment(int ownerId, int commId, String message){
        return ServiceGenerator.createService(VKApiWallMethods.class)
                .editComment(ownerId, commId, message);
    }

    @Override
    public Single<ResponseBody> deleteComment(int ownerId, int commId) {
        return ServiceGenerator.createService(VKApiWallMethods.class)
                .deleteComment(ownerId, commId);
    }

    @Override
    public Single<ResponseBody> setLike(String type, int ownerId, int itemId, String accessKey) {
        return ServiceGenerator.createService(VKApiLikesMethods.class)
                .setLike(type, ownerId, itemId, accessKey);
    }

    @Override
    public Single<ResponseBody> deleteLike(String type, int ownerId, int itemId) {
        return ServiceGenerator.createService(VKApiLikesMethods.class)
                .deleteLike(type, ownerId, itemId);
    }

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
