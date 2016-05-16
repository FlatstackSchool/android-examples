package com.ilyaeremin.pushnotifications.pushes;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by Ilya Eremin on 21.04.2016.
 */
public class PushInteractor {

    private static final String TAG = "PushInteractor";

    private static final String senderId     = "32878566673";
    private static final String serverApiKey = "AIzaSyAB-8Mfadqmg6LafxevK-ki5ZV8CWMrCMs";

    // Название топика может быть любое
    private static final String[] TOPICS   = {"global"};

    public interface ResultListener {
        void onTokenReceived(String token);
    }



    public static void registerDevice(final Context context, final ResultListener listener) {
        final Handler mainHandler = new Handler(context.getMainLooper());
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override public void run() {
                InstanceID instanceID = InstanceID.getInstance(context);
                try {
                    final String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.i(TAG, "GCM Registration Token: " + token);
                    subscribeTopics(token, context);
                    mainHandler.post(new Runnable() {
                        @Override public void run() {
                            listener.onTokenReceived(token);
                        }
                    });
                } catch (IOException ignored) {
                }
            }
        });
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    private static void subscribeTopics(String token, Context context) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(context);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }


}
