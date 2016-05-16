package com.ilyaeremin.activeandroidexample.serializer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilyaeremin.activeandroidexample.Deps;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Eremin on 16.05.2016.
 */
public class ListSerializer extends TypeSerializer {
    public static final String TYPE_STRING = "String";
    private             Gson   mapper      = Deps.getInstance().getMapper();

    /**
     * @return класс, который надо серелиазовать\десереализовать
     */
    @Override @NonNull public Class<?> getDeserializedType() {
        return List.class;
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
        List list = (List) data;
        String objectName;
        if (!list.isEmpty()) {
            Object o = list.get(0);
            if (o instanceof String) {
                objectName = TYPE_STRING;
            } else {
                // or you can
                // throw new IllegalStateException("unknown format");
                return null;
            }
        } else {
            return null;
        }
        return objectName + "\n" + mapper.toJson(data, List.class);
    }

    @Override @Nullable public List deserialize(@Nullable Object data) {
        if (data == null) {
            return null;
        }
        String parsedString = (String) data;
        String type = parsedString.substring(0, parsedString.indexOf("\n"));
        String json = parsedString.substring(parsedString.indexOf("\n") + 1);
        Type listType;
        if (type.equals(TYPE_STRING)) {
            listType = new TypeToken<ArrayList<String>>() {}.getType();
        } else {
//            throw new IllegalStateException("unknown list type");
            return null;
        }
        return mapper.fromJson(json, listType);
    }

}
