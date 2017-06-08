package com.flatstack.realmexample;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements Serializable {

    private String name;
    private String surname;
    @PrimaryKey
    private String phone;
    private int groupId;

    public User() {
    }

    public User(String name, String surname, String phone, int groupId) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

}
