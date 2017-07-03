package com.flatstack.analytics;

import com.flatstack.analytics.util.AnalyticsHelper;
import com.flatstack.analytics.util.CrashlyticsLogger;
import com.flatstack.analytics.util.FlurryAgentLogger;
import com.flatstack.analytics.util.GoogleAnalyticsLogger;
import com.flatstack.analytics.util.User;
import com.flurry.android.Constants;

/**
 * Created by yaroslavsudnik on 17/05/17.
 */

public class App extends android.app.Application {

    private static final String FLURRY_APIKEY = "W34TS7S5WM6PY9BR6RH9";

    @Override public void onCreate() {
        super.onCreate();

        initAnalytics();
    }

    private void initAnalytics() {
        AnalyticsHelper.setup();

        AnalyticsHelper.get().setUser(new User("12345",
            "yaroslav.sudnik@flatstack.com", "Yaroslav Sudnik", 18, Constants.MALE));

        AnalyticsHelper.get().addLogger(new FlurryAgentLogger(this, FLURRY_APIKEY));
        AnalyticsHelper.get().addLogger(new GoogleAnalyticsLogger(this));
        AnalyticsHelper.get().addLogger(new CrashlyticsLogger(this));
    }

}
