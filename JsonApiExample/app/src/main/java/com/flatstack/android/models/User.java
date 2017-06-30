package com.flatstack.android.models;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

/**
 * Created by ereminilya on 24/3/17.
 */

@JsonApi(type = "users")
public class User extends Resource {

    private String id;
    private String email;
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}

