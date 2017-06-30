package com.example.ereminilya.smsinterceptor.utils;

import android.support.annotation.Nullable;

/**
 * Created by ereminilya on 24/11/16.
 */
public class Strings {

    public static boolean equals(@Nullable String str1, @Nullable String str2) {
        return !(str1 == null || str2 == null) && str1.equals(str2);
    }
}
