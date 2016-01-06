package com.flatstack.socialnetworks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.flatstack.socialnetworks.R;
import com.flatstack.socialnetworks.authorization.TwitterLoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigator.setUpActivity(this);
        Navigator.mainScreen();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if you use fragment you need add this line to make twitter authorization work!
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TwitterLoginFragment.TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
