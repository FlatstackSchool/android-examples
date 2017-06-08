package com.flatstack.analytics.util;

import com.flatstack.analytics.User;

import java.util.ArrayList;

/**
 * Created by yaroslavsudnik on 06/06/17.
 */

public class AnalyticsHelper implements EventLogger {

    private static ArrayList<EventLogger> loggers = new ArrayList<>();

    public void addLogger(EventLogger eventLogger) {
        loggers.add(eventLogger);
    }

    @Override public void simpleEvent() {
        for (EventLogger eventLogger : loggers) {
            eventLogger.simpleEvent();
        }
    }

    @Override public void paramEvent(String paramKey, String paramValue) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.paramEvent(paramKey, paramValue);
        }
    }

    @Override public void errorEvent() {
        for (EventLogger eventLogger : loggers) {
            eventLogger.errorEvent();
        }
    }

    @Override public void performUserInfo(User user) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.performUserInfo(user);
        }
    }

    @Override public void onStart() {
        for (EventLogger eventLogger : loggers) {
            eventLogger.onStart();
        }
    }

    @Override public void onStop() {
        for (EventLogger eventLogger : loggers) {
            eventLogger.onStop();
        }
    }

    @Override public void onResume(String screenName) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.onResume(screenName);
        }
    }

}