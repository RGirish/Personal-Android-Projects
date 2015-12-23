package com.example.pin;

import java.util.Locale;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class New extends Activity
{
	SQLiteDatabase db;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.newplace);
	}
	
	public void addplace(View v)
	{
		db=openOrCreateDatabase("PINCODES.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		EditText pl=(EditText)findViewById(R.id.place);
		EditText pi=(EditText)findViewById(R.id.pin);
		
		try
		{
			db.execSQL("CREATE TABLE alldata(name TEXT, pin TEXT);");
		}
		catch(Exception e){}		
		
		db.execSQL("INSERT INTO alldata VALUES('"+pl.getText().toString()+"','"+pi.getText().toString()+"');");
		
		super.onBackPressed();
	}
	
}
