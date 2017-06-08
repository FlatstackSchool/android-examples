package com.flatstack.analytics.util;

import com.flatstack.analytics.User;

/**
 * Created by yaroslavsudnik on 07/06/17.
 */

public interface EventLogger {

    void simpleEvent();

    void paramEvent(String paramKey, String paramValue);

    void errorEvent();

    void performUserInfo(User user);

    void onStart();

    void onStop();

    void onResume(String screenName);

}