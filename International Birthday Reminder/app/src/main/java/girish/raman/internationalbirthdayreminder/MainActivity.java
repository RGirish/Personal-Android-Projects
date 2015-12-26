package girish.raman.internationalbirthdayreminder;

import android.app.AlarmManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase db;
    static AlarmManager alarmManager;
    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        db = openOrCreateDatabase("bdayoverseas.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        try {
            db.execSQL("CREATE TABLE mytimezone(timezone TEXT)");
        } catch (Exception e) {
        }
        try {
            db.execSQL("CREATE TABLE reminders(contactid TEXT, name TEXT, birthday TEXT, timezone TEXT, alarmid TEXT);");
        } catch (Exception e) {
        }

        // iterate through all Contact's Birthdays and print in log
        Cursor cursor = getContactsBirthdays();
        int bDayColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
        int nameColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.DISPLAY_NAME);
        int idColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.CONTACT_ID);

        final RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        contacts = new ArrayList<>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(nameColumn);
            String bDay = cursor.getString(bDayColumn);
            String id = cursor.getString(idColumn);

            SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
            Date theDate;
            try {
                theDate = format.parse(bDay);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    theDate = format.parse(bDay);
                } catch (ParseException e2) {
                    format = new SimpleDateFormat("--MM-dd", Locale.ENGLISH);
                    try {
                        theDate = format.parse(bDay);
                    } catch (ParseException e3) {
                        Log.e("ParseException", "ParseException");
                        continue;
                    }
                }
            }

            Calendar myCal = new GregorianCalendar();
            myCal.setTime(theDate);
            String birthday = new DateFormatSymbols().getMonths()[myCal.get(Calendar.MONTH)] + " " + String.valueOf(myCal.get(Calendar.DAY_OF_MONTH)) + ", " + String.valueOf(myCal.get(Calendar.YEAR));
            Cursor cursor2 = db.rawQuery("SELECT timezone FROM reminders WHERE contactid='" + id + "';", null);
            cursor2.moveToFirst();
            try {
                String timeZone = cursor2.getString(0);
                contacts.add(new Contact(name, birthday, id, true, "in " + timeZone));
            } catch (Exception e) {
                contacts.add(new Contact(name, birthday, id, false, ""));
            }
            cursor2.close();
        }

        RVAdapter adapter = new RVAdapter(contacts, this);
        rv.setAdapter(adapter);
    }

    private Cursor getContactsBirthdays() {
        Uri uri = ContactsContract.Data.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.START_DATE
        };

        String where =
                ContactsContract.Data.MIMETYPE + "= ? AND " +
                        ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
        String[] selectionArgs = new String[]{
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
        };
        return getContentResolver().query(uri, projection, where, selectionArgs, ContactsContract.Contacts.DISPLAY_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_set_time_zone) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Select your Time Zone");
            b.setCancelable(false);
            b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            String[] TZ = TimeZone.getAvailableIDs();
            ArrayList<String> TZ1 = new ArrayList<>();
            for (int i = 0; i < TZ.length; i++) {
                if (!(TZ1.contains(TimeZone.getTimeZone(TZ[i]).getDisplayName()))) {
                    TZ1.add(TimeZone.getTimeZone(TZ[i]).getDisplayName());
                }
            }
            final String[] TZ2 = TZ1.toArray(new String[TZ1.size()]);
            Arrays.sort(TZ2);
            b.setItems(TZ2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int pos) {
                    db.execSQL("DELETE FROM mytimezone;");
                    Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "Your Time Zone is set to " + TZ2[pos], Snackbar.LENGTH_LONG).show();
                    db.execSQL("INSERT INTO mytimezone VALUES('" + TZ2[pos] + "');");
                }
            });
            b.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}