package com.flatstack.analytics.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yaroslavsudnik on 06/06/17.
 */

public class AnalyticsHelper implements EventLogger {

    private static ArrayList<EventLogger> loggers = new ArrayList<>();

    private User user;

    private static AnalyticsHelper analyticsHelper;

    public static void setup() {
        analyticsHelper = new AnalyticsHelper();
    }

    public void addLogger(EventLogger eventLogger) {
        loggers.add(eventLogger);
    }

    public static AnalyticsHelper get() {
        return analyticsHelper;
    }

    @Override public void log(String event) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.log(event);
        }
    }

    @Override public void logParam(String eventName, Map<String, String> events) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.logParam(eventName, events);
        }
    }

    @Override public void logError(String errorId, String message, Throwable exception) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.logError(errorId, message, exception);
        }
    }

    @Override public void performUserInfo(User user) {
        if (user != null) {
            for (EventLogger eventLogger : loggers) {
                eventLogger.performUserInfo(user);
            }
        }
    }

    @Override public void onStartSession(Context context) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.performUserInfo(user);
            eventLogger.onStartSession(context);
        }
    }

    @Override public void onStopSession(Context context) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.onStopSession(context);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

}