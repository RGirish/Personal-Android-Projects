package com.example.xnos;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PlayerProfiles extends Activity 
{
	
	public String GENDER=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_profiles);
	}
	
	
	public void onclick_newplayer(View view)
	{
		Intent intent=new Intent(this, NewPlayer.class);
		startActivity(intent);
		//setContentView(R.layout.activity_newplayer);
	}
	
	
	public void onclicknewgame(View view)
	{
		Intent intent= new Intent(this, GameScreen.class);
		startActivity(intent);
	}
	
	
	@SuppressWarnings("deprecation")
	public void onclickcurrentplayers(View view)
	{
		
		SQLiteDatabase db;
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        Cursor c1;
        Cursor c2;
        String q="SELECT * FROM all_data";
        try
        {
        	c1=db.rawQuery(q, null);
        	c1.moveToFirst();
        	c2=db.rawQuery(q, null);
        	c2.moveToLast();
        }
        catch(Exception e)
        {
        	Toast.makeText(this, "No one has played yet, be the first one! :)", Toast.LENGTH_LONG).show();
        	db.close();
        	return;
        }
        		
		Intent intent= new Intent(this, CurrentPlayers.class);
		startActivity(intent);
	}
	
	
	public void onclickplayerprofiles(View view)
	{
		Intent intent= new Intent(this, PlayerProfiles.class);
		startActivity(intent);
	}

	public void onclickquit(View view)
	{
		for(int i=0;i<4000;++i);
		System.exit(0);
	}	
	
	
	
	
	
}
