package com.spitchenko.appsgeyser.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.userinterface.BaseActivity;

import java.util.Map;
import java.util.Set;

/**
 * Date: 19.06.17
 * Time: 11:21
 *
 * @author anatoliy
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final static long VIBRATE = 1000;
    private final static int NOTIFICATION_ID = 100500;
    private final static String TAG = "Firebase : ";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            final Set<Map.Entry<String, String>> entries = remoteMessage.getData().entrySet();
            for (final Map.Entry<String, String> next : entries) {
                Log.d(TAG, next.getKey());
                Log.d(TAG, next.getValue());
            }

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            final String body = remoteMessage.getNotification().getBody();

            Log.d(TAG, "Message Notification Body: " + body);

            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat_ic_notification)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(body)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))
                    .setVibrate(new long[] { VIBRATE, VIBRATE, VIBRATE, VIBRATE, VIBRATE })
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true);

            final Intent resultIntent = new Intent(this, BaseActivity.class);
            //resultIntent.setAction(NOTIFICATION);
            final PendingIntent resultPendingIntent = PendingIntent.getService(this, 0, resultIntent, 0);
            builder.setContentIntent(resultPendingIntent);
            final NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
