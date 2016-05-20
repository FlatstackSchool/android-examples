package com.ilyaeremin.pushnotifications;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.ilyaeremin.pushnotifications.pushes.PushInteractor;
import com.ilyaeremin.pushnotifications.pushes.PushNotificationManager;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1234;
    private TextView pushToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pushToken = (TextView) findViewById(R.id.push_token);
        TextView registerPushTokenBtn = (TextView) findViewById(R.id.register_token);

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                registerPushTokenBtn.setText("You need to install google services");
            } else {
                registerPushTokenBtn.setText("You have no google play services installed.\nPush notifications does not work without them.");
            }
        } else {
            registerPushTokenBtn.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    PushInteractor.registerDevice(MainActivity.this, new PushInteractor.ResultListener() {
                        @Override public void onTokenReceived(String token) {
                            pushToken.setText(token);
                        }
                    });
                }
            });
        }

        findViewById(R.id.fake_push).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PushNotificationManager.showNotification(MainActivity.this,
                    PushNotificationManager.pushExample(MainActivity.this, "this will be shown in push activity"), "Fake push");
            }
        });

    }


}
