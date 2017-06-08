package com.flatstack.analytics.util;

import android.content.Context;

import com.flatstack.analytics.BuildConfig;
import com.flatstack.analytics.User;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaroslavsudnik on 07/06/17.
 */

public class FlurryAgentLogger implements EventLogger {

    private static final String FLURRY_APIKEY = "W34TS7S5WM6PY9BR6RH9";

    private Context context;

    public FlurryAgentLogger(Context context) {
        this.context = context;

        FlurryAgent.setLogEnabled(true);
        FlurryAgent.setVersionName(BuildConfig.VERSION_NAME);
        FlurryAgent.setReportLocation(true);
        FlurryAgent.init(context, FLURRY_APIKEY);
    }

    @Override public void simpleEvent() {
        FlurryAgent.logEvent("simple_event");
    }

    @Override public void paramEvent(String paramKey, String paramValue) {
        Map<String, String> params = new HashMap<>();
        params.put(paramKey, paramValue);

        FlurryAgent.logEvent("param_event", params);
    }

    @Override public void errorEvent() {
        FlurryAgent.onError("error_event", "this null error", new NullPointerException());
    }

    @Override public void performUserInfo(User user) {
        FlurryAgent.setUserId(user.getId());
        FlurryAgent.setAge(user.getAge());
        FlurryAgent.setGender(user.getGender());
    }

    @Override public void onStart() {
        FlurryAgent.onStartSession(context);
    }

    @Override public void onStop() {
        FlurryAgent.onEndSession(context);
    }

    @Override public void onResume(String screenName) {
    }

}
