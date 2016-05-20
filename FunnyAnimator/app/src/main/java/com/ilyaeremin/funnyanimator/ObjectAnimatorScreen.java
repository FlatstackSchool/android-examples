package com.ilyaeremin.funnyanimator;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Ilya Eremin on 23.04.2016.
 */
public class ObjectAnimatorScreen extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_rotate_and_jump);
        final View target = findViewById(R.id.target);
        final float initialSize = getResources().getDisplayMetrics().density * 64;
        target.setPivotX(initialSize/2);
        target.setPivotY(initialSize/2);
        findViewById(R.id.run).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ObjectAnimator.ofFloat(target, "rotation", 0, 360, 0).setDuration(2000).start();
            }
        });
    }
}
