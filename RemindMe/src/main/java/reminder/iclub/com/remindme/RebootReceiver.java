package reminder.iclub.com.remindme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent=new Intent(context,RelaunchAlarms.class);
        context.startService(startServiceIntent);
    }
}
