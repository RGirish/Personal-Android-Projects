package com.example.pin;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showcontent();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		showcontent();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add("Add a place");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem i)
	{
		startActivity(new Intent(this, New.class));
		return true;
	}
	
	public void showcontent()
	{
		db=openOrCreateDatabase("PINCODES.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		Cursor c;
		LinearLayout ll=(LinearLayout)findViewById(R.id.ll);
		ll.removeAllViews();
		try
		{
		c=db.rawQuery("SELECT * FROM alldata ORDER BY name",null);
		c.moveToFirst();
		}
		catch(Exception e)
		{
			TextView tv=new TextView(this);			
			tv.setText("No places added to database!\nPress Menu to start adding new places!");
			tv.setPadding(15, 7, 0, 7);
			ll.addView(tv);
			return;
		}
		
		
		while(true)
		{
			TextView tv=new TextView(this);
			ll=(LinearLayout)findViewById(R.id.ll);
			tv.setText(c.getString(0)+" - "+c.getString(1));
			tv.setPadding(15, 7, 0, 7);
			ll.addView(tv);
			
			if(!c.isLast())
			{
				c.moveToNext();
			}
			else
			{
				break;
			}
		}
		
	}

}
