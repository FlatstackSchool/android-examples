package com.ilyaeremin.pushnotifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ilyaeremin.pushnotifications.pushes.PushNotificationManager;

/**
 * Created by Ilya Eremin on 21.04.2016.
 */
public class PushActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_push);
        handlePush(getIntent());
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handlePush(intent);
    }

    @SuppressWarnings("ConstantConditions") private void handlePush(Intent intent) {
        String payload = intent.getStringExtra(PushNotificationManager.KEY_PAYLOAD);
        ((TextView) findViewById(R.id.payload)).setText(payload);
    }

}
