package com.example.ereminilya.smsinterceptor.utils.di;

import com.example.ereminilya.smsinterceptor.SettingsScreen;
import com.example.ereminilya.smsinterceptor.SmsListener;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ereminilya on 24/11/16.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(SettingsScreen settingsScreen);

    void inject(SmsListener smsListener);
}
