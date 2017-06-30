package com.flatstack.analytics.util;

import android.content.Context;

import com.flatstack.analytics.BuildConfig;
import com.flurry.android.FlurryAgent;

import java.util.Map;

/**
 * Created by yaroslavsudnik on 07/06/17.
 */

public class FlurryAgentLogger implements EventLogger {

    public FlurryAgentLogger(Context context, String apiKey) {
        FlurryAgent.setLogEnabled(true);
        FlurryAgent.setVersionName(BuildConfig.VERSION_NAME);
        FlurryAgent.setReportLocation(true);
        FlurryAgent.init(context, apiKey);
    }

    @Override public void log(String event) {
        FlurryAgent.logEvent(event);
    }

    @Override public void logParam(String eventName, Map<String, String> events) {
        FlurryAgent.logEvent(eventName, events);
    }

    @Override public void logError(String errorId, String message, Throwable exception) {
        FlurryAgent.onError(errorId, message, exception);
    }

    @Override public void performUserInfo(User user) {
        FlurryAgent.setUserId(user.getId());
        FlurryAgent.setAge(user.getAge());
        FlurryAgent.setGender(user.getGender());
    }

    @Override public void onStartSession(Context context) {
        FlurryAgent.onStartSession(context);
    }

    @Override public void onStopSession(Context context) {
        FlurryAgent.onEndSession(context);
    }

}
