package com.example.rain;

import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class SeePost extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seepost);
		func();
	}
	
	@SuppressWarnings("deprecation")
	public void func()
	{
		TextView t=(TextView)findViewById(R.id.seepost);
		SQLiteDatabase db;
        
        db = openOrCreateDatabase("TEXT.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        String q="SELECT * FROM mytext";
        Cursor c=db.rawQuery(q, null);
        c.moveToFirst();
        String cp=c.getString(0);
        
        t.setText(cp);
	}
	
}
