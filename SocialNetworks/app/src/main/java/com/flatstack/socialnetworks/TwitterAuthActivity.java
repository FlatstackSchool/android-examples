package com.flatstack.socialnetworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class TwitterAuthActivity extends AppCompatActivity {

    private static final String KEY_FIRST_LAUNCH = "firstLaunch";

    private boolean firstLaunch = true;

    private TwitterAuthClient twitterAuthClient;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_social_auth);
        if (savedInstanceState != null) {
            firstLaunch = savedInstanceState.getBoolean(KEY_FIRST_LAUNCH, true);
        }
        if (firstLaunch) {
            // in real application create twitter client as singletone in dagger module
            if (twitterAuthClient == null) {
                twitterAuthClient = new TwitterAuthClient();
            }
            twitterAuthClient.authorize(this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    authorizeWithTwitter(result.data.getAuthToken());
                }

                @Override
                public void failure(TwitterException exception) {
                    finish();
                }
            });
            firstLaunch = false;
        }
    }

    private void authorizeWithTwitter(TwitterAuthToken authToken) {
        Toast.makeText(TwitterAuthActivity.this, "token is :" + authToken.token, Toast.LENGTH_SHORT)
            .show();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (twitterAuthClient == null) {
            twitterAuthClient = new TwitterAuthClient();
        }
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_FIRST_LAUNCH, firstLaunch);
    }
}
