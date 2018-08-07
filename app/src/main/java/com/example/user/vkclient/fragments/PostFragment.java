package com.example.user.vkclient.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.vkclient.App;
import com.example.user.vkclient.R;
import com.example.user.vkclient.adapters.PostCommentsAdapter;
import com.example.user.vkclient.dialogs.RepostDialog;
import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.interfaces.FragmentsBackPressed;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.VKFeedResponse;
import com.example.user.vkclient.mvp.PostActivityMvp.PostActivityMVP;
import com.example.user.vkclient.mvp.PostActivityMvp.PostActivityPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SetTextI18n")
public class PostFragment extends Fragment implements PostActivityMVP.View, View.OnClickListener, CheckCallback, FragmentsBackPressed.ReturnVKFeedObject {

    private RecyclerView recyclerComments;
    private ImageView postImage;
    private ImageButton send, setLike, reposts, iconGroup;
    private EditText massageForComm;
    private TextView textPost, countLikes, countReposts, groupName, postDate, loadMoreComm;

    private PostCommentsAdapter adapter;

    private int replyCommId;
    private int commentsOffset = 0;

    private VKFeedResponse.Response.VKFeedObject vkFeedObject;

    public PostActivityPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initFields();

        presenter.attachView(this);
        presenter.getComments(vkFeedObject.getSource_id(),
                vkFeedObject.getPost_id(),
                1, "desc", 1, 0, 0);

        loadPostImage();
        presenter.loadImage(vkFeedObject.getParentGroupIcon()).into(iconGroup);
        groupName.setText(vkFeedObject.getParentGroupName());
        textPost.setText(vkFeedObject.getText());
        checkLike();

        countLikes.setText(vkFeedObject.getLikes().getCount() + " ");
        countReposts.setText(vkFeedObject.getReposts().getCount() + " ");
        postDate.setText(new SimpleDateFormat("hh:mm:ss").format(new Date(vkFeedObject.getDate())));

        recyclerComments.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                presenter.sendMessage(vkFeedObject.getSource_id(),
                        vkFeedObject.getPost_id(),
                        massageForComm.getText().toString(), replyCommId);
                break;

            case R.id.set_like:
                if (vkFeedObject.getLikes().getUser_likes() == 1) {
                    presenter.deleteLike("post", vkFeedObject.getSource_id(), vkFeedObject.getPost_id(), this);
                } else {
                    try {
                        presenter.setLike("post", vkFeedObject.getSource_id(), vkFeedObject.getPost_id(), vkFeedObject.getAttachments().get(0).getPhoto().getAccess_key(), this);
                    } catch (NullPointerException ignore) {
                        presenter.setLike("post", vkFeedObject.getSource_id(), vkFeedObject.getPost_id(), "", this);
                    }
                }
                break;

            case R.id.repost:
                RepostDialog repostDialog = new RepostDialog();
                Bundle bundle = new Bundle();
                bundle.putString("postUrl", "wall" + vkFeedObject.getSource_id() + "_" + vkFeedObject.getPost_id());
                repostDialog.setArguments(bundle);
                repostDialog.show(getActivity().getFragmentManager(), "repostDialog");
                break;

