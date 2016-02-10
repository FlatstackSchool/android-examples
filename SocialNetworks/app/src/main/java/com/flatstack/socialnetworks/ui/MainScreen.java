package com.flatstack.socialnetworks.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatstack.socialnetworks.Navigator;
import com.flatstack.socialnetworks.R;
import com.flatstack.socialnetworks.authorization.Shares;

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
        view.findViewById(R.id.share_via_fb).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Bitmap imageToShare = BitmapFactory.decodeResource(getResources(), R.drawable.ic_facebook);
                Shares.facebookLocalPhoto(getActivity(), imageToShare);
            }
        });
        view.findViewById(R.id.share_via_twitter).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
//                Shares.twitterPicture(getContext(), new File("path/to/your/picture"));
                Shares.twitter("https://vk.com/ilyaeremin", getContext());
            }
        });
        view.findViewById(R.id.share_via_vk).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Bitmap imageToShare = BitmapFactory.decodeResource(getResources(), R.drawable.ic_vk);
                Shares.vkImage(getContext(), imageToShare);
            }
        });
    }
}
