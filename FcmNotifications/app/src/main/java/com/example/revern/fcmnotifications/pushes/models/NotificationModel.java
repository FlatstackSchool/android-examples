package com.example.revern.fcmnotifications.pushes.models;

import com.example.revern.fcmnotifications.utils.StringUntils;

/**
 * Created by Revern on 22.03.2017.
 */

public class NotificationModel {
    private String name;
    private int age;
    private String text;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getText() {
        return text;
    }

    public boolean isEmpty() {
        return StringUntils.isEmpty(name) || StringUntils.isEmpty(text);
    }

}
