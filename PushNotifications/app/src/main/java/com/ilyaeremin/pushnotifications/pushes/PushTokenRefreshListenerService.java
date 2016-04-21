package com.ilyaeremin.pushnotifications.pushes;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Ilya Eremin on 3/3/16.
 */
public class PushTokenRefreshListenerService extends InstanceIDListenerService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        PushInteractor.registerDevice(this, new PushInteractor.ResultListener() {
            @Override public void onTokenReceived(String token) {
                // do what you need with new token
            }
        });
    }
}
