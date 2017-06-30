package com.example.ereminilya.smsinterceptor;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.ereminilya.smsinterceptor.utils.di.AppComponent;
import com.example.ereminilya.smsinterceptor.utils.di.AppModule;
import com.example.ereminilya.smsinterceptor.utils.di.DaggerAppComponent;
import com.karumi.dexter.Dexter;

/**
 * Created by ereminilya on 24/11/16.
 */
public class App extends Application {

    private AppComponent component;

    @Override public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    @NonNull public AppComponent getComponent() {
        return component;
    }
}
