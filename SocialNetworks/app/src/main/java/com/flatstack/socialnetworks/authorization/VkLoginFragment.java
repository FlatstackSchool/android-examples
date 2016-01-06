package com.flatstack.socialnetworks.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class VkLoginFragment extends Fragment {

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            firstFragmentLaunch();
        }
    }

    private void firstFragmentLaunch() {
        VKSdk.logout();
        VKSdk.login(this);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken token) {
                String tokenStr = token.accessToken;
                Toast.makeText(getContext(), "token here: " + tokenStr, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VKError error) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
