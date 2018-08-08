package com.example.user.vkclient.activities;

import android.annotation.SuppressLint;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.vkclient.R;
import com.example.user.vkclient.Utils;
import com.example.user.vkclient.adapters.UserPostAdapter;
import com.example.user.vkclient.fragments.UserPageFragment;
import com.example.user.vkclient.models.FriendsModel;
import com.example.user.vkclient.models.UserPostModel;
import com.example.user.vkclient.models.UserProfile;
import com.example.user.vkclient.mvp.UserPageActivityMvp.UserPageMvp;
import com.example.user.vkclient.mvp.UserPageActivityMvp.UserPagePresenter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserPageActivity extends AppCompatActivity /*implements UserPageMvp.View*/{

//    private ImageView userPhoto;
//    private TextView lastSeen, userStatus, userFriends, userFollowers, city;
//    private Toolbar toolbar;
//    private RecyclerView userPosts;
//    private UserPostAdapter adapter;
//
//    private UserPagePresenter presenter;
//
//    private NestedScrollView nestedScrollView;
//
//    private int postsOffset = 0;
//    private int postsCount = 0;
//    private int userId;
//    private boolean offPagination = true;

    UserPageFragment userPageFragment = new UserPageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
//        initViews();
//        initFields();


        getSupportFragmentManager().beginTransaction().add(R.id.user_page_frag, userPageFragment).commit();

//        userPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        userPosts.setNestedScrollingEnabled(false);
//        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged()
//            {
//                View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
//
//                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
//                        .getScrollY()));
//
//                if (diff == 40 & postsCount != postsOffset & offPagination) {
//                    presenter.getMoreUserPosts(userId, 1, "photo_100", postsOffset);
//                    offPagination = false;
//                }
//            }
//        });
//        presenter.attachView(this);
//        presenter.getUserProfile(getIntent().getIntExtra("userId", 1), Utils.allFieldsProfle, "nom");
    }

//    @Override
//    public void acceptUserProfile(UserProfile userProfile) {
//        toolbar.setTitle(getResponse(userProfile).getFirst_name() + " " + getResponse(userProfile).getLast_name());
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        setUserPhoto(userProfile);
//        setLastSeen(userProfile);
//        setUserStatus(getResponse(userProfile).getStatus());
//        presenter.getUserFriends(getResponse(userProfile).getId(), "all");
//        setUserFollowers(getResponse(userProfile).getFollowers_count());
//        try {
//            setCity(getResponse(userProfile).getCity().getTitle());
//        } catch (NullPointerException ignore) {
//            city.setVisibility(View.GONE);
//        }
//        userId = getResponse(userProfile).getId();
//        presenter.getUserPosts(userId, 1, "photo_100", postsOffset);
//    }
//
//    @Override
//    public void acceptUserFriends(FriendsModel friendsModel) {
//        if (friendsModel.getResponse().getCount() == 0)
//            userFriends.setVisibility(View.GONE);
//        else if (friendsModel.getResponse().getCount() == 1)
//            userFriends.setText(friendsModel.getResponse().getCount() + " друг");
//        else if(friendsModel.getResponse().getCount()>1 & friendsModel.getResponse().getCount()<5)
//            userFriends.setText(friendsModel.getResponse().getCount() + " друга");
//        else userFriends.setText(friendsModel.getResponse().getCount() + " друзей");
//    }
//
//    @Override
//    public void acceptUserPosts(UserPostModel userPostModel) {
//        postsCount = userPostModel.getResponse().getCount();
//        postsOffset = userPostModel.getResponse().getItems().size();
//        adapter = new UserPostAdapter(userPostModel);
//        userPosts.setAdapter(adapter);
//    }
//
//    @Override
//    public void acceptMoreUserPosts(UserPostModel userPostModel) {
//        int countItems = adapter.getItemCount();
//        adapter.getProfiles().addAll(userPostModel.getResponse().getProfiles());
//        adapter.getItems().addAll(userPostModel.getResponse().getItems());
//        postsOffset = adapter.getItems().size();
//        adapter.notifyItemRangeInserted(countItems, userPostModel.getResponse().getCount());
//        offPagination = true;
//    }
//
//    @Override
//    public void networkError(String error) {
//        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                break;
        }
        return true;
    }@Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        presenter.detachView();
//    }
//
//    private void initViews(){
//        userPhoto = findViewById(R.id.user_img);
//        lastSeen = findViewById(R.id.last_seen);
//        userStatus = findViewById(R.id.user_status);
//        userFriends = findViewById(R.id.user_friends);
//        userFollowers = findViewById(R.id.user_followers);
//        city = findViewById(R.id.city);
//        userPosts = findViewById(R.id.user_posts);
//        nestedScrollView = findViewById(R.id.nested_scroll);
//        toolbar = findViewById(R.id.toolbar);
//    }
//    private void initFields(){
//        presenter = new UserPagePresenter(this);
//    }
//
//
//    private void setUserPhoto(UserProfile userProfile) {
//        try {
//            Picasso.with(this).load(getResponse(userProfile).getCrop_photo().getPhoto().getSizes()
//                    .get(getResponse(userProfile).getCrop_photo().getPhoto().getSizes().size() - 1)
//                    .getUrl()).into(userPhoto);
//        } catch (NullPointerException ignore) {
//            Picasso.with(this).load(getResponse(userProfile).getPhoto_max_orig()).into(userPhoto);
//        }
//    }
//
//    private void setLastSeen(UserProfile userProfile) {
//        if (getResponse(userProfile).getSex() == 1)
//            lastSeen.setText("Была в сети в " + new SimpleDateFormat("hh:mm:ss").format(new Date(getResponse(userProfile).getLast_seen().getTime())));
//        else if (getResponse(userProfile).getSex() == 2)
//            lastSeen.setText("Был в сети в " + new SimpleDateFormat("hh:mm:ss").format(new Date(getResponse(userProfile).getLast_seen().getTime())));
//        else
//            lastSeen.setText("Был(а) в сети в " + new SimpleDateFormat("hh:mm:ss").format(new Date(getResponse(userProfile).getLast_seen().getTime())));
//
//    }
//
//    private void setUserStatus(String s) {
//        if (s.equals("")) {
//            userStatus.setVisibility(View.GONE);
//        } else userStatus.setText(s);
//    }
//
//    private void setUserFollowers(int s) {
//        if (s == 0) {
//            userFollowers.setVisibility(View.GONE);
//        } else if (s == 1) {
//            userFollowers.setText(s + " подписчик");
//        } else if (s > 1 & s < 5) {
//            userFollowers.setText(s + " подписчика");
//        } else userFollowers.setText(s + " подписчиков");
//    }
//
//    private void setCity(String s) {
//         city.setText(s);
//    }
//
//    private UserProfile.ProfileInfo getResponse(UserProfile userProfile) {
//        return userProfile.getResponse().get(0);
//    }
}
