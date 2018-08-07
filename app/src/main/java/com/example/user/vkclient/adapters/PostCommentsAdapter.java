package com.example.user.vkclient.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.user.vkclient.App;
import com.example.user.vkclient.R;
import com.example.user.vkclient.activities.PostActivity;
import com.example.user.vkclient.activities.UserPageActivity;
import com.example.user.vkclient.fragments.PostFragment;
import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.models.CommentsModel;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class PostCommentsAdapter extends RecyclerView.Adapter<PostCommentsAdapter.PostCommentsViewHolder> {

    private ArrayList<CommentsModel.Comments.Comment> comments;
    private int ownerId;

    public PostCommentsAdapter(ArrayList<CommentsModel.Comments.Comment> comments, int ownerId) {
        this.comments = comments;
        this.ownerId = ownerId;
    }

    @NonNull
    @Override
    public PostCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostCommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull PostCommentsViewHolder holder, int position) {
        CommentsModel.Comments.Comment comment = comments.get(position);

       // ((PostActivity) holder.itemView.getContext()).presenter.loadImage(comment.getUser_avatar()).into(holder.userAvatar);
        ((PostFragment)(((PostActivity) holder.itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).presenter.loadImage(comment.getUser_avatar()).into(holder.userAvatar);

        holder.userName.setText(comment.getUser_name());

        userLike(comment.getLikes().getUser_likes(), holder.setLike, holder.itemView);

        holder.text.setText(comment.getText());
        holder.countLike.setText(comment.getLikes().getCount() + "");

        userComment(comment.getFrom_id(), holder.itemView);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    private void userLike(int userLike, ImageButton setLike, View itemView){
        if (userLike == 1) {
            setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_red));
        } else {
            setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_gray));
        }
    }

    private void userComment(int fromId, View itemView) {
        if (fromId == App.getUserId()) {
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.userCommColor));
        } else {
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
        }
    }

    public void setComments(ArrayList<CommentsModel.Comments.Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<CommentsModel.Comments.Comment> getComments() {
        return comments;
    }

    public void addComments(ArrayList<CommentsModel.Comments.Comment> comments){
        int c = getItemCount();
        this.comments.addAll(comments);
        notifyItemRangeInserted(c, comments.size());
    }


    public class PostCommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CheckCallback.CommCheck, CheckCallback {
        ImageButton userAvatar;
        TextView userName, text, countLike;
        ImageButton setLike, reply;


        PostCommentsViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userName = itemView.findViewById(R.id.user_name);
            setLike = itemView.findViewById(R.id.set_like);
            countLike = itemView.findViewById(R.id.count_like);
            reply = itemView.findViewById(R.id.reply);
            itemView.setOnClickListener(this);
            setLike.setOnClickListener(this);
            reply.setOnClickListener(this);
            userAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.post_comm_container:
                    if (comments.get(getAdapterPosition()).getFrom_id() == App.getUserId()) {
//                        ((PostActivity) itemView.getContext()).choiceSelfAction(comments.get(getAdapterPosition()).getId(), text.getText().toString(), this, getAdapterPosition());
                        ((PostFragment)(((PostActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).choiceSelfAction(comments.get(getAdapterPosition()).getId(), text.getText().toString(), this, getAdapterPosition());
                    } else {
//                        ((PostActivity) itemView.getContext()).choiceAction();
                        ((PostFragment)(((PostActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).choiceAction();
                    }
                    break;
                case R.id.set_like:
                    if (comments.get(getAdapterPosition()).getLikes().getUser_likes() == 1) {
//                        ((PostActivity) itemView.getContext()).presenter.deleteLike("comment", ownerId, comments.get(getAdapterPosition()).getId(), this);
                        ((PostFragment)(((PostActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).presenter.deleteLike("comment", ownerId, comments.get(getAdapterPosition()).getId(), this);
                    } else {
//                        ((PostActivity) itemView.getContext()).presenter.setLike("comment", ownerId, comments.get(getAdapterPosition()).getId(), "", this);
                        ((PostFragment)(((PostActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).presenter.setLike("comment", ownerId, comments.get(getAdapterPosition()).getId(), "", this);
                    }
                    break;
                case R.id.reply:
//                    ((PostActivity) itemView.getContext()).setReplyCommId(comments.get(getAdapterPosition()).getId());
//                    ((PostActivity) itemView.getContext()).setMessageText(comments.get(getAdapterPosition()).getUser_name());
                    ((PostFragment)(((PostActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).setReplyCommId(comments.get(getAdapterPosition()).getId());
                    ((PostFragment)(((PostActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).setMessageText((comments.get(getAdapterPosition()).getUser_name()));
                    break;
                case R.id.user_avatar:
                    Intent intent = new Intent(itemView.getContext(), UserPageActivity.class);
                    intent.putExtra("userId", comments.get(getAdapterPosition()).getFrom_id());
//                    ((PostActivity) itemView.getContext()).startActivityForResult(intent, 2);
                    ((PostFragment)(((PostActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.post_frag))).startActivityForResult(intent, 2);
                    break;
            }
        }

        @Override
        public void isSuccessfulEdit(int position, String text) {
            comments.get(position).setText(text);
            notifyDataSetChanged();
        }

        @Override
        public void isSuccessfulDelete(int position) {
            comments.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public void isSuccessful(int i) {
            if (i == 1) {
                setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_red));
                comments.get(getAdapterPosition()).getLikes().setCount(comments.get(getAdapterPosition()).getLikes().getCount() + 1);
                comments.get(getAdapterPosition()).getLikes().setUser_likes(1);
                countLike.setText(comments.get(getAdapterPosition()).getLikes().getCount() + "");
            } else {
                setLike.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.likes_gray));
                comments.get(getAdapterPosition()).getLikes().setCount(comments.get(getAdapterPosition()).getLikes().getCount() - 1);
                comments.get(getAdapterPosition()).getLikes().setUser_likes(0);
                countLike.setText(comments.get(getAdapterPosition()).getLikes().getCount() + "");
            }
        }
    }
}

