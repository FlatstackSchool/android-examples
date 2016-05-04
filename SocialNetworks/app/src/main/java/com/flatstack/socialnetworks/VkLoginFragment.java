package com.flatstack.socialnetworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class VkLoginFragment extends Fragment {

    private static final String KEY_FIRST_LAUNCH = "firstLaunch";

    private boolean firstLaunch = true;

    private static final String KEY_LINK      = "link";
    private static final String KEY_IMAGE_URL = "imageUrl";
    private static final String KEY_TEXT      = "text";
    private static final String KEY_CLOSE_SCREEN = "closeScreen";

    private boolean closeScreen;

    public static VkLoginFragment shareAfter(String link, String text, String imageUrl) {
        VkLoginFragment f = new VkLoginFragment();
        Bundle args = new Bundle();
        args.putString(KEY_LINK, link);
        args.putString(KEY_IMAGE_URL, imageUrl);
        args.putString(KEY_TEXT, text);
        f.setArguments(args);
        return f;
    }

    private String link;
    private String title;
    private String imageUrl;

    protected void parseArguments(@NonNull Bundle args) {
        link = args.getString(KEY_LINK);
        title = args.getString(KEY_TEXT);
        imageUrl = args.getString(KEY_IMAGE_URL);
    }

    @Override public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        if (getArguments() != null) {
            parseArguments(getArguments());
        }
        if (savedState != null) {
            firstLaunch = savedState.getBoolean(KEY_FIRST_LAUNCH, true);
            closeScreen = savedState.getBoolean(KEY_CLOSE_SCREEN, false);
        }
        if (firstLaunch) {
            VKSdk.logout();
            // if you want to post on a user wall then you need WALL permission
            // if you want post with image, then you also need PHOTOS permission
            VKSdk.logout();
            VKSdk.login(this, VKScope.WALL, VKScope.PHOTOS);
            firstLaunch = false;
        }
    }

    @Override public void onResume() {
        super.onResume();
        if (closeScreen) {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater,
                                                 @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_social_auth, container, false);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken token) {
                Toast.makeText(getActivity(), "vk token here: " + token.accessToken, Toast.LENGTH_SHORT).show();
                if (link != null) {
                    Shares.vk(link, title, imageUrl, getActivity());
                }
            }

            @Override
            public void onError(VKError error) {
                closeScreen = true;
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_FIRST_LAUNCH, firstLaunch);
        outState.putBoolean(KEY_CLOSE_SCREEN, closeScreen);
    }

}
