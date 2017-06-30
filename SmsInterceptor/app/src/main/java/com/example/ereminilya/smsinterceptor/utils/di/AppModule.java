package com.example.ereminilya.smsinterceptor.utils.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.example.ereminilya.smsinterceptor.Settings;
import com.example.ereminilya.smsinterceptor.utils.storage.SharedPrefsStorage;
import com.example.ereminilya.smsinterceptor.utils.storage.Storage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ereminilya on 24/11/16.
 */
@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton Context providesApplication() {
        return mApplication;
    }


    @Provides @Singleton Settings providesSettings(Storage storage) {
        return new Settings(storage);
    }

    @Provides @Singleton Storage providesStorage(SharedPreferences sharedPreferences) {
        return new SharedPrefsStorage(sharedPreferences);
    }

    @Provides @Singleton public SharedPreferences providesSharedPrefs(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}