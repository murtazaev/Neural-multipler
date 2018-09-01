package com.example.user.vkclient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.user.vkclient.R;
import com.example.user.vkclient.fragments.UserPageFragment;

public class UserPageActivity extends AppCompatActivity {

    UserPageFragment userPageFragment;
    private String USER_PAGE_FRAG = "tag1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        userPageFragment = (UserPageFragment) getSupportFragmentManager().findFragmentByTag(USER_PAGE_FRAG);

        if (userPageFragment == null) {
            userPageFragment = new UserPageFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.user_page_frag, userPageFragment, USER_PAGE_FRAG).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

}
