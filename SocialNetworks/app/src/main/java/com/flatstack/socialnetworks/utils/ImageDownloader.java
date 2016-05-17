package com.flatstack.socialnetworks.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

/**
 * Created by Ilya Eremin on 20.04.2016.
 */
public class ImageDownloader {

    @WorkerThread @Nullable public static Bitmap download(@NonNull String url, Context context) {
        try {
            return Glide.
                with(context).
                load(url).
                asBitmap().
                into(-1, -1). // Width and height
                get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
