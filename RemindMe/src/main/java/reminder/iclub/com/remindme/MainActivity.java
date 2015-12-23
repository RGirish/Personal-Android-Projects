package reminder.iclub.com.remindme;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends ActionBarActivity {

    public ImageButton fab;
    public Dialog dialog,dialog2;
    public int cr_count=0,pr_count=0,count=0;
    public boolean long_click=false;
    public Button cancelbtn,okbtn;
    public EditText et;
    public int d20,d105,dm50,dm8,d70,d15;
    public LinearLayout mainll,mainll2;
    private ScheduleClient scheduleClient;
    private DatePicker dpicker;
    private TimePicker tpicker;
    private EditText remindertext;
    public SQLiteDatabase db;
    public ArrayList<LinearLayout> linearLayouts;
    public ArrayList<String> selectedones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        setContentView(R.layout.layout);
        //changeFontOfTitle();
        setDimensions();
        createDatabase();
        addAllReminders();
    }

    public void addAllReminders()
    {
        fab=(ImageButton)findViewById(R.id.fab);
        mainll=(LinearLayout)findViewById(R.id.mainll);
        mainll2=(LinearLayout)findViewById(R.id.mainll2);
        addCurrentRemindersTv();
        addPastRemindersTv();
        Cursor c=db.rawQuery("SELECT * FROM reminders ORDER BY year,month,day,hour,minute;",null);
        Cursor c2=db.rawQuery("SELECT COUNT(year) FROM reminders;",null);
        try {
            c.moveToFirst();
            c2.moveToFirst();
            count=c2.getInt(0);
            linearLayouts=new ArrayList<LinearLayout>(count);
            selectedones=new ArrayList<String>(count);
            while(!c.isAfterLast())
            {
                Calendar now = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();
                cal.set(c.getInt(3), c.getInt(2), c.getInt(1));
                cal.set(Calendar.HOUR_OF_DAY, c.getInt(4));
                cal.set(Calendar.MINUTE, c.getInt(5));
                cal.set(Calendar.SECOND, 0);
                if(cal.compareTo(now)>0)
                {
                    addReminder(c.getString(0), returnTimeLeftString(cal) , cal , c.getInt(6));
                }
                else
                {
                    addReminder(c.getString(0), returnDateString(c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5)), cal, c.getInt(6));
                }
                c.moveToNext();
            }
        }
        catch (Exception e){Log.e("erroro",e.toString());}
        if(cr_count==0)
        {
            addNothingTextView1();
        }
        if(pr_count==0)
        {
            addNothingTextView2();
        }
    }


    public void onBackPressed()
    {
        if(long_click){
            long_click=false;
            for(int i=0;i<linearLayouts.size();++i){
                fab.setImageResource(R.drawable.plus);
                linearLayouts.get(i).setBackgroundResource(R.drawable.reminder_selector);
                linearLayouts.get(i).setTag("normal");
                selectedones.clear();
            }
        }
        else super.onBackPressed();
    }

    public void addReminder(String text, String date,Calendar cal, int uid)
    {
        LinearLayout ll1=new LinearLayout(this);
        ll1.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp1= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.setMargins(dm50,dm8*2+1,dm50,0);
        ll1.setLayoutParams(lp1);
        ll1.setOrientation(LinearLayout.VERTICAL);
        ll1.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        LinearLayout ll2=new LinearLayout(this);
        ll2.setLongClickable(true);
        ll2.setTag("normal");
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(long_click){
                    if(view.getTag().equals("longclicked")){

                        view.setTag("normal");
                        TextView uidtv=(TextView)view.findViewWithTag("uid");
                        String uid=uidtv.getText().toString();
                        selectedones.remove(uid);
                        if(selectedones.size()==0)onBackPressed();
                        view.setBackgroundResource(R.drawable.reminder_selector);
                    }
                    else {
                        view.setTag("longclicked");
                        TextView uidtv=(TextView)view.findViewWithTag("uid");
                        String uid=uidtv.getText().toString();
                        selectedones.add(uid);
                        view.setBackgroundResource(R.drawable.reminder_longpressed);
                    }

                }
                else{
                    dialog2=new Dialog(MainActivity.this);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.viewreminder);
                    dialog2.show();
                    TextView remindertext=(TextView)dialog2.findViewById(R.id.remindertext);
                    TextView datetime=(TextView)dialog2.findViewById(R.id.datetime);
                    TextView timeleft=(TextView)dialog2.findViewById(R.id.timeleft);

                    //set reminder text
                    TextView temp1=(TextView)view.findViewWithTag("remindertext");
                    remindertext.setText(temp1.getText().toString());

                    //set date and time
                    temp1=(TextView)view.findViewWithTag("date");
                    int dd=Integer.parseInt(temp1.getText().toString());
                    temp1=(TextView)view.findViewWithTag("month");
                    int mo=Integer.parseInt(temp1.getText().toString());
                    temp1=(TextView)view.findViewWithTag("year");
                    int yy=Integer.parseInt(temp1.getText().toString());
                    temp1=(TextView)view.findViewWithTag("hour");
                    int hh=Integer.parseInt(temp1.getText().toString());
                    temp1=(TextView)view.findViewWithTag("minute");
                    int mi=Integer.parseInt(temp1.getText().toString());
                    datetime.setText(returnDateString(dd,mo,yy,hh,mi));

                    //set time left
                    Calendar cal=Calendar.getInstance();
                    Calendar c1=Calendar.getInstance();
                    c1.set(Calendar.YEAR,yy);
                    c1.set(Calendar.MONTH,mo);
                    c1.set(Calendar.DATE,dd);
                    c1.set(Calendar.HOUR,hh);
                    c1.set(Calendar.MINUTE,mi);
                    if(c1.compareTo(cal)>0) {
                        temp1 = (TextView) view.findViewWithTag("datetime");
                        timeleft.setText(temp1.getText().toString());
                    }
                    else{
                        timeleft.setVisibility(View.GONE);
                    }

                }
            }
        });
        ll2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!long_click){
                    fab.setImageResource(R.drawable.delete);
                    long_click=true;
                    TextView uidtv=(TextView)view.findViewWithTag("uid");
                    String uid=uidtv.getText().toString();
                    selectedones.add(uid);
                    view.setTag("longclicked");
                    view.setBackgroundResource(R.drawable.reminder_longpressed);
                }
                return true;
            }
        });
        ll2.setGravity(Gravity.LEFT);
        ll2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp2= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll2.setLayoutParams(lp2);
        ll2.setBackgroundResource(R.drawable.reminder_selector);
        ll2.setPadding(0,0,d70,0);

        TextView tv1=new TextView(this);
        tv1.setText(text);
        tv1.setTag("remindertext");
        LinearLayout.LayoutParams lp3= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.setMargins(d70,d15,0,0);
        tv1.setLayoutParams(lp3);
        tv1.setTextSize(18);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setSingleLine(true);
        tv1.setEllipsize(TextUtils.TruncateAt.END);
        ll2.addView(tv1);

        TextView tv2=new TextView(this);
        tv2.setText(date);
        tv2.setTag("datetime");
        LinearLayout.LayoutParams lp4= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp4.setMargins(d70,0,0,d15);
        tv2.setLayoutParams(lp4);
        tv2.setTextSize(13);
        ll2.addView(tv2);

        TextView tv3=new TextView(this);
        tv3.setTag("uid");
        tv3.setText(Integer.toString(uid));
        tv3.setVisibility(View.GONE);
        ll2.addView(tv3);

        TextView tv4=new TextView(this);
        tv4.setTag("hour");
        tv4.setText(Integer.toString(cal.get(Calendar.HOUR)));
        tv4.setVisibility(View.GONE);
        ll2.addView(tv4);

        TextView tv5=new TextView(this);
        tv5.setTag("minute");
        tv5.setText(Integer.toString(cal.get(Calendar.MINUTE)));
        tv5.setVisibility(View.GONE);
        ll2.addView(tv5);

        TextView tv6=new TextView(this);
        tv6.setTag("date");
        tv6.setText(Integer.toString(cal.get(Calendar.DATE)));
        tv6.setVisibility(View.GONE);
        ll2.addView(tv6);

        TextView tv7=new TextView(this);
        tv7.setTag("month");
        tv7.setText(Integer.toString(cal.get(Calendar.MONTH)));
        tv7.setVisibility(View.GONE);
        ll2.addView(tv7);

        TextView tv8=new TextView(this);
        tv8.setTag("year");
        tv8.setText(Integer.toString(cal.get(Calendar.YEAR)));
        tv8.setVisibility(View.GONE);
        ll2.addView(tv8);

        ll1.addView(ll2);
        linearLayouts.add(ll2);

        Calendar cal2=Calendar.getInstance();
        if(cal.compareTo(cal2)>0){
            mainll.addView(ll1);
            cr_count++;
        }
        else if(cal.compareTo(cal2)<0){
            mainll2.addView(ll1);
            pr_count++;
        }
        else{
            mainll.addView(ll1);
            cr_count++;
        }
    }

    public void onclickedit(View view)
    {

    }

    /*public void changeFontOfTitle()
    {
        TextView tx = (TextView)findViewById(R.id.textView1);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/calli.ttf");
        tx.setTypeface(custom_font);
        TextView textv = (TextView) findViewById(R.id.textView1);
        textv.setShadowLayer(3, 0, 1, Color.GRAY);
    }*/

    public void setDimensions()
    {
        d20=(int)getResources().getDimension(R.dimen.d20);
        d105=(int)getResources().getDimension(R.dimen.d105);
        dm50=(int)getResources().getDimension(R.dimen.dm50);
        d70=(int)getResources().getDimension(R.dimen.d70);
        dm8=(int)getResources().getDimension(R.dimen.dm8);
        d15=(int)getResources().getDimension(R.dimen.d15);
    }

    public void onclickfab(View view)
    {
        if(long_click){
            for(int i=0;i<selectedones.size();++i) {
                Intent intent = new Intent(this, NotifyService.class);
                intent.putExtra(NotifyService.INTENT_NOTIFY, true);
                PendingIntent displayIntent = PendingIntent.getService(this, Integer.parseInt(selectedones.get(i)), intent, PendingIntent.FLAG_NO_CREATE);
                if (displayIntent != null) {
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(displayIntent);
                    displayIntent.cancel();
                }
                db.execSQL("DELETE FROM reminders WHERE uid='"+selectedones.get(i)+"';");
            }
            selectedones.clear();
            long_click=false;
            for(int i=0;i<linearLayouts.size();++i){
                fab.setImageResource(R.drawable.plus);
                linearLayouts.get(i).setBackgroundResource(R.drawable.reminder_selector);
                linearLayouts.get(i).setTag("normal");
                selectedones.clear();
            }
            this.recreate();
        }
        else{
            dialog=new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.newreminder);
            et=(EditText)dialog.findViewById(R.id.et1);
            cancelbtn=(Button)dialog.findViewById(R.id.cancelbtn);
            okbtn=(Button)dialog.findViewById(R.id.okbtn);
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            okbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dpicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    tpicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                    remindertext = (EditText) dialog.findViewById(R.id.et1);
                    if (remindertext.getText().toString().equals("") || remindertext.getText().toString().length() == 0) {
                        Toast.makeText(MainActivity.this, "Enter the text for reminder!", Toast.LENGTH_SHORT).show();
                    } else {
                        int day = dpicker.getDayOfMonth();
                        int month = dpicker.getMonth();
                        int year = dpicker.getYear();
                        int hour = tpicker.getCurrentHour();
                        int minute = tpicker.getCurrentMinute();
                        String text = remindertext.getText().toString();

                        Calendar c = Calendar.getInstance();
                        c.set(year, month, day);
                        c.set(Calendar.HOUR_OF_DAY, hour);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        int uid = (int) ((System.currentTimeMillis() + 1) & 0xfffffff);
                        scheduleClient.setAlarmForNotification(c, text, uid);
                        db.execSQL("INSERT INTO reminders VALUES('" + text + "'," + day + "," + month + "," + year + "," + hour + "," + minute + ",'" + uid + "');");
                        Toast.makeText(MainActivity.this, "Reminder is set!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        /*Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day);
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, 0);
                        addReminder(text, returnDateString(day, month, year, hour, minute), cal, uid);*/
                        MainActivity.this.recreate();
                    }
                }
            });
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            dialog.show();
        }

    }

    private String returnTimeLeftString(Calendar future)
    {
        long SECOND_MILLIS=1000;
        long MINUTE_MILIS=SECOND_MILLIS*60;
        long HOUR_MILLIS=MINUTE_MILIS*60;
        long DAY_MILLIS=HOUR_MILLIS*24;

        long nowmillis=System.currentTimeMillis();
        long futuremillis=future.getTimeInMillis();
        long difference=futuremillis-nowmillis;

        String s="";

        if(difference/DAY_MILLIS>0)
        {
            s=s+(difference/DAY_MILLIS)+" Days, ";
            difference=difference%DAY_MILLIS;
        }
        if(difference/HOUR_MILLIS>0)
        {
            s=s+(difference/HOUR_MILLIS)+" Hours, ";
            difference=difference%HOUR_MILLIS;
        }
        if(difference/MINUTE_MILIS>0)
        {
            s=s+(difference/MINUTE_MILIS)+" Minutes, ";
            difference=difference%MINUTE_MILIS;
        }
        if(difference/SECOND_MILLIS>0)
        {
            s=s+(difference/SECOND_MILLIS)+" Seconds";
        }
        return s;
    }

    private String returnDateString(int day, int month, int year, int hour, int minute)
    {
        String s="";
        Calendar c=new GregorianCalendar(year,month,day);
        switch (c.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.MONDAY:
                s=s+("Mon, ");
                break;
            case Calendar.TUESDAY:
                s=s+("Tue, ");
                break;
            case Calendar.WEDNESDAY:
                s=s+("Wed, ");
                break;
            case Calendar.THURSDAY:
                s=s+("Thu, ");
                break;
            case Calendar.FRIDAY:
                s=s+("Fri, ");
                break;
            case Calendar.SATURDAY:
                s=s+("Sat, ");
                break;
            case Calendar.SUNDAY:
                s=s+("Sun, ");
                break;
        }
        switch (month)
        {
            case Calendar.JANUARY:
                s=s+("Jan "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.FEBRUARY:
                s=s+("Feb "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.MARCH:
                s=s+("Mar "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.APRIL:
                s=s+("Apr "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.MAY:
                s=s+("May "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.JUNE:
                s=s+("Jun "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.JULY:
                s=s+("Jul "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.AUGUST:
                s=s+("Aug "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.SEPTEMBER:
                s=s+("Sep "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.OCTOBER:
                s=s+("Oct "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.NOVEMBER:
                s=s+("Nov "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
            case Calendar.DECEMBER:
                s=s+("Dec "+day+", "+year+" - "+String.format("%02d",hour)+":"+String.format("%02d",minute));
                break;
        }
        return s;
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

    @Override
    protected void onStop() {
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

    public void addNothingTextView1()
    {
        TextView cr=new TextView(this);
        cr.setText("No reminders to display!");
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,d20,0,d20);
        cr.setLayoutParams(lp);
        cr.setTextSize(16);
        cr.setTypeface(null,Typeface.BOLD);
        mainll.addView(cr);
    }

    public void addNothingTextView2()
    {
        TextView cr=new TextView(this);
        cr.setText("No reminders to display!");
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,d20,0,d15+d15);
        cr.setLayoutParams(lp);
        cr.setTextSize(16);
        cr.setTypeface(null,Typeface.BOLD);
        mainll2.addView(cr);
    }

    public void addCurrentRemindersTv()
    {
        TextView cr=new TextView(this);
        cr.setText("Current Reminders");
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(d20,d105,0,d20);
        lp.gravity=Gravity.LEFT;
        cr.setLayoutParams(lp);
        cr.setTextSize(16);
        cr.setTypeface(null,Typeface.BOLD);
        mainll.addView(cr);
    }

    public void addPastRemindersTv()
    {
        TextView cr=new TextView(this);
        cr.setText("Past Reminders");
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(d20,d20,0,d20);
        lp.gravity=Gravity.LEFT;
        cr.setLayoutParams(lp);
        cr.setTextSize(16);
        cr.setTypeface(null,Typeface.BOLD);
        mainll2.addView(cr);
    }

}