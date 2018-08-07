package com.example.user.vkclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.user.vkclient.adapters.UserFeedAdapter;

public class DataFragment extends Fragment {

    private UserFeedAdapter adapter;

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
}
