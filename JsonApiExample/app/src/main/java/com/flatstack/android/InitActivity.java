package com.flatstack.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.flatstack.android.utils.storage.IStorage;

/**
 * Created by ereminilya on 29/3/17.
 */

public class InitActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = Deps.storage().getString(IStorage.KEY_TOKEN);
        finish();
        if (token != null) {
            Navigator.toDos(this);
        } else {
            Navigator.signIn(this);
        }
    }
}
