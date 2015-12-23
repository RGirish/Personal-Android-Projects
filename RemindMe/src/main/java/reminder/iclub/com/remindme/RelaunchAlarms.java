package reminder.iclub.com.remindme;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

public class RelaunchAlarms extends IntentService{

    private SQLiteDatabase db;
    private ScheduleClient scheduleClient;

    public RelaunchAlarms(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        createDatabase();

        Cursor c=db.rawQuery("SELECT * FROM reminders;",null);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                c1.set(c.getInt(3), c.getInt(2), c.getInt(1));
                c1.set(Calendar.HOUR_OF_DAY, c.getInt(4));
                c1.set(Calendar.MINUTE, c.getInt(5));
                c1.set(Calendar.SECOND, 0);
                if (c1.compareTo(c2) > 0) {
                    String uidold=c.getString(6);
                    Log.e("Helohelohelo","helohelohelo");
                    int uidnew = (int) ((System.currentTimeMillis() + 1) & 0xfffffff);
                    scheduleClient.setAlarmForNotification(c1, c.getString(0), uidnew);
                    db.execSQL("UPDATE reminders SET uid='"+uidnew+"' WHERE uid='"+uidold+"';");
                }
                c.moveToNext();
            }
        }
        catch (Exception e){}

        }

    private void createDatabase()
    {
        db = openOrCreateDatabase("remindme.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        try
        {
            db.execSQL("CREATE TABLE reminders(name text, day number, month number, year number, hour number, minute number,uid text);");
        }
        catch(Exception e){
            Log.i("error", "MainActivity.java, createDatabases()");
        }
    }
}
