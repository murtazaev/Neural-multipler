package com.example.user.vkclient.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.vkclient.R;
import com.example.user.vkclient.models.VKFeedResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CollageImageAdapter extends RecyclerView.Adapter<CollageImageAdapter.CollageImageViewHolder> {

    private ArrayList<VKFeedResponse.Response.VKFeedObject.Attachments> images;

    CollageImageAdapter(ArrayList<VKFeedResponse.Response.VKFeedObject.Attachments> images) {
        this.images = new ArrayList<>();
        for(VKFeedResponse.Response.VKFeedObject.Attachments attachments : images){
            if(attachments.getType().equals("photo")){
                this.images.add(attachments);
            }
        }
    }

    @NonNull
    @Override
    public CollageImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollageImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollageImageViewHolder holder, int position) {
        VKFeedResponse.Response.VKFeedObject.Attachments.VKPhoto vkPhoto = images.get(position).getPhoto();
        ViewGroup.LayoutParams params = holder.postImage.getLayoutParams();

        try {
            switch (position) {
                case 0:
                    params.width = vkPhoto.getSizes().get(vkPhoto.getSizes().size() - 1).getWidth() / 2;
                    params.height = vkPhoto.getSizes().get(vkPhoto.getSizes().size() - 1).getHeight() / 2;
                    break;
                case 1:
                    params.width = vkPhoto.getSizes().get(vkPhoto.getSizes().size() - 1).getWidth() / 2;
                    params.height = vkPhoto.getSizes().get(vkPhoto.getSizes().size() - 1).getHeight() / 2;
                    break;
                default:
                    params.width = vkPhoto.getSizes().get(vkPhoto.getSizes().size() - 1).getWidth() / 4;
                    params.height = vkPhoto.getSizes().get(vkPhoto.getSizes().size() - 1).getHeight() / 4;
                    break;
            }

            Picasso.with(holder.postImage.getContext()).load(images
                    .get(position).getPhoto().getSizes()
                    .get(images.get(position).getPhoto().getSizes().size() - 1)
                    .getUrl()).into(holder.postImage);
        } catch (NullPointerException ignore) {
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class CollageImageViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;

        CollageImageViewHolder(View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_image);
        }
    }
}
