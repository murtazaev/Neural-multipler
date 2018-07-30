package com.example.user.vkclient.mvp.PostActivityMvp;

import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.ConversationModel;
import com.example.user.vkclient.mvp.global.MvpView;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Query;

public interface PostActivityMVP {
    interface View extends MvpView{
        void setComments(CommentsModel recyclerComments);
        void acceptMoreComments(CommentsModel commentsModel);
        void isSuccessfulSend(String text, int id);
        void networkError(String error);
    }

    interface DataManager{
        Single<CommentsModel> getPostComments(int ownerId, int postId, int needLikes, String sort, int extended, int startComm, int offset);
        Single<ResponseBody> sendMassage(int ownerId, int post_id, String message, int replyToComm);
        Single<ResponseBody> editComment(int ownerId, int commId, String message);
        Single<ResponseBody> deleteComment(int ownerId, int commId);
        Single<ResponseBody> setLike(String type, int ownerId, int itemId, String accessKey);
        Single<ResponseBody> deleteLike(String type, int ownerId, int itemId);
        Single<ConversationModel> getConversations(int offset, int count, String filter, int extended);
        Single<ResponseBody> sharePost(StringBuffer ids, StringBuffer types, String postUrl, String message);
        Single<ResponseBody> repost(String object, String message);
    }
}
