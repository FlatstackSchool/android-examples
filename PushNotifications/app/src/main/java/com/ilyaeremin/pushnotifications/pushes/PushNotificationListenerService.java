package com.ilyaeremin.pushnotifications.pushes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.ilyaeremin.pushnotifications.PushActivity;

/**
 * Created by Ilya Eremin on 21.04.2016.
 */
public class PushNotificationListenerService extends GcmListenerService {

    /**
     * called when push notification comes from GCM server
     *
     * @param from
     * @param data here you can find all data from server
     */
    @Override public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String usefulInfo = data.getString("payload");
        Intent action = new Intent(this, PushActivity.class);
        action.putExtra(PushNotificationManager.KEY_PAYLOAD, usefulInfo);
        action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PushNotificationManager.showNotification(this, action, message);
    }

}
