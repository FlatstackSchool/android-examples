package com.ilyaeremin.activeandroidexample.serializer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;
import com.ilyaeremin.activeandroidexample.Deps;

/**
 * Created by Ilya Eremin on 16.05.2016.
 */
public class StringArraySerializer extends TypeSerializer {
    private Gson mapper = Deps.getInstance().getMapper();

    /**
     * @return класс, который надо серелиазовать\десереализовать
     */
    @Override @NonNull public Class<?> getDeserializedType() {
        return String[].class;
    }

    /**
     * @return формат в который будем сериазовать. Любой примитив или String
     */
    @Override @NonNull public Class<?> getSerializedType() {
        return String.class;
    }

    @Override @Nullable public String serialize(@Nullable Object data) {
        if (data == null) {
            return null;
        }
        return mapper.toJson(data);
    }

    @Override @Nullable public String[] deserialize(@Nullable Object data) {
        if (data == null) {
            return null;
        }
        return mapper.fromJson(((String) data), String[].class);
    }
}
