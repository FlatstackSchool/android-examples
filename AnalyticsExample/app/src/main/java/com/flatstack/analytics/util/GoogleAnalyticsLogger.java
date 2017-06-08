package com.flatstack.analytics.util;

import android.content.Context;

import com.flatstack.analytics.R;
import com.flatstack.analytics.User;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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

    @Override public void simpleEvent() {
        mTracker.send(new HitBuilders.EventBuilder()
            .setCategory("Category")
            .setAction("simple_event")
            .build());
    }

    @Override public void paramEvent(String paramCategory, String paramAction) {
        mTracker.send(new HitBuilders.EventBuilder()
            .setCategory(paramCategory)
            .setAction(paramAction)
            .build());
    }

    @Override public void errorEvent() {
        mTracker.send(new HitBuilders.ExceptionBuilder()
            .setDescription("nullPointerException")
            .setFatal(true)
            .build());
    }

    @Override public void performUserInfo(User user) {
        mTracker.send(new HitBuilders.EventBuilder()
            .setCategory("Brand")
            .setAction(android.os.Build.BRAND)
            .build());
    }

    @Override public void onStart() {
    }

    @Override public void onStop() {
    }

    @Override public void onResume(String screenName) {
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
