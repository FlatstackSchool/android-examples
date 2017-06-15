package com.flatstack.analytics.util;

/**
 * Created by yaroslavsudnik on 15/06/17.
 */

public class User {

    private String id;
    private String email;
    private String name;
    private int    age;
    private byte   gender;

    public User(String id, String email, String name, int age, byte gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public byte getGender() {
        return gender;
    }

}