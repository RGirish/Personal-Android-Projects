package reminder.iclub.com.remindme;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

public class NotificationClearIntentService extends IntentService{

    public NotificationClearIntentService(){
        super("NotificationClearIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int k=intent.getIntExtra("id", 0);
        nm.cancel(k);
    }
}