package com.flatstack.analytics;

import com.flatstack.analytics.util.AnalyticsHelper;
import com.flatstack.analytics.util.CrashlyticsLogger;
import com.flatstack.analytics.util.FlurryAgentLogger;
import com.flatstack.analytics.util.GoogleAnalyticsLogger;
import com.flurry.android.Constants;

/**
 * Created by yaroslavsudnik on 17/05/17.
 */

public class App extends android.app.Application {

    private static final String FLURRY_APIKEY = "W34TS7S5WM6PY9BR6RH9";

    public static AnalyticsHelper analyticsHelper;

    @Override public void onCreate() {
        super.onCreate();

        analyticsHelper = new AnalyticsHelper(new AnalyticsHelper.User("12345",
            "yaroslav.sudnik@flatstack.com", "Yaroslav Sudnik", 18, Constants.MALE));

        analyticsHelper.addLogger(new FlurryAgentLogger(this, FLURRY_APIKEY));
        analyticsHelper.addLogger(new GoogleAnalyticsLogger(this));
        analyticsHelper.addLogger(new CrashlyticsLogger(this));
    }

    public static AnalyticsHelper getAnalyticsHelper() {
        return analyticsHelper;
    }

}
