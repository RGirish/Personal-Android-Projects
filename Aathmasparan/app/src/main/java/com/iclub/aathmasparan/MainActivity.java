
package com.iclub.aathmasparan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, FetchDataListener {

    String NAME,USERNAME,PASSWORD,PHONE,EMAIL,ADD1,ADD2,CITY,PINCODE,STATE,BLOODTYPE,DOB,LDD;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    ProgressDialog pdialog;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up the local database to keep track of who is currently logged in
        db = openOrCreateDatabase("aathmasparan.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        try{
            db.execSQL("CREATE TABLE session(username TEXT,name TEXT,dob TEXT,address1 TEXT,address2 TEXT,city TEXT,pincode TEXT,state TEXT,phone TEXT,email TEXT,lastdonated TEXT,bloodtype TEXT);");
        }catch(Exception e){}

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position){
            case 0:
                fragmentManager.beginTransaction().replace(R.id.container, HomeFragment.newInstance(position + 1)).commit();
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.container, AboutUsFragment.newInstance(position + 1)).commit();
                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.container, SomeFactsFragment.newInstance(position + 1)).commit();
                break;
            case 3:
                fragmentManager.beginTransaction().replace(R.id.container, FindDonorFragment.newInstance(position + 1)).commit();
                break;
            case 4:
                fragmentManager.beginTransaction().replace(R.id.container, RegisterDonorFragment.newInstance(position + 1)).commit();
                break;
            case 5:
                Cursor cursor = db.rawQuery("SELECT * FROM session;", null);
                try{
                    cursor.moveToFirst();
                    cursor.getString(0);
                    fragmentManager.beginTransaction().replace(R.id.container, ProfileFragment.newInstance(position + 1)).commit();
                }catch (Exception e){
                    fragmentManager.beginTransaction().replace(R.id.container, DonorLoginFragment.newInstance(position + 1)).commit();
                }
                break;
            case 6:
                fragmentManager.beginTransaction().replace(R.id.container, ContactFragment.newInstance(position + 1)).commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                Cursor cursor = db.rawQuery("SELECT * FROM session;", null);
                try{
                    cursor.moveToFirst();
                    cursor.getString(0);
                    mTitle = getString(R.string.title_section6_logged_in);
                }catch (Exception e){
                    mTitle = getString(R.string.title_section6_logged_out);
                }
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">"+mTitle+"</font>"));
        }else{
            actionBar.setTitle(Html.fromHtml("<font color=\"#444444\">"+mTitle+"</font>"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            Cursor cursor = db.rawQuery("SELECT * FROM session;", null);
            try{
                cursor.moveToFirst();
                cursor.getString(0);
                getMenuInflater().inflate(R.menu.logged_in, menu);
            }catch (Exception e){
                getMenuInflater().inflate(R.menu.logged_out, menu);
            }
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            db.execSQL("DELETE FROM session;");
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.container, DonorLoginFragment.newInstance(6)).commit();
                            mTitle = "Donor Login";
                            invalidateOptionsMenu();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class HomeFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static HomeFragment newInstance(int sectionNumber) {
            HomeFragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public HomeFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class AboutUsFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static AboutUsFragment newInstance(int sectionNumber) {
            AboutUsFragment fragment = new AboutUsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public AboutUsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class SomeFactsFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static SomeFactsFragment newInstance(int sectionNumber) {
            SomeFactsFragment fragment = new SomeFactsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public SomeFactsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_somefacts, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class FindDonorFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        public static View rootView;

        public static FindDonorFragment newInstance(int sectionNumber) {
            FindDonorFragment fragment = new FindDonorFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public FindDonorFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_finddonor, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    public static class RegisterDonorFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static RegisterDonorFragment newInstance(int sectionNumber) {
            RegisterDonorFragment fragment = new RegisterDonorFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public RegisterDonorFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_register, container, false);
            rootView.findViewById(R.id.dob).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    ((TextView)rootView.findViewById(R.id.dob)).setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();
                }
            });

            rootView.findViewById(R.id.lastdonation).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    ((TextView)rootView.findViewById(R.id.lastdonation)).setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();
                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class DonorLoginFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static DonorLoginFragment newInstance(int sectionNumber) {
            DonorLoginFragment fragment = new DonorLoginFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public DonorLoginFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_donorlogin, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    public static class ContactFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static ContactFragment newInstance(int sectionNumber) {
            ContactFragment fragment = new ContactFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ContactFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class ProfileFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static ProfileFragment newInstance(int sectionNumber) {
            ProfileFragment fragment = new ProfileFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ProfileFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            Cursor cursor = db.rawQuery("SELECT username,name,dob,address1,address2,city,pincode,state,phone,email,lastdonated,bloodtype FROM session;", null);
            cursor.moveToFirst();
            ((TextView)rootView.findViewById(R.id.username_profile)).setText(cursor.getString(0));
            ((EditText)rootView.findViewById(R.id.name_profile)).setText(cursor.getString(1));
            ((TextView)rootView.findViewById(R.id.dob_profile)).setText(cursor.getString(2));
            ((EditText)rootView.findViewById(R.id.address1_profile)).setText(cursor.getString(3));
            ((EditText)rootView.findViewById(R.id.address2_profile)).setText(cursor.getString(4));
            ((EditText)rootView.findViewById(R.id.city_profile)).setText(cursor.getString(5));
            ((EditText)rootView.findViewById(R.id.pincode_profile)).setText(cursor.getString(6));
            ((Spinner)rootView.findViewById(R.id.state_profile)).setSelection(7);
            ((EditText)rootView.findViewById(R.id.phone_profile)).setText(cursor.getString(8));
            ((EditText)rootView.findViewById(R.id.email_profile)).setText(cursor.getString(9));
            ((TextView)rootView.findViewById(R.id.lastdonation_profile)).setText(cursor.getString(10));
            ((Spinner)rootView.findViewById(R.id.bloodtype_profile)).setSelection(11);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void onClickPhoneNumber(View v){
        String url = "tel:+919003219694";
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
        startActivity(intent);
    }

    public void onClickEmail(View v){
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"aathmasparan@gmail.com"});
        startActivity(Intent.createChooser(emailIntent, "Send mail via..."));
    }

    public void onClickAddress(View v){
        String map = "http://maps.google.co.in/maps?q=Palavakkam, Chennai";
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(i);
    }

    public void onClickRegister(View v){
        NAME = ((TextView)findViewById(R.id.name)).getText().toString().trim().replace(" ","%20");
        USERNAME = ((TextView)findViewById(R.id.username)).getText().toString().trim().replace(" ","%20");
        PASSWORD = ((TextView)findViewById(R.id.password)).getText().toString().trim().replace(" ","%20");
        PHONE = ((TextView)findViewById(R.id.phone)).getText().toString().trim().replace(" ","%20");
        EMAIL = ((TextView)findViewById(R.id.email)).getText().toString().trim().replace(" ","%20");
        ADD1 = ((TextView)findViewById(R.id.address1)).getText().toString().trim().replace(" ","%20");
        ADD2 = ((TextView)findViewById(R.id.address2)).getText().toString().trim().replace(" ","%20");
        CITY = ((TextView)findViewById(R.id.city)).getText().toString().trim().replace(" ","%20");
        PINCODE = ((TextView)findViewById(R.id.pincode)).getText().toString().trim().replace(" ","%20");
        STATE = ((Spinner)findViewById(R.id.state)).getSelectedItem().toString().trim().replace(" ","%20");
        BLOODTYPE = ((Spinner)findViewById(R.id.bloodtype)).getSelectedItem().toString().trim().replace(" ", "%20").replace("+", "%2B");
        DOB = ((TextView)findViewById(R.id.dob)).getText().toString().trim().replace(" ","%20");
        LDD = ((TextView)findViewById(R.id.lastdonation)).getText().toString().trim().replace(" ","%20");

        if(NAME.equals("") || USERNAME.equals("") || PASSWORD.equals("") || PHONE.equals("") || EMAIL.equals("") || ADD1.equals("") || ADD2.equals("") || CITY.equals("") || PINCODE.equals("") || STATE.equals("Select%20Your%20State") || BLOODTYPE.equals("Select%20Blood%20Type") || DOB.equals("Set%20Date%20of%20Birth") || LDD.equals("Set%20Last%20Donation%20Date")){
            Toast.makeText(MainActivity.this, "Don't leave the fields empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(PHONE.length()!=10){
            Toast.makeText(MainActivity.this, "Enter a valid Phone Number!", Toast.LENGTH_LONG).show();
        }
        if(!isValidEmail(EMAIL)){
            Toast.makeText(MainActivity.this, "Enter a valid Email ID!", Toast.LENGTH_LONG).show();
            return;
        }
        if(PINCODE.length()!=6){
            Toast.makeText(MainActivity.this, "Enter a valid Pincode!", Toast.LENGTH_LONG).show();
        }

        if(checkConnection()){
            pdialog = ProgressDialog.show(this, null, "Please wait...", true);
            String get_params = "uname="+USERNAME;
            CheckUsernameTask task = new CheckUsernameTask(this);
            task.execute(get_params);
        }else{
            Toast.makeText(this, "Check Your Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickFindDonor(View v){
        if(checkConnection()) {
            pdialog = ProgressDialog.show(this, null, "Please wait...", true);

            String STATE = ((Spinner) findViewById(R.id.state_finddonor)).getSelectedItem().toString().trim().replace(" ", "%20");
            String CITY = ((EditText) findViewById(R.id.city_finddonor)).getText().toString().trim().replace(" ", "%20");
            String BLOODTYPE = ((Spinner) findViewById(R.id.bloodtype_finddonor)).getSelectedItem().toString().trim().replace(" ", "%20").replace("+", "%2B");

            if (CITY.equals("") || STATE.equals("Select%20Your%20State") || BLOODTYPE.equals("Select%20Blood%20Type")) {
                Toast.makeText(MainActivity.this, "Don't leave the fields empty!", Toast.LENGTH_LONG).show();
                pdialog.dismiss();
                return;
            }

            String get_params = "state=" + STATE + "&city=" + CITY + "&bloodtype=" + BLOODTYPE;
            SearchTask task = new SearchTask(this);
            task.execute(get_params);
        }else{
            Toast.makeText(MainActivity.this, "Please check your Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickLogin(View v){
        if(checkConnection()){
            String USERNAME = ((TextView)findViewById(R.id.username_login)).getText().toString().trim().replace(" ","%20");
            String PASSWORD = ((TextView)findViewById(R.id.password_login)).getText().toString().trim().replace(" ","%20");
            if(USERNAME.equals("") || PASSWORD.equals("")){
                Toast.makeText(MainActivity.this, "Don't leave the fields empty!", Toast.LENGTH_LONG).show();
                return;
            }
            pdialog = ProgressDialog.show(this, null, "Please wait...", true);
            String get_params = "login_username="+USERNAME+"&login_password="+PASSWORD;
            LoginTask task = new LoginTask(this);
            task.execute(get_params);
        }else{
            Toast.makeText(MainActivity.this, "Please check your Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickUpdateProfile(View v){
        pdialog = ProgressDialog.show(this, null, "Please wait...", true);
        NAME = ((EditText)findViewById(R.id.name_profile)).getText().toString().trim().replace(" ","%20");
        USERNAME = ((TextView)findViewById(R.id.username_profile)).getText().toString().trim().replace(" ","%20");
        PASSWORD = ((EditText)findViewById(R.id.password_profile)).getText().toString().trim().replace(" ","%20");
        PHONE = ((EditText)findViewById(R.id.phone_profile)).getText().toString().trim().replace(" ","%20");
        EMAIL = ((EditText)findViewById(R.id.email_profile)).getText().toString().trim().replace(" ","%20");
        ADD1 = ((EditText)findViewById(R.id.address1_profile)).getText().toString().trim().replace(" ","%20");
        ADD2 = ((EditText)findViewById(R.id.address2_profile)).getText().toString().trim().replace(" ","%20");
        CITY = ((EditText)findViewById(R.id.city_profile)).getText().toString().trim().replace(" ","%20");
        PINCODE = ((EditText)findViewById(R.id.pincode_profile)).getText().toString().trim().replace(" ","%20");
        STATE = ((Spinner)findViewById(R.id.state_profile)).getSelectedItem().toString().trim().replace(" ","%20");
        BLOODTYPE = ((Spinner)findViewById(R.id.bloodtype_profile)).getSelectedItem().toString().trim().replace(" ","%20").replace("+", "%2B");
        DOB = ((TextView)findViewById(R.id.dob_profile)).getText().toString().trim().replace(" ","%20");
        LDD = ((TextView)findViewById(R.id.lastdonation_profile)).getText().toString().trim().replace(" ","%20");

        if(NAME.equals("") || PASSWORD.equals("") || PHONE.equals("") || EMAIL.equals("") || ADD1.equals("") || ADD2.equals("") || CITY.equals("") || PINCODE.equals("") || STATE.equals("Select%20Your%20State") || BLOODTYPE.equals("Select%20Blood%20Type") || DOB.equals("Set%20Date%20of%20Birth") || LDD.equals("Set%20Last%20Donation%20Date")){
            Toast.makeText(MainActivity.this, "Don't leave the fields empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(PHONE.length()!=10){
            Toast.makeText(MainActivity.this, "Enter a valid Phone Number!", Toast.LENGTH_LONG).show();
        }
        if(!isValidEmail(EMAIL)){
            Toast.makeText(MainActivity.this, "Enter a valid Email ID!", Toast.LENGTH_LONG).show();
            return;
        }
        if(PINCODE.length()!=6){
            Toast.makeText(MainActivity.this, "Enter a valid Pincode!", Toast.LENGTH_LONG).show();
        }

        if(checkConnection()){
            pdialog = ProgressDialog.show(this, null, "Please wait...", true);
            db.execSQL("UPDATE session SET username="+USERNAME+" AND name="+NAME+" AND dob="+DOB+" AND address1="+ADD1+" AND address2="+ADD2+" AND city="+CITY+" AND pincode="+PINCODE+" AND state="+STATE+" AND phone="+PHONE+" AND email="+EMAIL+" AND lastdonated="+LDD+" AND bloodtype="+BLOODTYPE+";");
            String get_params = "profile_name="+NAME+"&profile_phone="+PHONE+"&profile_email="+EMAIL+"&profile_address1="+ADD1+
                    "&profile_address2="+ADD2+"&profile_city="+CITY+"&profile_state="+STATE+"&profile_pincode="+PINCODE+
                    "&profile_bloodtype="+BLOODTYPE+"&profile_dob="+DOB+"&profile_lastdonated="+LDD+"&profile_username="+USERNAME+
                    "&profile_password="+PASSWORD;
            UpdateProfileTask task = new UpdateProfileTask(this);
            task.execute(get_params);
        }else{
            Toast.makeText(this, "Check Your Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return target!=null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean checkConnection(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo == null ) return false;
        else return true;
    }

    @Override
    public void onFetchComplete() {
        pdialog.dismiss();
        ((EditText)findViewById(R.id.pincode)).setText("");
        ((EditText)findViewById(R.id.city)).setText("");
        ((EditText)findViewById(R.id.address2)).setText("");
        ((EditText)findViewById(R.id.address1)).setText("");
        ((EditText)findViewById(R.id.email)).setText("");
        ((EditText)findViewById(R.id.phone)).setText("");
        ((EditText)findViewById(R.id.password)).setText("");
        ((EditText)findViewById(R.id.username)).setText("");
        ((EditText)findViewById(R.id.name)).setText("");
        ((Spinner)findViewById(R.id.state)).setSelection(0);
        ((Spinner)findViewById(R.id.bloodtype)).setSelection(0);
        ((TextView)findViewById(R.id.dob)).setText("Set Date of Birth");
        ((TextView)findViewById(R.id.lastdonation)).setText("Set Last Donation Date");
        findViewById(R.id.regScrollView).scrollTo(0,0);
        findViewById(R.id.name).requestFocus();
        PINCODE=CITY=ADD1=ADD2=EMAIL=PASSWORD=PHONE=PINCODE=USERNAME=NAME="";
        STATE = "Select%20Your%20State";
        BLOODTYPE = "Select%20Blood%20Type";
        LDD = "Set%20Last%20Donation%20Date";
        DOB = "Set%20Date%20of%20Birth";
        Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchComplete(String s) {
        if(s.contains("correctcredentials")){
            String[] parts = s.split("_");
            db.execSQL("DELETE FROM session;");
            db.execSQL("INSERT INTO session VALUES('" + parts[1] + "','" + parts[2] + "','" + parts[3] + "','" + parts[4] + "','" + parts[5] + "','" + parts[6] + "','" + parts[7] + "','" + parts[8] + "','" + parts[9] + "','" + parts[10] + "','" + parts[11] + "','" + parts[12] + "');");
            setProfileDetails();
            pdialog.dismiss();
            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
        }else if(s.contains("update")){
            pdialog.dismiss();
            Toast.makeText(MainActivity.this, "Profile Updated Successfully!", Toast.LENGTH_LONG).show();
        }else if(s.contains("checkusername")){
            String get_params = "reg_name="+NAME+"&reg_phone="+PHONE+"&reg_email="+EMAIL+"&reg_address1="+ADD1+
                    "&reg_address2="+ADD2+"&reg_city="+CITY+"&reg_state="+STATE+"&reg_pincode="+PINCODE+
                    "&reg_bloodtype="+BLOODTYPE+"&reg_dob="+DOB+"&reg_lastdonated="+LDD+"&reg_username="+USERNAME+
                    "&reg_password="+PASSWORD;
            RegisterTask task = new RegisterTask(this);
            task.execute(get_params);
        }else if(s.startsWith("donors")){
            Toast.makeText(MainActivity.this, "Scroll down!", Toast.LENGTH_SHORT).show();
            String[] donors = s.split("=");

            LinearLayout ll = (LinearLayout)FindDonorFragment.rootView.findViewById(R.id.donorsLinearLayout);
            ll.removeAllViews();
            TextView textView = new TextView(this);
            textView.setText("Here are the donors who have registered with us!");
            textView.setTextSize(25);
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(null, Typeface.ITALIC);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) getResources().getDimension(R.dimen.dp20), (int) getResources().getDimension(R.dimen.dp20), (int) getResources().getDimension(R.dimen.dp20), (int) getResources().getDimension(R.dimen.dp20));
            textView.setLayoutParams(params);
            ll.addView(textView);

            int sno = 0;
            for(String donor:donors) {
                if (sno == 0) {
                    sno++;
                    continue;
                }
                final String[] parts = donor.split("!");

                try {
                    textView = new TextView(this);
                    textView.setText("(" + String.valueOf(sno) + ")");
                    sno++;
                    textView.setTextSize(23);
                    textView.setGravity(Gravity.CENTER);
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins((int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp20), (int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10));
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText(parts[0]);//Name
                    textView.setTextSize(24);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText(parts[1]);//Address1
                    textView.setTextSize(19);
                    textView.setGravity(Gravity.CENTER);
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText(parts[2]);//Address2
                    textView.setTextSize(19);
                    textView.setGravity(Gravity.CENTER);
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText(parts[3] + ", " + parts[4] + ", " + parts[5]);//City, State, Pincode
                    textView.setTextSize(19);
                    textView.setGravity(Gravity.CENTER);
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText(parts[6]);//Email
                    textView.setTextSize(19);
                    textView.setTextColor(getResources().getColor(R.color.primaryDark));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{parts[6]});
                            startActivity(Intent.createChooser(emailIntent, "Send mail via..."));
                        }
                    });
                    textView.setGravity(Gravity.CENTER);
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins((int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10));
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText(parts[7]);//Phone
                    textView.setTextSize(19);
                    textView.setTextColor(getResources().getColor(R.color.primaryDark));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = "tel:0" + parts[7];
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                            startActivity(intent);
                        }
                    });
                    textView.setGravity(Gravity.CENTER);
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText("Blood Type - " + parts[8]);//Bloodtype
                    textView.setTextSize(19);
                    textView.setGravity(Gravity.CENTER);
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins((int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10));
                    textView.setLayoutParams(params);
                    ll.addView(textView);

                    textView = new TextView(this);
                    textView.setText("Last Donated on " + parts[9]);//LastDonatedDate
                    textView.setTextSize(19);
                    textView.setGravity(Gravity.CENTER);
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    ll.addView(textView);
                }catch (Exception e){}

                Button button = new Button(this);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkConnection()) {
                            String get_params = "phone=" + parts[7];
                            ReportNumberTask task = new ReportNumberTask();
                            task.execute(get_params);
                            Toast.makeText(MainActivity.this, "Phone Number Reported. Thank you!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Please check your Internet Connection!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                button.setText("Report as Invalid");
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button.setTextColor(Color.WHITE);
                }else{
                    button.setTextColor(Color.parseColor("#444444"));
                }
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins((int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10), (int) getResources().getDimension(R.dimen.dp10));
                button.setLayoutParams(params);
                ll.addView(button);
            }
            pdialog.dismiss();
        }else if(s.startsWith("nodonors")){
            LinearLayout ll = (LinearLayout)FindDonorFragment.rootView.findViewById(R.id.donorsLinearLayout);
            TextView textView = new TextView(this);
            textView.setText("Sorry, no donor has registered with us as per your request.");
            textView.setTextSize(25);
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(null, Typeface.ITALIC);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) getResources().getDimension(R.dimen.dp20), (int) getResources().getDimension(R.dimen.dp20), (int) getResources().getDimension(R.dimen.dp20), (int) getResources().getDimension(R.dimen.dp20));
            textView.setLayoutParams(params);
            ll.addView(textView);
            pdialog.dismiss();
        }
    }

    @Override
    public void onFetchFailure() {
        pdialog.dismiss();
        Toast.makeText(MainActivity.this, "Please try again later!", Toast.LENGTH_SHORT).show();
        Log.e("ON FETCH FAILURE", "asdasdasdasdasd");
    }

    @Override
    public void onFetchFailure(String s) {
        if(s.equals("wrong")){
            pdialog.dismiss();
            Toast.makeText(MainActivity.this, "Wrong credentials!", Toast.LENGTH_SHORT).show();
            Log.e("ON FETCH FAILURE", "String s");
        }else if(s.contains("checkusername")){
            pdialog.dismiss();
            Toast.makeText(MainActivity.this, "Username already taken!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setProfileDetails(){
        mTitle = "Update Donor Profile";
        invalidateOptionsMenu();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, ProfileFragment.newInstance(6)).commit();
    }

}