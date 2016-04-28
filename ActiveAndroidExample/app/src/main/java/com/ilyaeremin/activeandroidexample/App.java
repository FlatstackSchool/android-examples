package com.ilyaeremin.activeandroidexample;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.ilyaeremin.activeandroidexample.models.Article;
import com.ilyaeremin.activeandroidexample.serializer.SharingLinksSerializer;

/**
 * Created by Ilya Eremin on 27.04.2016.
 */
public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        initializeDb();
    }

    @SuppressWarnings("unchecked") private void initializeDb() {
        Configuration.Builder configBuilder = new Configuration.Builder(this);
        configBuilder.addModelClasses(Article.class);
        configBuilder.addTypeSerializers(SharingLinksSerializer.class);
        configBuilder.setDatabaseName("example.db");
        configBuilder.setDatabaseVersion(1);
        ActiveAndroid.initialize(configBuilder.create());
    }
}
