package com.example.user.vkclient.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.vkclient.R;
import com.example.user.vkclient.dialogs.RepostDialog;
import com.example.user.vkclient.interfaces.RepostCallback;
import com.example.user.vkclient.models.CommentsModel;
import com.example.user.vkclient.models.ConversationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RepostConversationsAdapter extends RecyclerView.Adapter<RepostConversationsAdapter.RepostConversationsViewHolder> {

    private ArrayList<ConversationModel.Response.Item> items;
    private ArrayList<Integer> index;
    private RepostCallback callback;

    public RepostConversationsAdapter(ArrayList<ConversationModel.Response.Item> items, RepostCallback callback) {
        this.items = items;
        index = new ArrayList<>(items.size());
        this.callback = callback;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }

    public ArrayList<ConversationModel.Response.Item> getItems() {
        return items;
    }

    @NonNull
    @Override
    public RepostConversationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepostConversationsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repost_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepostConversationsViewHolder holder, int position) {
        ConversationModel.Response.Item item = items.get(position);
        try {
            holder.repostListName.setText(item.getConversation().getChat_settings().getTitle());
            chatImageAndTitle(holder.repostLiatPhoto,
                    item.getConversation().getChat_settings().getPhoto().getPhoto_100(), holder.itemView);
        } catch (NullPointerException i) {
            conversationImageAndTitle(holder.repostListName,
                    holder.repostLiatPhoto, item.getrName(), item.getrPhoto(), holder.itemView);
        }
        arrayIsContainsItem(position, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private void arrayIsContainsItem(int position, View itemView) {
        if (index.contains(position)) {
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.gray));
        } else {
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
        }
    }
    private void chatImageAndTitle(ImageView repostLiatPhoto, String photo, View itemView) {
        try {
            Picasso.with(itemView.getContext()).load(photo).into(repostLiatPhoto);
        } catch (NullPointerException ignore) {
        }
    }
    private void conversationImageAndTitle(TextView repostListName, ImageView repostListPhoto, String name, String photo, View itemView) {
        Picasso.with(itemView.getContext()).load(photo).into(repostListPhoto);
        repostListName.setText(name);
    }


    public class RepostConversationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView repostLiatPhoto;
        TextView repostListName;

        RepostConversationsViewHolder(View itemView) {
            super(itemView);
            repostLiatPhoto = itemView.findViewById(R.id.repost_list_photo);
            repostListName = itemView.findViewById(R.id.repost_list_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.repost_list_container:
                    if (index.contains(getAdapterPosition())) {
                        index.remove((Integer) getAdapterPosition());
                        itemView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
                        if (index.size() == 0) {
                            callback.setVisibility(0);
                        }
                    } else {
                        index.add(getAdapterPosition());
                        itemView.setBackgroundColor(itemView.getResources().getColor(R.color.gray));
                        if (index.size() == 1) {
                            callback.setVisibility(1);
                        }
                    }
                    break;
            }
        }
    }
}
