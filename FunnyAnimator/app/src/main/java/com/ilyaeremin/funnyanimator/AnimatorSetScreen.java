package com.ilyaeremin.funnyanimator;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ilya Eremin on 17.05.2016.
 */
public class AnimatorSetScreen extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_animator_set);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play().
    }
}
