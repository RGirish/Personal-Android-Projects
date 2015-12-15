package com.example.xnos;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}

	public void onBackPressed()
	{
		finish();
		System.exit(0);
	}
	
	@SuppressWarnings("deprecation")
	public void onclicknewgame(View view)
	{
		SQLiteDatabase db;
		db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);
		
		Cursor c1 = null;
        String q="SELECT * FROM all_data"; 
        try
        {
        	c1=db.rawQuery(q, null);
        	c1.moveToFirst();
        }
        catch(Exception e)
        {
        	Toast.makeText(this, "You have to create two profiles to play this 2-player game! :)", Toast.LENGTH_LONG).show();
       		return;
        }
        if(c1.isLast())
        {
        	Toast.makeText(this, "You have to create atleast two profiles to play a 2-player game!", Toast.LENGTH_LONG).show();
       		return;
        }
        db.close();
		Intent intent= new Intent(this, SelectPlayer.class);
		startActivity(intent);
	}
	
	public void onclickoptions(View view)
	{
		Intent intent= new Intent(this, Options.class);
		startActivity(intent);
	}
	
	public void onclickplayerprofiles(View view)
	{
		Intent intent= new Intent(this, PlayerProfiles.class);
		startActivity(intent);
	}
	
	@SuppressWarnings("deprecation")
	public void onclickhighscore(View view)
	{
		SQLiteDatabase db;
		db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);
		
		Cursor c1 = null;
        String q="SELECT * FROM all_data"; 
        try
        {
        	c1=db.rawQuery(q, null);
        	c1.moveToFirst();
        }
        catch(Exception e)
        {
        	Toast.makeText(this, "No one has played yet, be the first one! :)", Toast.LENGTH_LONG).show();
       		return;
        }
        db.close();
		Intent intent= new Intent(this, HighScores.class);
		startActivity(intent);
	}

	public void onclickquit(View view)
	{
		finish();
		System.exit(0);
	}	
}
