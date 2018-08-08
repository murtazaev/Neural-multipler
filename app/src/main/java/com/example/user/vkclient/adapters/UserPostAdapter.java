package com.example.user.vkclient.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.vkclient.R;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.UserPostModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder> {

    private ArrayList<UserPostModel.UserPosts.Item> items;
    private ArrayList<CommentsModel.Comments.Profiles> profiles;

    public UserPostAdapter(UserPostModel userPostModel) {
        items = userPostModel.getResponse().getItems();
        profiles = userPostModel.getResponse().getProfiles();
    }

    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostViewHolder holder, int position) {
        postMater(holder.userImage, holder.name, holder.date, position);
        postText(holder.text, position);

        try {
            holder.postImage.setVisibility(View.VISIBLE);
            postImage(holder.postImage, items.get(position).getAttachments()
                    .get(0).getPhoto().getSizes().
                            get(items.get(position).getAttachments()
                                    .get(0).getPhoto().getSizes().size() - 1)
                    .getUrl());
        } catch (NullPointerException ignore) {
            holder.postImage.setVisibility(View.GONE);
        }

        holder.countRepost.setText(items.get(position).getReposts().getCount() + "");
        holder.countComm.setText(items.get(position).getComments().getCount() + "");
        holder.countLike.setText(items.get(position).getLikes().getCount() + "");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private void postMater(ImageButton userImage, TextView name, TextView date, int position) {
        for (CommentsModel.Comments.Profiles profile : profiles) {
            if (profile.getId() == items.get(position).getFrom_id()) {
                Picasso.with(userImage.getContext()).load(profile.getPhoto_100()).into(userImage);
                name.setText(profile.getName());
                date.setText(items.get(position).getDate() + "");
            }
        }
    }

    private void postText(TextView text, int position) {
        if (!items.get(position).getText().equals("")) {
            text.setVisibility(View.VISIBLE);
            text.setText(items.get(position).getText());
        } else text.setVisibility(View.GONE);
        text.setText(position+" ");
    }

    private void postImage(ImageView postImage, String urlImage) {
        Picasso.with(postImage.getContext()).load(urlImage).into(postImage);
    }


    public ArrayList<UserPostModel.UserPosts.Item> getItems() {
        return items;
    }

    public ArrayList<CommentsModel.Comments.Profiles> getProfiles() {
        return profiles;
    }


    class UserPostViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, text, countRepost, countComm, countLike;
        ImageButton userImage, repost, toComm, setLike;
        ImageView postImage;

        public UserPostViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.icon_group);
            name = itemView.findViewById(R.id.group_name);
            date = itemView.findViewById(R.id.post_datet);
            text = itemView.findViewById(R.id.feed_text);
            postImage = itemView.findViewById(R.id.feed_image);
            countRepost = itemView.findViewById(R.id.count_repost);
            repost = itemView.findViewById(R.id.repost);
            countComm = itemView.findViewById(R.id.count_comment);
            toComm = itemView.findViewById(R.id.to_comments);
            countLike = itemView.findViewById(R.id.count_like);
            setLike = itemView.findViewById(R.id.set_like);
        }
    }
}
