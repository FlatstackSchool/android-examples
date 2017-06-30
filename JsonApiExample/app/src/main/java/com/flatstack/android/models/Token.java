package com.flatstack.android.models;

import com.squareup.moshi.Json;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

/**
 * Created by ereminilya on 24/3/17.
 */
@JsonApi(type = "sessions")
public class Token extends Resource {

    private @Json(name = "authentication-token")
    String authenticationToken;
    private String email;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public String getEmail() {
        return email;
    }
}