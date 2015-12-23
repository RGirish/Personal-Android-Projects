package com.example.dbapp15thmay;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.Locale;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		SQLiteDatabase db;
        
        db = openOrCreateDatabase("TestingData.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        final String CREATE_TABLE_COUNTRIES ="CREATE TABLE tbl_countries1 ("+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "country_name TEXT);";
        final String CREATE_TABLE_STATES ="CREATE TABLE tbl_states1 ("+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "state_name TEXT,"+ "country_id INTEGER NOT NULL CONSTRAINT "+ "contry_id REFERENCES tbl_contries(id) "+ "ON DELETE CASCADE);";     
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_STATES);
        
        ContentValues countryvalues = new ContentValues();
        countryvalues.put("country_name", "US");
        long countryId = db.insert("tbl_countries1", null, countryvalues);
        
        ContentValues stateValues = new ContentValues();
        stateValues.put("state_name", "Texas");
        stateValues.put("country_id", Long.toString(countryId));
        
        try {
            db.insertOrThrow("tbl_states1", null, stateValues);
        } catch (Exception e) {
            //catch code
        }
        
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
