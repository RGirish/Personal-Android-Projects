
package com.iclub.sampleservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {

    public class ServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    private static int NOTIFICATION = 123;
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new ServiceBinder();

    private void showNotification() {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentTitle("TN 06 B 1468");
        notification.setContentText("Black Honda City @ S12");
        notification.setSmallIcon(R.drawable.ic_notification);
        notification.setSound(soundUri);
        notification.setContentIntent(pIntent);
        notification.setTicker("Black Honda City @ S12");
        notification.setOngoing(true);
        notification.setAutoCancel(true);

        Intent i = new Intent(this, NotificationClearIntentService.class);
        i.putExtra("id", NOTIFICATION);
        PendingIntent pIntentService = PendingIntent.getService(this, NOTIFICATION, i, PendingIntent.FLAG_CANCEL_CURRENT);

        notification.addAction(R.drawable.check, "Done", pIntentService);
        mNM.notify(NOTIFICATION, notification.build());
        NOTIFICATION++;
    }
}