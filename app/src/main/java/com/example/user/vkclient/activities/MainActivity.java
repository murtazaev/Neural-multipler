package com.example.user.vkclient.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.vkclient.fragments.DataFragment;
import com.example.user.vkclient.fragments.UserFeedFragment;
import com.example.user.vkclient.R;
import com.example.user.vkclient.adapters.UserFeedAdapter;

public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;

    private UserFeedFragment userFeedFragment;
    private DataFragment dataFragment;

    private String FEED_FRAG_TAG = "tag1";
    private String DATA_FRAG = "tag2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        dataFragment = (DataFragment) getSupportFragmentManager().findFragmentByTag(DATA_FRAG);

        if (dataFragment == null){
            dataFragment = new DataFragment();
            getSupportFragmentManager().beginTransaction().add(dataFragment, DATA_FRAG).commit();
        }

        userFeedFragment = (UserFeedFragment) getSupportFragmentManager().findFragmentByTag(FEED_FRAG_TAG);

        if (userFeedFragment == null) {
            userFeedFragment = new UserFeedFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.user_feed_frag, userFeedFragment, FEED_FRAG_TAG).commit();
        }
        //progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
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

    public void dataFragSetData(UserFeedAdapter adapter){
        dataFragment.setAdapter(adapter);
    }

    public UserFeedAdapter dataFragGetData(){
        return dataFragment.getAdapter();
    }
}