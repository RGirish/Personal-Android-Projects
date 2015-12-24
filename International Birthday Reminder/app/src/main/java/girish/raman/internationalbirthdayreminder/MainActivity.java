package girish.raman.internationalbirthdayreminder;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    List<Contact> contacts;
    SQLiteDatabase db;
    AlarmManager alarmManager;

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
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, int position) {

                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.set_reminder);

                        final Cursor cursor = db.rawQuery("SELECT name, birthday, timezone FROM reminders WHERE contactid = '" + ((TextView) view.findViewById(R.id.contactID)).getText().toString() + "';", null);
                        cursor.moveToFirst();
                        final SwitchCompat switchCompat = (SwitchCompat) dialog.findViewById(R.id.setReminderSwitch);
                        if (cursor.getCount() != 0) {
                            switchCompat.setChecked(true);
                            TextView textView = (TextView) dialog.findViewById(R.id.setReminderReminderSetTextView);
                            textView.setText("Reminder set for " + cursor.getString(0) + " in " + cursor.getString(2));
                            textView.setVisibility(View.VISIBLE);
                        }
                        ((TextView) dialog.findViewById(R.id.setReminderChooseTimeZoneTextView)).setText("Choose " + ((TextView) view.findViewById(R.id.name)).getText().toString() + "'s Time Zone");
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        String[] TZ = TimeZone.getAvailableIDs();
                        ArrayList<String> TZ1 = new ArrayList<>();
                        for (int i = 0; i < TZ.length; i++) {
                            if (!(TZ1.contains(TimeZone.getTimeZone(TZ[i]).getDisplayName()))) {
                                TZ1.add(TimeZone.getTimeZone(TZ[i]).getDisplayName());
                            }
                        }
                        for (int i = 0; i < TZ1.size(); i++) {
                            adapter.add(TZ1.get(i));
                        }
                        AppCompatSpinner TZone = (AppCompatSpinner) dialog.findViewById(R.id.setReminderSpinner);
                        TZone.setAdapter(adapter);

                        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    dialog.findViewById(R.id.setReminderChooseTimeZoneTextView).setVisibility(View.VISIBLE);
                                    dialog.findViewById(R.id.setReminderSpinner).setVisibility(View.VISIBLE);
                                } else {
                                    dialog.findViewById(R.id.setReminderChooseTimeZoneTextView).setVisibility(View.GONE);
                                    dialog.findViewById(R.id.setReminderSpinner).setVisibility(View.GONE);
                                    dialog.findViewById(R.id.setReminderReminderSetTextView).setVisibility(View.GONE);
                                }
                            }
                        });

                        dialog.findViewById(R.id.setReminderDoneButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String contactID = ((TextView) view.findViewById(R.id.contactID)).getText().toString();
                                if (switchCompat.isChecked()) {
                                    String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                                    String birthday = ((TextView) view.findViewById(R.id.birthday)).getText().toString();
                                    String timezone = ((AppCompatSpinner) dialog.findViewById(R.id.setReminderSpinner)).getSelectedItem().toString();

                                    Cursor temp = db.rawQuery("SELECT COUNT(timezone) FROM mytimezone;", null);
                                    temp.moveToFirst();
                                    if (temp.getInt(0) == 0) {
                                        dialog.dismiss();
                                        final Snackbar snackbar = Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "First, you need to set your Timezone! Access the menu to do that!", Snackbar.LENGTH_INDEFINITE);
                                        snackbar.setActionTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                        snackbar.setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                snackbar.dismiss();
                                            }
                                        });
                                        snackbar.show();
                                        temp.close();
                                        return;
                                    }

                                    Cursor c = db.rawQuery("SELECT COUNT(name) FROM reminders WHERE contactid='" + contactID + "';", null);
                                    c.moveToFirst();
                                    if (c.getInt(0) == 0) {

                                        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
                                        Date theDate = null;
                                        try {
                                            theDate = format.parse(birthday);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        Calendar bdayCal = new GregorianCalendar();
                                        bdayCal.setTime(theDate);

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(Calendar.DAY_OF_MONTH, bdayCal.get(Calendar.DAY_OF_MONTH));
                                        calendar.set(Calendar.MONTH, bdayCal.get(Calendar.MONTH));

                                        int bdayMonth = bdayCal.get(Calendar.MONTH);
                                        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

                                        int bdayDate = bdayCal.get(Calendar.DAY_OF_MONTH);
                                        int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                                        if (currentMonth > bdayMonth) {
                                            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
                                        } else {
                                            if (currentMonth == bdayMonth) {
                                                if (currentDate > bdayDate)
                                                    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
                                                else
                                                    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                                            } else {
                                                calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                                            }
                                        }
                                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                                        calendar.set(Calendar.MINUTE, 0);

                                        /**
                                          Calculate 00:00 in my time zone
                                         */

                                        Intent myIntent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
                                        myIntent.putExtra("name", name);
                                        int alarmID = (int) System.currentTimeMillis();
                                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, alarmID, myIntent, 0);
                                        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

                                        db.execSQL("INSERT INTO reminders VALUES('" + contactID + "','" + name + "','" + birthday + "','" + timezone + "','" + alarmID + "');");
                                        Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "Reminder set!", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        db.execSQL("UPDATE reminders SET timezone='" + timezone + "' WHERE contactid='" + contactID + "';");
                                        /**
                                         * delete already set reminder and set a new one
                                         */
                                        Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "Reminder updated!", Snackbar.LENGTH_LONG).show();
                                    }
                                    c.close();
                                } else {
                                    Cursor c = db.rawQuery("SELECT COUNT(name) FROM reminders WHERE contactid='" + contactID + "';", null);
                                    c.moveToFirst();
                                    if (c.getInt(0) != 0) {
                                        Cursor cursor = db.rawQuery("SELECT alarmid,name FROM reminders WHERE contactid='" + contactID + "';", null);
                                        cursor.moveToFirst();

                                        Intent myIntent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
                                        myIntent.putExtra("name", cursor.getString(1));
                                        int alarmID = Integer.parseInt(cursor.getString(0));
                                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, alarmID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        alarmManager.cancel(pendingIntent);

                                        db.execSQL("DELETE FROM reminders WHERE contactid='" + contactID + "';");
                                        Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "Reminder deleted!", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "No changes made!", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                })
        );
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
            try{
                String timeZone = cursor2.getString(0);
                contacts.add(new Contact(name, birthday, id, true, "in " + timeZone));
            }catch (Exception e){
                contacts.add(new Contact(name, birthday, id, false, ""));
            }
            cursor2.close();
        }

        RVAdapter adapter = new RVAdapter(contacts);
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
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.set_my_time_zone);

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            String[] TZ = TimeZone.getAvailableIDs();
            ArrayList<String> TZ1 = new ArrayList<>();
            for (int i = 0; i < TZ.length; i++) {
                if (!(TZ1.contains(TimeZone.getTimeZone(TZ[i]).getDisplayName()))) {
                    TZ1.add(TimeZone.getTimeZone(TZ[i]).getDisplayName());
                }
            }
            for (int i = 0; i < TZ1.size(); i++) {
                adapter.add(TZ1.get(i));
            }
            final AppCompatSpinner TZone = (AppCompatSpinner) dialog.findViewById(R.id.timeZones);
            TZone.setAdapter(adapter);
            for (int i = 0; i < TZ1.size(); i++) {
                if (TZ1.get(i).equals(TimeZone.getDefault().getDisplayName())) {
                    TZone.setSelection(i);
                }
            }
            dialog.findViewById(R.id.setTimeZoneButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.execSQL("DELETE FROM mytimezone;");
                    Toast.makeText(MainActivity.this, "Your Time Zone is set to " + TZone.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    db.execSQL("INSERT INTO mytimezone VALUES('" + TZone.getSelectedItem().toString() + "');");
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}