package com.ilyaeremin.funnyanimator;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Ilya Eremin on 09.05.2016.
 */
public class ViewPropertyAnimatorScreen extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.screen_view_property_animator);
        findViewById(R.id.rotate).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                findViewById(R.id.flip_flop).animate().setDuration(300).rotationBy(360).start();
            }
        });

        findViewById(R.id.shake).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                shake(findViewById(R.id.flip_flop), 10);
            }
        });
    }

    private void shake(final View view, final int count) {
        if(count == 0) return;
        if (count % 2 == 0) {
            view.animate().xBy(10).setDuration(30).setListener(new Animator.AnimatorListener() {
                @Override public void onAnimationStart(Animator animation) {

                }

                @Override public void onAnimationEnd(Animator animation) {
                    shake(view, count - 1);
                }

                @Override public void onAnimationCancel(Animator animation) {

                }

                @Override public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        } else {
            view.animate().xBy(-10).setDuration(30).setListener(new Animator.AnimatorListener() {
                @Override public void onAnimationStart(Animator animation) {

                }

                @Override public void onAnimationEnd(Animator animation) {
                    shake(view, count - 1);
                }

                @Override public void onAnimationCancel(Animator animation) {

                }

                @Override public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }
}
