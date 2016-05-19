package com.ilyaeremin.activeandroidexample;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * Created by Ilya Eremin on 14.05.2016.
 */
public class Deps {
    private static Deps ourInstance = new Deps();

    public static Deps getInstance() {
        return ourInstance;
    }

    private Gson gson;

    @NonNull public Gson getMapper() {
        if (gson == null) {
            gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL)
                .create();
        }
        return gson;
    }


}
