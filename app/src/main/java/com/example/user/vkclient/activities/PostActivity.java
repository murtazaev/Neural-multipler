package com.example.user.vkclient.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.user.vkclient.R;
import com.example.user.vkclient.adapters.PostCommentsAdapter;
import com.example.user.vkclient.fragments.DataFragment;
import com.example.user.vkclient.fragments.PostFragment;
import com.example.user.vkclient.interfaces.FragmentsBackPressed;
import com.example.user.vkclient.models.CommentsModel;


@SuppressLint("SetTextI18n")
public class PostActivity extends AppCompatActivity {

    PostFragment postFragment;
    DataFragment dataFragment;

    FragmentsBackPressed.ReturnVKFeedObject returnVKFeedObject;
    private String POST_FRAG = "tag1";
    private String DATA_FRAG = "tag2";

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataFragment = (DataFragment) getSupportFragmentManager().findFragmentByTag(DATA_FRAG);

        if(dataFragment == null){
            dataFragment = new DataFragment();
            getSupportFragmentManager().beginTransaction().add(dataFragment, DATA_FRAG).commit();
        }


        postFragment = (PostFragment) getSupportFragmentManager().findFragmentByTag(POST_FRAG);
        returnVKFeedObject = postFragment;

        if (postFragment == null) {
            postFragment = new PostFragment();
            returnVKFeedObject = postFragment;
            getSupportFragmentManager().beginTransaction().add(R.id.post_frag, postFragment, POST_FRAG).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent();
                i.putExtra("feedResponse", returnVKFeedObject.returnVKFeedObject());
                i.putExtra("pos", getIntent().getIntExtra("pos", 0));
                setResult(RESULT_OK, i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("feedResponse", returnVKFeedObject.returnVKFeedObject());
        i.putExtra("pos", getIntent().getIntExtra("pos", 0));
        setResult(RESULT_OK, i);
        finish();
    }

    public void setCommentsToDataFrag(PostCommentsAdapter postCommentsAdapter){
        dataFragment.setPostCommentsAdapter(postCommentsAdapter);
    }
    public PostCommentsAdapter getCommentsToDataFrag(){
        return dataFragment.getPostCommentsAdapter();
    }
}