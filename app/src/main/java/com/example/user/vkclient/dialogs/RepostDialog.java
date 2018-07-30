package com.example.user.vkclient.dialogs;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.vkclient.R;
import com.example.user.vkclient.Utils;
import com.example.user.vkclient.adapters.RepostConversationsAdapter;
import com.example.user.vkclient.interfaces.RepostCallback;
import com.example.user.vkclient.models.ConversationModel;
import com.example.user.vkclient.mvp.RepostDialogMvp.RepostDialogMvp;
import com.example.user.vkclient.mvp.RepostDialogMvp.RepostDialogPresenter;
import com.example.user.vkclient.retrofit.ServiceGenerator;
import com.example.user.vkclient.retrofit.VKApiExecuteMethod;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RepostDialog extends DialogFragment implements RepostDialogMvp.View, RepostCallback, View.OnClickListener {


    RecyclerView conversations;
    Button sendPost, repost;
    EditText message;

    RepostDialogPresenter presenter;
    RepostConversationsAdapter adapter;
    String postUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        postUrl = getArguments().getString("postUrl");
        presenter = new RepostDialogPresenter(this);
        presenter.attachView(this);
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialog_repost, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conversations = view.findViewById(R.id.conversations);
        sendPost = view.findViewById(R.id.send_repost);
        repost = view.findViewById(R.id.repost);
        message = view.findViewById(R.id.message);
        conversations.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        presenter.getConversations(0, 100, "all", 1);
        sendPost.setOnClickListener(this);
        repost.setOnClickListener(this);
    }

    @Override
    public void acceptConversations(ConversationModel conversationModel) {
        adapter = new RepostConversationsAdapter(conversationModel.getResponse().getConversations(), this);
        conversations.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == 1) {
            repost.setVisibility(View.GONE);
            sendPost.setVisibility(View.VISIBLE);
        } else {
            repost.setVisibility(View.VISIBLE);
            sendPost.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_repost:
                StringBuffer ids = new StringBuffer();
                StringBuffer types = new StringBuffer();
                presenter.buildArrays(adapter.getIndex(), adapter.getItems(), ids, types);
                presenter.sahrePost(ids, types, postUrl, message.getText().toString());
                break;
            case R.id.repost:
                presenter.repost(postUrl, message.getText().toString());
                break;
        }
    }

    @Override
    public void shareIsSuccessful() {
        Toast.makeText(getActivity(), "Запрос завершился успешно", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}