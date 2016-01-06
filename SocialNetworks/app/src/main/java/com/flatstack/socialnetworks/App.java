package com.flatstack.socialnetworks;

import android.app.Application;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.vk.sdk.VKSdk;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        String twitterKey = getString(R.string.twitter_key);
        String twitterSecret = getString(R.string.twitter_secret);
        String vkAppId = getString(R.string.vk_app_id);
        Fabric.with(this, new TwitterCore(new TwitterAuthConfig(twitterKey, twitterSecret)));
        VKSdk.initialize(this, Integer.valueOf(vkAppId));
    }

}
