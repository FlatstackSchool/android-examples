package com.flatstack.android.models;

import com.squareup.moshi.Json;

/**
 * Created by ereminilya on 29/3/17.
 */
public enum Status {

    @Json(name = "created")CREATED, @Json(name = "completed")COMPLETED
}