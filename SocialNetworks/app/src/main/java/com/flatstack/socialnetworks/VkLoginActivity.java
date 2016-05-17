package com.flatstack.socialnetworks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class VkLoginActivity extends AppCompatActivity {

    private static final String KEY_FIRST_LAUNCH = "firstLaunch";

    private boolean firstLaunch = true;

    private static final String KEY_LINK      = "link";
    private static final String KEY_IMAGE_URL = "imageUrl";
    private static final String KEY_TEXT      = "text";
    private static final String KEY_CLOSE_SCREEN = "closeScreen";

    private boolean closeScreen;

    public static void shareAfter(String link, String text, String imageUrl, Context context) {
        Intent intent = new Intent(context, VkLoginActivity.class);
        intent.putExtra(KEY_LINK, link);
        intent.putExtra(KEY_IMAGE_URL, imageUrl);
        intent.putExtra(KEY_TEXT, text);
        context.startActivity(intent);
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
        if (getIntent() != null && getIntent().getExtras() != null) {
            parseArguments(getIntent().getExtras());
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
            VKSdk.login(this, getPermissions());
            firstLaunch = false;
        }
    }

    /**
     * Список необходимых для публикации с фото на стене пользователя
     * Список всех прав можно увидеть в классе VKScope
     */
    @NonNull private String[] getPermissions() {
        return new String[]{VKScope.WALL, VKScope.PHOTOS};
    }

    @Override public void onResume() {
        super.onResume();
        if (closeScreen) {
            finish();
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken token) {
                Toast.makeText(VkLoginActivity.this, "vk token here: " + token.accessToken, Toast.LENGTH_SHORT).show();
                if (link != null) {
                    Shares.vk(link, title, imageUrl, VkLoginActivity.this);
                }
                finish();
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