            case R.id.load_more_comments:
                presenter.loadMorecomments(vkFeedObject.getSource_id(),
                        vkFeedObject.getPost_id(),
                        1, "desc", 1, 0, commentsOffset);
                break;

        }
    }

    @Override
    public void isSuccessful(int i) {
        if (i == 1) {
            setLike.setImageDrawable(getResources().getDrawable(R.drawable.likes_red));
            vkFeedObject.getLikes().setCount(vkFeedObject.getLikes().getCount() + 1);
            vkFeedObject.getLikes().setUser_likes(1);
            countLikes.setText(vkFeedObject.getLikes().getCount() + "");
        } else {
            setLike.setImageDrawable(getResources().getDrawable(R.drawable.likes_gray));
            vkFeedObject.getLikes().setCount(vkFeedObject.getLikes().getCount() - 1);
            vkFeedObject.getLikes().setUser_likes(0);
            countLikes.setText(vkFeedObject.getLikes().getCount() + "");
        }
    }

    @Override
    public void setComments(CommentsModel comments) {
        allotmentNameAndPhoto(comments);
        commentsOffset = comments.getResponse().getItems().size();
        adapter = new PostCommentsAdapter(comments.getResponse().getItems(), vkFeedObject.getSource_id());
        recyclerComments.setAdapter(adapter);
        numberOfAvailableComments(comments.getResponse().getCount());
    }

    @Override
    public void acceptMoreComments(CommentsModel comments) {
        allotmentNameAndPhoto(comments);
        commentsOffset = adapter.getComments().size() + comments.getResponse().getItems().size();
        adapter.addComments(comments.getResponse().getItems());
        numberOfAvailableComments(comments.getResponse().getCount());
    }

    @Override
    public void networkError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isSuccessfulSend(String text, int id) {
        CommentsModel.Comments.Comment comment = new CommentsModel.Comments.Comment();
        comment.setUser_name("Абдулмуталим Муртазаев");
        comment.setText(text);
        comment.setLikes(new CommentsModel.Comments.Comment.Like());
        comment.getLikes().setUser_likes(0);
        comment.getLikes().setCount(0);
        comment.setId(id);
        comment.setFrom_id(App.getUserId());
        adapter.getComments().add(0, comment);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    private void allotmentNameAndPhoto(CommentsModel comments) {
        for (CommentsModel.Comments.Comment comment : comments.getResponse().getItems()) {
            for (CommentsModel.Comments.Profiles profiles : comments.getResponse().getProfiles()) {
                if (comment.getFrom_id() == profiles.getId()) {
                    comment.setUser_avatar(profiles.getPhoto_100());
                    comment.setUser_name(profiles.getName());
                }
            }
        }
    }

    private void numberOfAvailableComments(int commentsCount) {
        if (commentsCount - commentsOffset == 0) {
            loadMoreComm.setVisibility(View.GONE);
        } else
            loadMoreComm.setText("Загрузить еще " + (commentsCount - commentsOffset) + " комментариев");
    }

    private void checkLike() {
        if (vkFeedObject.getLikes().getUser_likes() == 1) {
            setLike.setImageDrawable(getResources().getDrawable(R.drawable.likes_red));
        } else {
            setLike.setImageDrawable(getResources().getDrawable(R.drawable.likes_gray));
        }
    }

    private void initViews(View view) {
        recyclerComments = view.findViewById(R.id.recycler_comments);
        loadMoreComm = view.findViewById(R.id.load_more_comments);
        postImage = view.findViewById(R.id.post_image);
        send = view.findViewById(R.id.send);
        massageForComm = view.findViewById(R.id.massage_for_comm);
        textPost = view.findViewById(R.id.textPost);
        setLike = view.findViewById(R.id.set_like);
        reposts = view.findViewById(R.id.repost);
        countLikes = view.findViewById(R.id.count_like);
        countReposts = view.findViewById(R.id.count_repost);
        iconGroup = view.findViewById(R.id.icon_group);
        groupName = view.findViewById(R.id.group_name);
        postDate = view.findViewById(R.id.post_date);
        send.setOnClickListener(this);
        setLike.setOnClickListener(this);
        reposts.setOnClickListener(this);
        loadMoreComm.setOnClickListener(this);
    }

    private void initFields() {
        presenter = new PostActivityPresenter(this);
        vkFeedObject = getActivity().getIntent().getParcelableExtra("feedResponse");
    }

    private void loadPostImage() {
        try {
            if (!vkFeedObject
                    .getAttachments()
                    .get(0).getPhoto().getSizes()
                    .get(vkFeedObject.getAttachments()
                            .get(0).getPhoto().getSizes().size() - 1)
                    .equals("")) {

                presenter.loadImage(vkFeedObject.getAttachments()
                        .get(0).getPhoto().getSizes()
                        .get(vkFeedObject.getAttachments()
                                .get(0).getPhoto().getSizes().size() - 1).getUrl())
                        .into(postImage);

            }
        } catch (NullPointerException ignore) { }
    }


    public void setReplyCommId(int replyCommId) {
        this.replyCommId = replyCommId;
    }

    public void setMessageText(String text) {
        massageForComm.setText(text);
    }

    public void choiceSelfAction(final int commId, String text, final CheckCallback.CommCheck commCheck, final int position) {
        final EditText editText = new EditText(getContext());
        editText.setText(text);
        new AlertDialog.Builder(getContext())
                .setTitle("Выберите действие")
                .setView(editText)
                .setItems(new CharSequence[]{"Редактировать", "Удалить"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (!editText.getText().toString().equals("")) {
                                    presenter.editComment(vkFeedObject.getSource_id(), commId, editText.getText().toString(), commCheck, position);
                                } else
                                    Toast.makeText(getContext(), "Введите текст.", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                presenter.deleteComment(vkFeedObject.getSource_id(), commId, commCheck, position);
                                Toast.makeText(getContext(), "Удаление", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).show();
    }

    public void choiceAction() {
        new AlertDialog.Builder(getContext())
                .setTitle("Выберите действие")
                .setItems(new CharSequence[]{"Скопировать текст комментария", "Пожаловаться"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public VKFeedResponse.Response.VKFeedObject returnVKFeedObject() {
        return vkFeedObject;
    }
}
