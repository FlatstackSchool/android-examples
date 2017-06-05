package com.example.revern.fcmnotifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    TextView uiToken;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //subscribe to topic
        FirebaseMessaging.getInstance().subscribeToTopic("cats");
        FirebaseMessaging.getInstance().subscribeToTopic("c");

        uiToken = (TextView) findViewById(R.id.token);
        uiToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiToken.setText(FirebaseInstanceId.getInstance().getToken());
                Log.d("token", FirebaseInstanceId.getInstance().getToken());
            }
        });
    }
}
