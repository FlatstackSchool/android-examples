package com.ilyaeremin.activeandroidexample.serializer;

import com.activeandroid.serializer.TypeSerializer;
import com.ilyaeremin.activeandroidexample.models.SharingLinks;

/**
 * Created by Ilya Eremin on 27.04.2016.
 */
public class SharingLinksSerializer extends TypeSerializer {
    /**
     * @return класс, который надо серелиазовать\десереализовать
     */
    @Override public Class<?> getDeserializedType() {
        return SharingLinks.class;
    }

    /**
     * @return формат в который будем сериазовать. Любой примитив или String
     */
    @Override public Class<?> getSerializedType() {
        return String.class;
    }

    @Override public String serialize(Object data) {
        if (data == null) {
            return null;
        }
        return toString((SharingLinks) data);
    }

    private String toString(SharingLinks data) {
        return data.getTwitter() + "\n" +
            data.getVk() + "\n" +
            data.getFacebook();
    }

    @Override public SharingLinks deserialize(Object data) {
        if (data == null) {
            return null;
        }
        String serializedStr = (String) data;
        String[] split = serializedStr.split("\n");
        return new SharingLinks(split[0], split[1], split[2]);
    }
}
