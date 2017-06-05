package com.example.revern.ormlite;

import android.app.Application;

import com.example.revern.ormlite.database.DbHelperFactory;
import com.facebook.stetho.Stetho;

/**
 * Created by Revern on 31.03.2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        DbHelperFactory.setHelper(this);
    }

    @Override
    public void onTerminate() {
        DbHelperFactory.releaseHelper();
        super.onTerminate();
    }
}
