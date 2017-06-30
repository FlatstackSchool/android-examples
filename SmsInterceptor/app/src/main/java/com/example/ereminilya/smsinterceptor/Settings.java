package com.example.ereminilya.smsinterceptor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ereminilya.smsinterceptor.utils.storage.Storage;

/**
 * Created by ereminilya on 24/11/16.
 */
public class Settings {

    private static final String KEY_PHONE_TO_INTERCEPT   = "phoneToIntercept";
    private static final String KEY_INTERCEPTION_ENABLED = "interceptionEnabled";

    private final Storage storage;

    public Settings(Storage storage) {
        this.storage = storage;
    }

    public void saveNumber(@NonNull String phone) {
        storage.putString(KEY_PHONE_TO_INTERCEPT, phone);
    }

    public void enableInterception(boolean enabled) {
        storage.putBoolean(KEY_INTERCEPTION_ENABLED, enabled);
    }

    public boolean isInterceptionEnabled() {
        return storage.getBoolean(KEY_INTERCEPTION_ENABLED, true);
    }

    @Nullable public String getPhoneToSpy() {
        return storage.getString(KEY_PHONE_TO_INTERCEPT);
    }
}
