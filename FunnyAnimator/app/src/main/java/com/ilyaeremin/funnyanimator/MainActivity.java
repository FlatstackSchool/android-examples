package com.ilyaeremin.funnyanimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.value_animator).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ValueAnimatorScreen.class));
            }
        });
        findViewById(R.id.object_animator).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ObjectAnimatorScreen.class));
            }
        });
        findViewById(R.id.view_property_animator).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewPropertyAnimatorScreen.class));
            }
        });
        findViewById(R.id.animator_inflater).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimatorInflaterScreen.class));
            }
        });
        findViewById(R.id.inflater_animator_set).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimatorSetScreen.class));
            }
        });
    }
}
