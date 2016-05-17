package com.flatstack.socialnetworks.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flatstack.socialnetworks.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, new MainScreen())
            .commit();
    }

}
