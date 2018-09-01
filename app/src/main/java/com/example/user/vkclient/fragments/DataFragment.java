package com.example.user.vkclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.user.vkclient.adapters.PostCommentsAdapter;
import com.example.user.vkclient.adapters.UserFeedAdapter;
import com.example.user.vkclient.models.CommentsModel;

public class DataFragment extends Fragment {

    private UserFeedAdapter adapter;
    private PostCommentsAdapter postCommentsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public UserFeedAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(UserFeedAdapter adapter) {
        this.adapter = adapter;
    }

    public PostCommentsAdapter getPostCommentsAdapter() {
        return postCommentsAdapter;
    }

    public void setPostCommentsAdapter(PostCommentsAdapter postCommentsAdapter) {
        this.postCommentsAdapter = postCommentsAdapter;
    }
}
