package com.flatstack.realmexample.util;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Views {

    public static View inflate(@NonNull ViewGroup viewGroup, @LayoutRes int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

}
