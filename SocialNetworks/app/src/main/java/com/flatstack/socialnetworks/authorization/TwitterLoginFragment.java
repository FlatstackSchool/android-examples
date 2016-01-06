package com.flatstack.socialnetworks.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class TwitterLoginFragment extends Fragment {

    // NOTE if you use fragment you need modify hosted activity see {@link com.flatstack.socialnetworks.ui.MainActivity#onActivityResult MainActivity}

    public static final String TAG = "twitterFragmentTag";

    private TwitterLoginButton twitterBtn;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            firstFragmentLaunch();
        }
    }

    protected void firstFragmentLaunch() {
        twitterBtn = new TwitterLoginButton(getContext());
        twitterBtn.setCallback(new Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {
                TwitterAuthToken authToken = result.data.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                Toast.makeText(getContext(), "token: " + token + " , secret: " + secret, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // TODO do something when fail
            }
        });
        twitterBtn.performClick();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterBtn.onActivityResult(requestCode, resultCode, data);
    }

}
