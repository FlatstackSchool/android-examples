package com.flatstack.analytics.util;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.flatstack.analytics.User;

import io.fabric.sdk.android.Fabric;

/**
 * Created by yaroslavsudnik on 07/06/17.
 */

public class CrashlyticsLogger implements EventLogger {

    public CrashlyticsLogger(Context context) {
        Fabric.with(context, new Crashlytics());
    }

    @Override public void simpleEvent() {
        Answers.getInstance().logContentView(new ContentViewEvent());
    }

    @Override public void paramEvent(String paramKey, String paramValue) {
        Answers.getInstance().logCustom(new CustomEvent("Custom event")
            .putCustomAttribute(paramKey, paramValue));
    }

    @Override public void errorEvent() {
        throw new RuntimeException("This is a crash");
    }

    @Override public void performUserInfo(User user) {
        Crashlytics.setUserIdentifier(user.getId());
        Crashlytics.setUserEmail(user.getEmail());
        Crashlytics.setUserName(user.getName());
    }

    @Override public void onStart() {
    }

    @Override public void onStop() {
    }

    @Override public void onResume(String screenName) {
    }

}
