package com.flatstack.analytics;

import com.flatstack.analytics.util.AnalyticsHelper;
import com.flatstack.analytics.util.CrashlyticsLogger;
import com.flatstack.analytics.util.FlurryAgentLogger;
import com.flatstack.analytics.util.GoogleAnalyticsLogger;

/**
 * Created by yaroslavsudnik on 17/05/17.
 */

public class App extends android.app.Application {

    public static AnalyticsHelper analyticsHelper = new AnalyticsHelper();

    @Override public void onCreate() {
        super.onCreate();

        analyticsHelper.addLogger(new FlurryAgentLogger(this));
        analyticsHelper.addLogger(new GoogleAnalyticsLogger(this));
        analyticsHelper.addLogger(new CrashlyticsLogger(this));
    }

    public static AnalyticsHelper getAnalyticsHelper() {
        return analyticsHelper;
    }

}
