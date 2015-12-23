package reminder.iclub.com.remindme;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotifyService extends Service {

    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    private static int NOTIFICATION = 123;
    private int req = 123;
    public static final String INTENT_NOTIFY = "reminder.iclub.com.remindme.INTENT_NOTIFY";
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
        {
            showNotification(intent.getStringExtra("remindertext"));
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new ServiceBinder();

    private void showNotification(String remindertext) {
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap largeicon= BitmapFactory.decodeResource(getResources(), R.drawable.ic_not);

        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pIntent=PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentTitle("Reminde Me");
        notification.setContentText(remindertext);
        notification.setSmallIcon(R.drawable.ic_stat);
        notification.setLargeIcon(largeicon);
        notification.setSound(soundUri);
        notification.setContentIntent(pIntent);
        notification.setTicker(remindertext);
        notification.setOngoing(true);

        Intent i=new Intent(this,NotificationClearIntentService.class);
        i.putExtra("id",NOTIFICATION);
        PendingIntent pIntentService=PendingIntent.getService(this,NOTIFICATION,i,PendingIntent.FLAG_CANCEL_CURRENT);

        notification.addAction(R.drawable.ic_not_action,"Got it.",pIntentService);
        notification.setAutoCancel(true);
        mNM.notify(NOTIFICATION, notification.build());
        NOTIFICATION++;
        stopSelf();
    }
}