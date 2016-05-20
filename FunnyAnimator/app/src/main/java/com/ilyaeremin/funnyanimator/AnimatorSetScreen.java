package com.ilyaeremin.funnyanimator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Ilya Eremin on 17.05.2016.
 */
public class AnimatorSetScreen extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_animator_set);
        final View viewForAnimation = findViewById(R.id.target);
        final View rootView = findViewById(R.id.root);

        viewForAnimation.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator first = ObjectAnimator.ofFloat(v, "translationX", rootView.getWidth() - v.getWidth()); // you can just pass 100 or 200 or any number
                ObjectAnimator second = ObjectAnimator.ofFloat(v, "translationY", rootView.getHeight() - v.getHeight());
                ObjectAnimator third = ObjectAnimator.ofFloat(v, "translationX", 0);
                ObjectAnimator forth = ObjectAnimator.ofFloat(v, "translationX", rootView.getWidth() - v.getWidth());
                ObjectAnimator fifth = ObjectAnimator.ofFloat(v, "translationX", 0);
                ObjectAnimator sixth = ObjectAnimator.ofFloat(v, "translationY", 0);

                animatorSet.play(first).before(second);
                animatorSet.play(second).with(third);
                animatorSet.play(forth).after(third);
                animatorSet.play(fifth).with(sixth).after(forth);
                animatorSet.setDuration(500);
                animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
                animatorSet.start();
            }
        });
    }
}
