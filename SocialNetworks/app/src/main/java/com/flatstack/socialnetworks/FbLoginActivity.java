package com.flatstack.socialnetworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class FbLoginActivity extends AppCompatActivity {

    private LoginButton     loginButton;
    private CallbackManager callbackManager;

    @Override public void onCreate(Bundle savedState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = new LoginButton(this);
        requestAdditionalPermission(loginButton);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();
                Toast.makeText(FbLoginActivity.this, "token here" + token, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancel() {
                finish();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(FbLoginActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        super.onCreate(savedState);
        if (savedState == null) {
            firstFragmentLaunch();
        }
    }

    /**
     * если необходимо дополнительные права на чтение\запись ты знаешь что делать
     * Для поста на стену достаточно стандартного скоупа
     * {@see https://developers.facebook.com/docs/facebook-login/permissions}
     */
    private void requestAdditionalPermission(LoginButton loginButton) {
//        loginButton.setPublishPermissions();
//        loginButton.setReadPermissions();
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
