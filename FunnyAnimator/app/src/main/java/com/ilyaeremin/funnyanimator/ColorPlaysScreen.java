package com.ilyaeremin.funnyanimator;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Ilya Eremin on 23.04.2016.
 */
public class ColorPlaysScreen extends AppCompatActivity {

    private int targetColor = Color.BLACK;
    private View target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_colors_play);
        target = findViewById(R.id.target);
        findViewById(R.id.green).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeColor(Color.GREEN);
            }
        });
        findViewById(R.id.violet).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeColor(Color.parseColor("#7F00FF"));
            }
        });
    }

    private void changeColor(final int finishColor) {
        if(finishColor == targetColor) return;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), targetColor, finishColor);
        colorAnimation.setDuration(1000); // milliseconds
        colorAnimation.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {}

            @Override public void onAnimationEnd(Animator animation) {
                targetColor = finishColor;
            }

            @Override public void onAnimationCancel(Animator animation) {}

            @Override public void onAnimationRepeat(Animator animation) {}
        });
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                target.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}
