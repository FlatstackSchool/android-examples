package com.flatstack.realmexample;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yaroslavsudnik on 24/05/17.
 */

public class Group extends RealmObject implements Serializable {

    @PrimaryKey
    private int             id;
    private String          name;
    private RealmList<User> users;

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public RealmList<User> getUsers() {
        return users;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(RealmList<User> users) {
        this.users = users;
    }

}
