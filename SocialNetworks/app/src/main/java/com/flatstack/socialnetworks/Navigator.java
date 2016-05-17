package com.flatstack.socialnetworks;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class Navigator {

    public static void vkLogin(@NonNull Context context) {
        startActivity(context, VkLoginActivity.class);
    }

    public static void fbLogin(@NonNull Context context) {
        startActivity(context, FbLoginActivity.class);
    }

    public static void twitterLogin(@NonNull Context context) {
        startActivity(context, TwitterAuthActivity.class);
    }

    public static void vkAuthAndShare(@NonNull String link, @NonNull String title,
                                      @Nullable String imageUrl, @NonNull Context context) {
        VkLoginActivity.shareAfter(link, title, imageUrl, context);
    }

    private static void startActivity(@NonNull Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }
}
