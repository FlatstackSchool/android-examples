package com.flatstack.socialnetworks.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatstack.socialnetworks.Navigator;
import com.flatstack.socialnetworks.R;
import com.flatstack.socialnetworks.Shares;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class MainScreen extends Fragment {

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.screen_main, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.vk).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Navigator.vkLogin(getActivity());
            }
        });
        view.findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Navigator.fbLogin(getActivity());
            }
        });
        view.findViewById(R.id.twitter).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Navigator.twitterLogin(getActivity());
            }
        });
        view.findViewById(R.id.share_via_fb).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Shares.facebook("https://vk.com/ilyaeremin", "Your money my code", "http://www.alternet.org/files/images/managed/media_snoop.jpg", getActivity());
            }
        });
        view.findViewById(R.id.share_via_twitter).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Shares.twitter("https://vk.com/ilyaeremin", "Hello from Ilya", null, getContext());
            }
        });
        view.findViewById(R.id.share_via_vk).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Shares.vk("https://vk.com/ilyaeremin", "Your money my code", "http://www.alternet.org/files/images/managed/media_snoop.jpg", getActivity());
            }
        });
    }
}
