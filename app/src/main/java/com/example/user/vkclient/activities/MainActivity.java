package com.example.user.vkclient.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.vkclient.App;
import com.example.user.vkclient.EndlessRecyclerViewScrollListener;
import com.example.user.vkclient.fragments.UserFeedFragment;
import com.example.user.vkclient.mvp.MainMvp.MainActivityMVP;
import com.example.user.vkclient.mvp.MainMvp.MainPresenter;
import com.example.user.vkclient.R;
import com.example.user.vkclient.adapters.UserFeedAdapter;
import com.example.user.vkclient.interfaces.CheckCallback;
import com.example.user.vkclient.models.LastCommentModel;
import com.example.user.vkclient.models.VKFeedResponse;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;

    private UserFeedFragment userFeedFragment = new UserFeedFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        getSupportFragmentManager().beginTransaction().add(R.id.user_feed_frag, userFeedFragment).commit();

        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.massages:
                Toast.makeText(this, "vsdvdf", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
    }
}