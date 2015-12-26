package girish.raman.internationalbirthdayreminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FormViewHolder> {

    List<Contact> contacts;
    Activity activity;

    RVAdapter(List<Contact> contacts, Activity activity) {
        this.activity = activity;
        this.contacts = contacts;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public FormViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_details_item, viewGroup, false);
        return new FormViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FormViewHolder formViewHolder, final int i) {
        formViewHolder.name.setText(contacts.get(i).name);
        formViewHolder.birthday.setText(contacts.get(i).birthday);
        formViewHolder.id.setText(contacts.get(i).ID);
        if (contacts.get(i).reminder) {
            formViewHolder.reminderSwitch.setChecked(true);
            formViewHolder.timeZone.setVisibility(View.VISIBLE);
            formViewHolder.timeZone.setText(contacts.get(i).timeZone);
        } else {
            formViewHolder.reminderSwitch.setChecked(false);
            formViewHolder.timeZone.setVisibility(View.GONE);
        }
        formViewHolder.reminderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((SwitchCompat) v).isChecked()) {

                    Cursor temp = MainActivity.db.rawQuery("SELECT COUNT(timezone) FROM mytimezone;", null);
                    temp.moveToFirst();
                    if (temp.getInt(0) == 0) {
                        final Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.mainCoordinatorLayout), "First, you need to set your Timezone. Access the menu to do that!", Snackbar.LENGTH_INDEFINITE);
                        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                        temp.close();
                        ((SwitchCompat) v).setChecked(false);
                        return;
                    }

                    final String name = contacts.get(i).name;
                    final String birthday = contacts.get(i).birthday;

                    String[] TZ = TimeZone.getAvailableIDs();
                    ArrayList<String> TZ1 = new ArrayList<>();
                    for (int i = 0; i < TZ.length; i++) {
                        if (!(TZ1.contains(TimeZone.getTimeZone(TZ[i]).getDisplayName()))) {
                            TZ1.add(TimeZone.getTimeZone(TZ[i]).getDisplayName());
                        }
                    }
                    final String[] TZ2 = TZ1.toArray(new String[TZ1.size()]);
                    Arrays.sort(TZ2);
                    AlertDialog.Builder b = new AlertDialog.Builder(activity);
                    b.setTitle("Select the person's Time Zone");
                    b.setCancelable(false);
                    b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Snackbar.make(activity.findViewById(R.id.mainCoordinatorLayout), "No changes made!", Snackbar.LENGTH_LONG).show();
                            formViewHolder.reminderSwitch.setChecked(false);
                        }
                    });
                    b.setItems(TZ2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            dialog.dismiss();
                            String timezone = TZ2[pos];

                            Cursor c = MainActivity.db.rawQuery("SELECT COUNT(name) FROM reminders WHERE contactid='" + contacts.get(i).ID + "';", null);
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

                                Intent myIntent = new Intent(activity, MyBroadcastReceiver.class);
                                myIntent.putExtra("name", name);
                                int alarmID = (int) System.currentTimeMillis();
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, alarmID, myIntent, 0);
                                MainActivity.alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

                                MainActivity.db.execSQL("INSERT INTO reminders VALUES('" + contacts.get(i).ID + "','" + name + "','" + birthday + "','" + timezone + "','" + alarmID + "');");
                                Snackbar.make(activity.findViewById(R.id.mainCoordinatorLayout), "Reminder set!", Snackbar.LENGTH_LONG).show();
                            } else {
                                MainActivity.db.execSQL("UPDATE reminders SET timezone='" + timezone + "' WHERE contactid='" + contacts.get(i).ID + "';");
                                /**
                                 * delete already set reminder and set a new one
                                 */
                                Snackbar.make(activity.findViewById(R.id.mainCoordinatorLayout), "Reminder updated!", Snackbar.LENGTH_LONG).show();
                            }
                            c.close();
                            contacts.get(i).reminder = true;
                            formViewHolder.timeZone.setVisibility(View.VISIBLE);
                            formViewHolder.timeZone.setText(timezone);
                            contacts.get(i).timeZone = timezone;
                        }
                    });
                    b.show();
                } else {
                    Cursor c = MainActivity.db.rawQuery("SELECT COUNT(name) FROM reminders WHERE contactid='" + contacts.get(i).ID + "';", null);
                    c.moveToFirst();
                    if (c.getInt(0) != 0) {
                        Cursor cursor = MainActivity.db.rawQuery("SELECT alarmid,name FROM reminders WHERE contactid='" + contacts.get(i).ID + "';", null);
                        cursor.moveToFirst();

                        Intent myIntent = new Intent(activity, MyBroadcastReceiver.class);
                        myIntent.putExtra("name", cursor.getString(1));
                        int alarmID = Integer.parseInt(cursor.getString(0));
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, alarmID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        MainActivity.alarmManager.cancel(pendingIntent);

                        MainActivity.db.execSQL("DELETE FROM reminders WHERE contactid='" + contacts.get(i).ID + "';");
                        Snackbar.make(activity.findViewById(R.id.mainCoordinatorLayout), "Reminder deleted!", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(activity.findViewById(R.id.mainCoordinatorLayout), "No changes made!", Snackbar.LENGTH_LONG).show();
                    }
                    contacts.get(i).reminder = false;
                    formViewHolder.timeZone.setVisibility(View.GONE);
                    contacts.get(i).timeZone = "";
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FormViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView birthday;
        TextView id;
        TextView timeZone;
        SwitchCompat reminderSwitch;

        FormViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            birthday = (TextView) itemView.findViewById(R.id.birthday);
            id = (TextView) itemView.findViewById(R.id.contactID);
            timeZone = (TextView) itemView.findViewById(R.id.timeZone);
            reminderSwitch = (SwitchCompat) itemView.findViewById(R.id.reminderSwitch);
        }
    }
}