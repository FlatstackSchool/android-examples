package fs.com.permissionexample;

import android.app.Application;

import com.karumi.dexter.Dexter;

/**
 * Created by Ramil on 10/02/16.
 */
public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        Dexter.initialize(getApplicationContext());
    }
}
