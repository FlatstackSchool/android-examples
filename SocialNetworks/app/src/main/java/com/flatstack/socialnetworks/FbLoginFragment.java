package com.flatstack.socialnetworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class FbLoginFragment extends Fragment {

    private LoginButton     loginButton;
    private CallbackManager callbackManager;

    @Override public void onCreate(Bundle savedState) {
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = new LoginButton(getActivity());
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();
                Toast.makeText(getContext(), "token here" + token, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }

            @Override
            public void onError(FacebookException exception) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
                Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        super.onCreate(savedState);
        if (savedState == null) {
            firstFragmentLaunch();
        }
    }

    protected void firstFragmentLaunch() {
        LoginManager.getInstance().logOut();
        loginButton.performClick();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
