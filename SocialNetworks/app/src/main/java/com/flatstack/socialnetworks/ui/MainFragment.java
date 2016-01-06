package com.flatstack.socialnetworks.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatstack.socialnetworks.R;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class MainFragment extends Fragment {

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.screen_main, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.vk).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Navigator.vkLogin();
            }
        });
        view.findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Navigator.fbLogin();
            }
        });
        view.findViewById(R.id.twitter).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Navigator.twitterLogin();
            }
        });
    }
}
