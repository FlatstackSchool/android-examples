package com.ilyaeremin.pushnotifications.pushes;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import com.ilyaeremin.pushnotifications.PushActivity;
import com.ilyaeremin.pushnotifications.R;

import java.util.Random;

/**
 * Created by Ilya Eremin on 21.04.2016.
 */
public class PushNotificationManager {

    public static final String KEY_PAYLOAD    = "payload";

    public static void showNotification(Context context, Intent actionIntent, String text) {

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
            context,
            new Random().nextInt(9998) + 1,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification notification = createNotification(context, text, resultPendingIntent);
        // if you set constant number then 2...n pushes will make same action as first because of pending intent caching system
        NotificationManagerCompat.from(context).notify(new Random().nextInt(), notification);
    }

    /**
     * note that your push notificaiton icon should be entirely white starting from 5.0
     */
    @NonNull
    private static Notification createNotification(Context context, String text, PendingIntent resultPendingIntent) {
//        RemoteViews pushView = createCustomView(context, text);
        Notification notification = new NotificationCompat.Builder(context)
            .setContentTitle(context.getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentText(text)
            .setContentIntent(resultPendingIntent)
            .setLights(Color.BLUE, 500, 3000)
            .setAutoCancel(true)
            .build();

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;
        return notification;
    }

    @NonNull private static RemoteViews createCustomView(Context context, String text) {
        RemoteViews pushView = new RemoteViews(context.getPackageName(), R.layout.layout_push);
        pushView.setTextViewText(R.id.article_text, text);
        pushView.setTextViewText(R.id.app_name, context.getString(R.string.app_name));
        return pushView;
    }

    public static Intent pushExample(Context context, String payload) {
        Intent action = new Intent(context, PushActivity.class);
        action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        action.putExtra(PushNotificationManager.KEY_PAYLOAD, payload);
        return action;
    }

}
