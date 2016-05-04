package com.flatstack.socialnetworks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.flatstack.socialnetworks.ui.MainActivity;
import com.flatstack.socialnetworks.ui.MainScreen;

import java.lang.ref.WeakReference;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class Navigator {
    private static WeakReference<MainActivity> mainActivity;

    public static void setUpActivity(MainActivity activity) {
        mainActivity = new WeakReference<>(activity);
    }

    public static void mainScreen() {
        replaceScreen(new MainScreen());
    }

    public static void vkLogin(){
        replaceScreen(new VkLoginFragment());
    }

    private static void replaceScreen(Fragment fragment) {
        if (mainActivity != null) {
            MainActivity mainActivity = Navigator.mainActivity.get();
            if (mainActivity != null) {
                mainActivity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, fragment)
                    .commit();
            }
        }
    }

    public static void fbLogin() {
        replaceScreen(new FbLoginFragment());
    }

    public static void twitterLogin() {
        if (mainActivity != null && mainActivity.get() != null) {
            mainActivity.get().startActivity(new Intent(mainActivity.get(), TwitterAuthActivity.class));
        }
    }

    public static void vkAuthAndShare(@NonNull String link, @NonNull String title, @Nullable String imageUrl) {
        replaceScreen(VkLoginFragment.shareAfter(link, title, imageUrl));
    }
}
