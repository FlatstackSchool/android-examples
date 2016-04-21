package com.ilyaeremin.pushnotifications;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ilyaeremin.pushnotifications.pushes.PushInteractor;
import com.ilyaeremin.pushnotifications.pushes.PushNotificationManager;

public class MainActivity extends AppCompatActivity {

    private TextView pushToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pushToken = (TextView) findViewById(R.id.push_token);
        findViewById(R.id.register_token).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PushInteractor.registerDevice(MainActivity.this, new PushInteractor.ResultListener() {
                    @Override public void onTokenReceived(String token) {
                        pushToken.setText(token);
                    }
                });
            }
        });
        findViewById(R.id.fake_push).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PushNotificationManager.showNotification(MainActivity.this,
                    PushNotificationManager.pushExample(MainActivity.this, "this will be shown in push activity"), "Fake push");
            }
        });

    }


}
