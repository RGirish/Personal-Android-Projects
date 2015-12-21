package girish.raman.internationalbirthdayreminder;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    List<Contact> contacts;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = openOrCreateDatabase("bdayoverseas.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        try {
            db.execSQL("CREATE TABLE mytimezone(timezone TEXT)");
        } catch (Exception e) {
        }
        try {
            db.execSQL("CREATE TABLE reminders(contactID TEXT, name TEXT, birthday TEXT, timezone TEXT);");
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

                        Cursor cursor = db.rawQuery("SELECT name, birthday, timezone FROM reminders WHERE contactid = '" + ((TextView) view.findViewById(R.id.contactID)).getText().toString() + "';", null);
                        cursor.moveToFirst();
                        final SwitchCompat switchCompat = (SwitchCompat) dialog.findViewById(R.id.setReminderSwitch);
                        if (cursor.getCount() != 0) {
                            switchCompat.setChecked(true);
                            TextView textView = (TextView) dialog.findViewById(R.id.setReminderReminderSetTextView);
                            textView.setText("Reminder set for " + cursor.getString(0) + " on " + cursor.getString(1) + " in " + cursor.getString(2));
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

                                    Cursor c = db.rawQuery("SELECT COUNT(name) FROM reminders WHERE contactid='" + contactID + "';", null);
                                    c.moveToFirst();
                                    if (c.getInt(0) == 0) {
                                        db.execSQL("INSERT INTO reminders VALUES('" + contactID + "','" + name + "','" + birthday + "','" + timezone + "');");
                                    } else {
                                        db.execSQL("UPDATE reminders SET timezone='" + timezone + "' WHERE contactid='" + contactID + "';");
                                    }


                                    /*
                                     * Set the alarm
                                     */


                                    c.close();
                                } else {
                                    Cursor c = db.rawQuery("SELECT COUNT(name) FROM reminders WHERE contactid='" + contactID + "';", null);
                                    c.moveToFirst();
                                    if (c.getInt(0) != 0) {
                                        db.execSQL("DELETE FROM reminders WHERE contactid='" + contactID + "';");
                                        Toast.makeText(MainActivity.this, "Reminder deleted!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "No changes made!", Toast.LENGTH_SHORT).show();
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
            String bDay = cursor.getString(bDayColumn);
            String name = cursor.getString(nameColumn);
            String id = cursor.getString(idColumn);
            contacts.add(new Contact(name, bDay, id));
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