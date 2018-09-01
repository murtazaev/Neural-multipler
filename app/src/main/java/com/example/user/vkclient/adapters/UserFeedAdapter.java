package com.example.user.vkclient.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.vkclient.Utils;
import com.example.user.vkclient.activities.MainActivity;
import com.example.user.vkclient.R;
import com.example.user.vkclient.activities.PostActivity;
import com.example.user.vkclient.dialogs.RepostDialog;
import com.example.user.vkclient.fragments.UserFeedFragment;
import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.LastCommentModel;
import com.example.user.vkclient.models.VKFeedResponse;
import com.example.user.vkclient.observablepattern.EventManager;
import com.example.user.vkclient.observablepattern.Subscribers;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.squareup.picasso.Picasso;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import at.blogc.android.views.ExpandableTextView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@SuppressLint({"SimpleDateFormat", "SetTextI18n"})
public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapter.UserFeedViewHolder> {

    private ArrayList<VKFeedResponse.Response.VKFeedObject> feedResponses;
    private ArrayList<CommentsModel.Comments.Groups> feedGroups;
    private ArrayList<LastCommentModel> lastComm;

    public UserFeedAdapter(VKFeedResponse feedResponses, ArrayList<LastCommentModel> lastComm) {
        this.feedResponses = feedResponses.getUser_feed().getItems();
        this.feedGroups = feedResponses.getUser_feed().getGroups();
        pickOutGroupId();
        this.lastComm = lastComm;
    }

    public ArrayList<VKFeedResponse.Response.VKFeedObject> getFeedResponses() {
        return feedResponses;
    }

    public ArrayList<LastCommentModel> getLastComm() {
        return lastComm;
    }


    public void pickOutGroupId() {
        for (VKFeedResponse.Response.VKFeedObject vkFeedObject : feedResponses) {
            for (CommentsModel.Comments.Groups groups : feedGroups) {
                if (vkFeedObject.getSource_id() == Integer.parseInt(String.valueOf("-" + groups.getId()))) {
                    vkFeedObject.setParentGroupIcon(groups.getPhoto_100());
                    vkFeedObject.setParentGroupName(groups.getName());
                }
            }
        }
    }

    @NonNull
    @Override
    public UserFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull UserFeedViewHolder holder, int position) {
        VKFeedResponse.Response.VKFeedObject vkFeedObject = feedResponses.get(position);
        try {
            holder.accessKey = vkFeedObject.getAttachments().get(0).getPhoto().getAccess_key();
        } catch (NullPointerException ignored) {
        }
        holder.ownerId = vkFeedObject.getSource_id();
        holder.postId = vkFeedObject.getPost_id();
        holder.type = vkFeedObject.getType();

        canPostComm(vkFeedObject.getComments().getCan_post(),
                vkFeedObject.getComments().getCount(), holder.countComments, holder.comments);


        holder.countLike.setText(vkFeedObject.getLikes().getCount() + " ");
        holder.countRepost.setText(vkFeedObject.getReposts().getCount() + " ");
//        if (lastComment.equals("nonCommEXE1")) {
//            holder.lastComment.setText("Тут пока нет комментариев");
//        } else if (lastComment.equals("cannotAddCommEXE2")) {
//            holder.lastComment.setVisibility(View.GONE);
//        } else {
//            holder.lastComment.setText(lastComm.get(position));
//        }

        userLike(vkFeedObject.getLikes().getUser_likes(), holder.setLike, holder.itemView, holder);

        holder.groupName.setText(vkFeedObject.getParentGroupName());
        holder.postDate.setText(new SimpleDateFormat("hh:mm:ss").format(new Date(vkFeedObject.getDate())));
        holder.feedText.setText(vkFeedObject.getText() + " " + position);

        try {
            loadIconGroup(holder.groupIcon, vkFeedObject.getParentGroupIcon());
        } catch (NullPointerException ignore) {
        }

//        try {
//            loadPostImage(holder.feedImage, vkFeedObject.getAttachments()
//                    .get(0).getPhoto().getSizes().get(vkFeedObject.getAttachments()
//                            .get(0).getPhoto().getSizes().size() - 1)
//                    .getUrl());
//            holder.feedImage.setVisibility(View.VISIBLE);
//        } catch (NullPointerException i) {
//            try {
//                loadPostImage(holder.feedImage, vkFeedObject.getAttachments()
//                        .get(0).getLink().getPhoto().getSizes().
//                                get(vkFeedObject.getAttachments()
//                                        .get(0).getLink().getPhoto().getSizes().size() - 1)
//                        .getUrl());
//            } catch (NullPointerException ignore) {
//                holder.feedImage.setVisibility(View.GONE);
//            }
//        }

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(holder.collageImage.getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        holder.collageImage.setLayoutManager(flexboxLayoutManager);
        if (vkFeedObject.getAttachments() != null)
            holder.collageImage.setAdapter(new CollageImageAdapter(vkFeedObject.getAttachments()));

//        if (holder.feedText.isExpanded()) {
//            holder.expand.setText("Свернуть");
//            holder.feedText.expand();
//        } else {
//            holder.expand.setText("Развернуть");
//            holder.feedText.collapse();
//        }

        if (Utils.countLines(vkFeedObject.getText()) > 6) {
            holder.expand.setVisibility(View.VISIBLE);
            holder.feedText.setMaxLines(6);
        } else {
            holder.expand.setVisibility(View.GONE);
            holder.feedText.setMaxLines(Integer.MAX_VALUE);
        }
    }

    @Override
    public int getItemCount() {
        return feedResponses.size();
    }


    private void canPostComm(int canPost, int countComm, TextView countComments, ImageButton comments) {
        if (canPost == 0) {
            comments.setVisibility(View.GONE);
            countComments.setVisibility(View.GONE);
        } else {
            comments.setVisibility(View.VISIBLE);
            countComments.setVisibility(View.VISIBLE);
            countComments.setText(countComm + " ");
        }
    }

    private void userLike(int userLike, ImageButton setLike, View itemView, UserFeedViewHolder holder) {
        if (userLike == 1) {
            setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_red));
            holder.isLike = true;
        } else {
            setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_gray));
            holder.isLike = false;
        }
    }

    private void loadIconGroup(ImageButton groupIcon, String urlIcon) {
        Picasso
                .with(groupIcon.getContext())
                .load(urlIcon)
                .into(groupIcon);
    }

    private void loadPostImage(ImageView feedImage, String urlImage) {
        Picasso
                .with(feedImage.getContext())
                .load(urlImage)
                .into(feedImage);
        feedImage.setVisibility(View.VISIBLE);
    }


    class UserFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CheckCallback, Subscribers {
        TextView expand, lastComment, groupName, postDate, countComments, countLike, countRepost;
        ImageView feedImage;
        ImageButton setLike, comments, groupIcon, repost;
        ExpandableTextView feedText;
        RecyclerView collageImage;

        int ownerId, postId;
        String accessKey, type;
        boolean isLike;

        EventManager manager;

        UserFeedViewHolder(final View itemView) {
            super(itemView);
            feedText = itemView.findViewById(R.id.feed_text);
            expand = itemView.findViewById(R.id.expand);
            lastComment = itemView.findViewById(R.id.last_comment);
            groupName = itemView.findViewById(R.id.group_name);
            postDate = itemView.findViewById(R.id.post_datet);
            feedImage = itemView.findViewById(R.id.feed_image);
            groupIcon = itemView.findViewById(R.id.icon_group);
            setLike = itemView.findViewById(R.id.set_like);
            comments = itemView.findViewById(R.id.to_comments);
            countComments = itemView.findViewById(R.id.count_comment);
            countLike = itemView.findViewById(R.id.count_like);
            repost = itemView.findViewById(R.id.repost);
            countRepost = itemView.findViewById(R.id.count_repost);
            collageImage = itemView.findViewById(R.id.collage_image);
            manager = new EventManager();

            manager.subscribe("1", this);


            setLike.setOnClickListener(this);
            comments.setOnClickListener(this);
            repost.setOnClickListener(this);
            expand.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.set_like:
//                    if (type.equals("post")) {
//                        if (!isLike) {
//                            ((UserFeedFragment) ((MainActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.user_feed_frag)).setLike(type, ownerId, postId, accessKey, this);
//                            isLike = true;
//                        } else {
//                            ((UserFeedFragment) ((MainActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.user_feed_frag)).deleteLike(type, ownerId, postId, this);
//                            isLike = false;
//                        }
//                    }
                    manager.notifyed(v);
                    break;
                case R.id.to_comments:
                    Intent intent = new Intent(itemView.getContext(), PostActivity.class)
                            .putExtra("feedResponse", feedResponses.get(getAdapterPosition()))
                            .putExtra("pos", getAdapterPosition());
                    ((MainActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.user_feed_frag).startActivityForResult(intent, 1);
                    break;
                case R.id.repost:
                    RepostDialog repostDialog = new RepostDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString("postUrl", "wall" + ownerId + "_" + postId);
                    repostDialog.setArguments(bundle);
                    repostDialog.show(((MainActivity) itemView.getContext()).getFragmentManager(), "repostDialog");
                    break;
                case R.id.expand:
                    if (feedText.isExpanded()) {
                        feedText.collapse();
                        expand.setText("Развернуть");
                        Toast.makeText(itemView.getContext(), Utils.countLines(feedResponses.get(getAdapterPosition()).getText()) + "", Toast.LENGTH_SHORT).show();
                    } else {
                        feedText.expand();
                        expand.setText("Свернуть");
                        Toast.makeText(itemView.getContext(), Utils.countLines(feedResponses.get(getAdapterPosition()).getText()) + "", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        @Override
        public void isSuccessful(int i) {
            if (i == 1) {
                setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_red));
                feedResponses.get(getAdapterPosition()).getLikes().setCount(feedResponses.get(getAdapterPosition()).getLikes().getCount() + 1);
                feedResponses.get(getAdapterPosition()).getLikes().setUser_likes(1);
                countLike.setText(feedResponses.get(getAdapterPosition()).getLikes().getCount() + "");
            } else {
                setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_gray));
                feedResponses.get(getAdapterPosition()).getLikes().setCount(feedResponses.get(getAdapterPosition()).getLikes().getCount() - 1);
                feedResponses.get(getAdapterPosition()).getLikes().setUser_likes(0);
                countLike.setText(feedResponses.get(getAdapterPosition()).getLikes().getCount() + "");
            }
        }

        @Override
        public void accept(View view) {
            switch (view.getId()){
                case R.id.set_like:
                    if (type.equals("post")) {
                        if (!isLike) {
                            ((UserFeedFragment) ((MainActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.user_feed_frag)).setLike(type, ownerId, postId, accessKey, this);
                            isLike = true;
                        } else {
                            ((UserFeedFragment) ((MainActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.user_feed_frag)).deleteLike(type, ownerId, postId, this);
                            isLike = false;
                        }
                    }
                    break;
            }
        }
    }
}
