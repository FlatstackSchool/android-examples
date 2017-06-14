package com.flatstack.analytics.util;

import android.content.Context;

import com.flatstack.analytics.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * Created by yaroslavsudnik on 07/06/17.
 */

public class GoogleAnalyticsLogger implements EventLogger {

    private GoogleAnalytics googleAnalytics;
    private Tracker         mTracker;

    public GoogleAnalyticsLogger(Context context) {
        googleAnalytics = GoogleAnalytics.getInstance(context);
        mTracker = getDefaultTracker();
    }

    @Override public void log(String event) {
        mTracker.send(new HitBuilders.EventBuilder()
            .setAction(event)
            .build());
    }

    @Override public void logParam(String eventCategory, Map<String, String> events) {
        for (String event : events.keySet()) {
            mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(eventCategory)
                .set(event, events.get(event))
                .build());
        }
    }

    @Override public void logError(String errorId, String message, Throwable exception) {
        mTracker.send(new HitBuilders.ExceptionBuilder()
            .set(errorId, message)
            .setDescription(exception.toString())
            .setFatal(true)
            .build());
    }

    @Override public void performUserInfo(AnalyticsHelper.User user) {
        mTracker.set("&uid", user.getId());
        mTracker.send(new HitBuilders.EventBuilder()
            .setCategory("Brand")
            .setAction(android.os.Build.BRAND)
            .build());
    }

    @Override public void onStartSession() {
        mTracker.send(new HitBuilders.EventBuilder()
            .setNewSession()
            .build());
    }

    @Override public void onStopSession() {
        mTracker.send(new HitBuilders.EventBuilder()
            .set("&sc", "end")
            .build());
    }

    private synchronized Tracker getDefaultTracker() {
        if (mTracker == null) {
            mTracker = googleAnalytics.newTracker(R.xml.global_tracker);
            mTracker.enableExceptionReporting(true);
            mTracker.enableAdvertisingIdCollection(true);
        }
        return mTracker;
    }

}
