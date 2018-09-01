package com.example.user.vkclient.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.vkclient.R;
import com.example.user.vkclient.Utils;
import com.example.user.vkclient.activities.UserPageActivity;
import com.example.user.vkclient.adapters.UserPostAdapter;
import com.example.user.vkclient.models.FriendsModel;
import com.example.user.vkclient.models.UserPostModel;
import com.example.user.vkclient.models.UserProfile;
import com.example.user.vkclient.mvp.UserPageActivityMvp.UserPageMvp;
import com.example.user.vkclient.mvp.UserPageActivityMvp.UserPagePresenter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({"SetTextI18n", "SimpleDateFormat"})
public class UserPageFragment extends Fragment implements UserPageMvp.View {

    private ImageView userPhoto;
    private TextView lastSeen, userStatus, userFriends, userFollowers, city;
    private Toolbar toolbar;
    private RecyclerView userPosts;
    private UserPostAdapter adapter;

    private UserPagePresenter presenter;

    private NestedScrollView nestedScrollView;

    private int postsOffset = 0;
    private int postsCount = 0;
    private int userId;
    private boolean offPagination = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initFields();

        userPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        userPosts.setNestedScrollingEnabled(false);
        userPosts.setHasFixedSize(false);
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged()
            {
                View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
                        .getScrollY()));

                if (diff == 40 & postsCount != postsOffset & offPagination) {
                    presenter.getMoreUserPosts(userId, 1, "photo_100", postsOffset);
                    offPagination = false;
                }
            }
        });
        presenter.attachView(this);
        presenter.getUserProfile(getActivity().getIntent().getIntExtra("userId", 1), Utils.allFieldsProfle, "nom");
    }

    @Override
    public void acceptUserProfile(UserProfile userProfile) {
        toolbar.setTitle(getResponse(userProfile).getFirst_name() + " " + getResponse(userProfile).getLast_name());
        ((UserPageActivity)getActivity()).setSupportActionBar(toolbar);
        if (((UserPageActivity)getActivity()).getSupportActionBar() != null)
            ((UserPageActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUserPhoto(userProfile);
        setLastSeen(userProfile);
        setUserStatus(getResponse(userProfile).getStatus());
        presenter.getUserFriends(getResponse(userProfile).getId(), "all");
        setUserFollowers(getResponse(userProfile).getFollowers_count());
        try {
            setCity(getResponse(userProfile).getCity().getTitle());
        } catch (NullPointerException ignore) {
            city.setVisibility(View.GONE);
        }
        userId = getResponse(userProfile).getId();
        presenter.getUserPosts(userId, 1, "photo_100", postsOffset);
    }

    @Override
    public void acceptUserFriends(FriendsModel friendsModel) {
        if (friendsModel.getResponse().getCount() == 0)
            userFriends.setVisibility(View.GONE);
        else if (friendsModel.getResponse().getCount() == 1)
            userFriends.setText(friendsModel.getResponse().getCount() + " друг");
        else if(friendsModel.getResponse().getCount()>1 & friendsModel.getResponse().getCount()<5)
            userFriends.setText(friendsModel.getResponse().getCount() + " друга");
        else userFriends.setText(friendsModel.getResponse().getCount() + " друзей");
    }

    @Override
    public void acceptUserPosts(UserPostModel userPostModel) {
        postsCount = userPostModel.getResponse().getCount();
        postsOffset = userPostModel.getResponse().getItems().size();
        adapter = new UserPostAdapter(userPostModel);
        userPosts.setAdapter(adapter);
    }

    @Override
    public void acceptMoreUserPosts(UserPostModel userPostModel) {
        int countItems = adapter.getItemCount();
        adapter.getProfiles().addAll(userPostModel.getResponse().getProfiles());
        adapter.getItems().addAll(userPostModel.getResponse().getItems());
        postsOffset = adapter.getItems().size();
        adapter.notifyItemRangeInserted(countItems, userPostModel.getResponse().getCount());
        offPagination = true;
    }

    @Override
    public void networkError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    private void initViews(View view){
        userPhoto = view.findViewById(R.id.user_img);
        lastSeen = view.findViewById(R.id.last_seen);
        userStatus = view.findViewById(R.id.user_status);
        userFriends = view.findViewById(R.id.user_friends);
        userFollowers = view.findViewById(R.id.user_followers);
        city = view.findViewById(R.id.city);
        userPosts = view.findViewById(R.id.user_posts);
        nestedScrollView = view.findViewById(R.id.nested_scroll);
        toolbar = view.findViewById(R.id.toolbar);
    }
    private void initFields(){
        presenter = new UserPagePresenter(this);
    }

    private void setUserPhoto(UserProfile userProfile) {
        try {
            Picasso.with(getContext()).load(getResponse(userProfile).getCrop_photo().getPhoto().getSizes()
                    .get(getResponse(userProfile).getCrop_photo().getPhoto().getSizes().size() - 1)
                    .getUrl()).into(userPhoto);
        } catch (NullPointerException ignore) {
            Picasso.with(getContext()).load(getResponse(userProfile).getPhoto_max_orig()).into(userPhoto);
        }
    }


    private void setLastSeen(UserProfile userProfile) {
        if (getResponse(userProfile).getSex() == 1)
            lastSeen.setText("Была в сети в " + new SimpleDateFormat("hh:mm:ss").format(new Date(getResponse(userProfile).getLast_seen().getTime())));
        else if (getResponse(userProfile).getSex() == 2)
            lastSeen.setText("Был в сети в " + new SimpleDateFormat("hh:mm:ss").format(new Date(getResponse(userProfile).getLast_seen().getTime())));
        else
            lastSeen.setText("Был(а) в сети в " + new SimpleDateFormat("hh:mm:ss").format(new Date(getResponse(userProfile).getLast_seen().getTime())));

    }

    private void setUserStatus(String s) {
        if (s.equals("")) {
            userStatus.setVisibility(View.GONE);
        } else userStatus.setText(s);
    }

    private void setUserFollowers(int s) {
        if (s == 0) {
            userFollowers.setVisibility(View.GONE);
        } else if (s == 1) {
            userFollowers.setText(s + " подписчик");
        } else if (s > 1 & s < 5) {
            userFollowers.setText(s + " подписчика");
        } else userFollowers.setText(s + " подписчиков");
    }

    private void setCity(String s) {
        city.setText(s);
    }

    private UserProfile.ProfileInfo getResponse(UserProfile userProfile) {
        return userProfile.getResponse().get(0);
    }


}
