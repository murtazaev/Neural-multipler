package com.example.user.vkclient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.vkclient.App;
import com.example.user.vkclient.EndlessRecyclerViewScrollListener;
import com.example.user.vkclient.R;
import com.example.user.vkclient.activities.MainActivity;
import com.example.user.vkclient.adapters.UserFeedAdapter;
import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.models.LastCommentModel;
import com.example.user.vkclient.models.VKFeedResponse;
import com.example.user.vkclient.mvp.MainMvp.MainActivityMVP;
import com.example.user.vkclient.mvp.MainMvp.MainPresenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;

public class UserFeedFragment extends Fragment implements MainActivityMVP.View {

    private RecyclerView userFeed;
    private MainPresenter presenter;
    private UserFeedAdapter adapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initFields();

        userFeed.setHasFixedSize(true);
        userFeed.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        userFeed.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) userFeed.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.loadMoreUserFeedItems();
                TransitionManager.beginDelayedTransition((ViewGroup) progressBar.getParent(), new Slide(Gravity.BOTTOM).setDuration(100));
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        presenter.attachView(this);
        if (savedInstanceState == null) {
            presenter.onCheckToken();
        }else {
            adapter = ((MainActivity)getActivity()).dataFragGetData();
            userFeed.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                (App.getSharedPrefHelper()).saveToken(res.accessToken);
                App.getSharedPrefHelper().saveUserId(Integer.parseInt(res.userId));
                App.setUserId(App.getSharedPrefHelper().getUserId());
                presenter.onCheckToken();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getContext(), "Произошла ошибка во время авторизации", Toast.LENGTH_SHORT).show();
            }
        }));

        if (requestCode == 1) {
            try {
                VKFeedResponse.Response.VKFeedObject vkFeedObject = data.getParcelableExtra("feedResponse");
                int pos = data.getIntExtra("pos", 0);
                adapter.getFeedResponses().remove(pos);
                adapter.getFeedResponses().add(pos, vkFeedObject);
                adapter.notifyItemChanged(pos);
            } catch (NullPointerException ignore) {
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("q", "q");
    }

    @Override
    public void handleResponseFromCheckingUserAccessToken(int success) {
        if (success == 1) {
            presenter.getPostsWithLastComm();
        } else {
            presenter.VKLogin();
        }
    }

    @Override
    public void handleResponseFromUserFeed(VKFeedResponse vkFeedResponse, ArrayList<LastCommentModel> lastComm) {
        adapter = new UserFeedAdapter(vkFeedResponse, lastComm);
        ((MainActivity) getActivity()).dataFragSetData(adapter);
        userFeed.setAdapter(adapter);
    }

    @Override
    public void acceptPaginationFeedArray(VKFeedResponse vkFeedResponse, ArrayList<LastCommentModel> lastComm) {
        adapter.getFeedResponses().addAll(vkFeedResponse.getUser_feed().getItems());
        adapter.getLastComm().addAll(lastComm);
        adapter.pickOutGroupId();
        adapter.notifyDataSetChanged();
        TransitionManager.beginDelayedTransition((ViewGroup) progressBar.getParent(), new Slide(Gravity.BOTTOM).setDuration(100));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void networkError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initViews(View container) {
        userFeed = container.findViewById(R.id.user_feed);
        progressBar = container.findViewById(R.id.progress_bar);
    }

    private void initFields() {
        presenter = new MainPresenter(this);
    }

    public void setLike(String type, int ownerId, int itemId, String accessKey, CheckCallback checkCallback) {
        presenter.setLike(type, ownerId, itemId, accessKey, checkCallback);
    }

    public void deleteLike(String type, int ownerId, int itemId, CheckCallback checkCallback) {
        presenter.deleteLike(type, ownerId, itemId, checkCallback);
    }
}
