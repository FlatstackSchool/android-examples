package com.flatstack.realmexample;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
            .schemaVersion(4)
            .migration(migration)
            .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());
    }

    RealmMigration migration = (realm, oldVersion, newVersion) -> {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            schema.create("User")
                .addField("name", String.class)
                .addField("phone", String.class, FieldAttribute.PRIMARY_KEY);
            oldVersion++;
        }
        if (oldVersion == 1) {
            schema.get("User")
                .addField("surname", String.class);
            oldVersion++;
        }
        if (oldVersion == 2) {
            schema.create("Group")
                .addField("name", String.class)
                .addRealmListField("users", schema.get("User"));
            oldVersion++;
        }
        if (oldVersion == 3) {
            schema.get("User")
                .addField("groupId", int.class);
            schema.get("Group")
                .addField("id", int.class, FieldAttribute.PRIMARY_KEY);
            oldVersion++;
        }
    };

}
