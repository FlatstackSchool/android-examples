package com.ilyaeremin.funnyanimator;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Ilya Eremin on 17.05.2016.
 */
public class AnimatorInflaterScreen extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_animation_inflater);

        final ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.object_animator_example);
        findViewById(R.id.inflater_object_animator).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                animator.setTarget(v);
                animator.start();
            }
        });

        final AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_set_example);

        findViewById(R.id.inflater_animator_set).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                animatorSet.setTarget(v);
                animatorSet.start();
            }
        });

        final ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.value_animator_example);

        findViewById(R.id.value_animator).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(final View v) {
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override public void onAnimationUpdate(ValueAnimator animation) {
                        v.setTranslationX(((int) animation.getAnimatedValue()));
                    }
                });
                valueAnimator.start();
            }
        });
    }


}
