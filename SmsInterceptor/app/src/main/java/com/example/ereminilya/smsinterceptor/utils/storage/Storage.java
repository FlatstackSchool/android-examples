package com.example.ereminilya.smsinterceptor.utils.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ereminilya on 24/11/16.
 */
public interface Storage {

    boolean contains(@NonNull String key);

    void putLong(@NonNull String key, long number);

    long getLong(@NonNull String key, long defaultValue);

    void putInt(@NonNull String key, int number);

    int getInt(@NonNull String key, int defaultValue);

    void putBoolean(@NonNull String key, boolean value);

    boolean getBoolean(@NonNull String key, boolean defaultValue);

    void putString(@NonNull final String key, @NonNull String str);

    @Nullable String getString(@NonNull String key);

    void remove(@NonNull String key);

    void clear();
}
