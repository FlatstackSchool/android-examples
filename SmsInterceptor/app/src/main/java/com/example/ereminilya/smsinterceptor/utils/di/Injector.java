package com.example.ereminilya.smsinterceptor.utils.di;

import android.content.Context;

import com.example.ereminilya.smsinterceptor.App;
import com.example.ereminilya.smsinterceptor.SettingsScreen;
import com.example.ereminilya.smsinterceptor.SmsListener;

/**
 * Created by ereminilya on 24/11/16.
 */

public class Injector {

    public static void inject(SettingsScreen settingsScreen) {
        ((App) settingsScreen.getApplicationContext()).getComponent().inject(settingsScreen);
    }

    public static void inject(SmsListener smsListener, Context context) {
        ((App) context.getApplicationContext()).getComponent().inject(smsListener);
    }
}
