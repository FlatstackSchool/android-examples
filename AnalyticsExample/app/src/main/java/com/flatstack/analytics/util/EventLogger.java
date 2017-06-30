package com.flatstack.analytics.util;

import android.content.Context;

import java.util.Map;

/**
 * Created by yaroslavsudnik on 07/06/17.
 */

public interface EventLogger {

    void log(String event);

    void logParam(String eventName, Map<String, String> events);

    void logError(String errorId, String message, Throwable exception);

    void performUserInfo(User user);

    void onStartSession(Context context);

    void onStopSession(Context context);

}