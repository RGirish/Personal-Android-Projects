package girish.raman.internationalbirthdayreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent receivedIntent) {
        showNotification(context, receivedIntent);
    }

    public void showNotification(Context context, Intent receivedIntent) {
        String name = receivedIntent.getStringExtra("name");

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(context)
                .setTicker("Hey, it's " + name + "'s birthday!")
                .setSmallIcon(getNotificationIcon())
                .setContentTitle("Birthday alert!")
                .setSmallIcon(R.drawable.ic_reminder)
                .setPriority(Notification.PRIORITY_MAX)
                .setVibrate(new long[0])
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setContentText("Hey, it's " + name + "'s birthday!")
                .setContentIntent(pi)
                .build();

        notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

        try {
            Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, noti);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_reminder: R.mipmap.ic_launcher;
    }
}