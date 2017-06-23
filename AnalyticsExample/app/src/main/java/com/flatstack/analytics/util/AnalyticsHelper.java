package com.flatstack.analytics.util;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yaroslavsudnik on 06/06/17.
 */

public class AnalyticsHelper implements EventLogger {

    private static ArrayList<EventLogger> loggers = new ArrayList<>();

    private User user;

    public static AnalyticsHelper analyticsHelper;

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

    @Override public void onStartSession() {
        for (EventLogger eventLogger : loggers) {
            eventLogger.performUserInfo(user);
            eventLogger.onStartSession();
        }
    }

    @Override public void onStopSession() {
        for (EventLogger eventLogger : loggers) {
            eventLogger.onStopSession();
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

}