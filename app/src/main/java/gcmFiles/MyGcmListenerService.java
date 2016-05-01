package gcmFiles;

/**
 * Created by SAYED on 4/23/2016.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.smalam.pseudozero.trackme.R;
import com.smalam.pseudozero.trackme.RequestNotificationActivity;
import com.smalam.pseudozero.trackme.WatchersMapsActivity;

import config.Config;

/**
 * Created by jahid on 16/02/16.
 */
public class MyGcmListenerService extends GcmListenerService
{
    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = "";
        String type = "";
        //Log.d("data: ", data.getString("abc"));
        for (String not_type : Config.notificationType) {
            if (data.get(not_type) != null) {
                message = data.getString(not_type);
                type = not_type;

            }
        }
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if(data.getString("notificationType").equals("track"))
        {
            sendTrackRequestNotification(message,type);
        }

        if(data.getString("notificationType").equals("request"))
        {
            sendRequestNotification(message, type);
        }


        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendRequestNotification(String message, String type) {
        Intent intent = new Intent(this, RequestNotificationActivity.class);
        intent.putExtra(RequestNotificationActivity.DATA,message);
        if (type.equalsIgnoreCase("default")) {
            //nothing will be changed.
        } else if (type.equalsIgnoreCase("type1")) {
            //define intent to do some other tasks than running the main activity
            intent = new Intent(this,RequestNotificationActivity.class);
        } else if (type.equalsIgnoreCase("type2")) {
            //define intent to do some other tasks than running the main activity
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_short_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        //notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendTrackRequestNotification(String message, String type) {
        Intent intent = new Intent(this, WatchersMapsActivity.class);
        intent.putExtra(WatchersMapsActivity.WATCHED_NAME,message);
        if (type.equalsIgnoreCase("default")) {
            //nothing will be changed.
        } else if (type.equalsIgnoreCase("type1")) {
            //define intent to do some other tasks than running the main activity
            intent = new Intent(this,WatchersMapsActivity.class);
        } else if (type.equalsIgnoreCase("type2")) {
            //define intent to do some other tasks than running the main activity
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_short_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        //notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}