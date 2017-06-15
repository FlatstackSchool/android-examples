package com.flatstack.analytics.util;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yaroslavsudnik on 06/06/17.
 */

public class AnalyticsHelper implements EventLogger {

    private static ArrayList<EventLogger> loggers = new ArrayList<>();

    private User user;

    public AnalyticsHelper(User user) {
        this.user = user;
    }

    public void addLogger(EventLogger eventLogger) {
        loggers.add(eventLogger);
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

}