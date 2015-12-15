package com.example.xnos;

import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SelectPlayer extends Activity
{
	Context context;
	String PLAYER_FOR_X=null;
	String PLAYER_FOR_O=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.player_for_x);
		player_for_x();
	}
	
	@SuppressWarnings("deprecation")
	public void player_for_x()
	{
		SQLiteDatabase db;
		db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);
		
		Cursor c1 = null;
        Cursor c2 = null;
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
        	Toast.makeText(this, "You have to create a profile to play! :)", Toast.LENGTH_LONG).show();
        	finish();
        	Intent intent= new Intent(this, MainMenu.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			startActivity(intent);
			return;
        }
		       
        do
        {
        	final String nn=c1.getString(1);
        	LinearLayout ll=(LinearLayout)findViewById(R.id.player_x_ll);
        	Button child=new Button(this);
        	child.setBackgroundColor(getResources().getColor(R.color.white));
        	child.setTextColor(getResources().getColor(R.color.b6));
        	child.setText(nn);
        	child.setTextSize(25);
        	child.setOnClickListener(new OnClickListener()
        	{

				@Override
				public void onClick(View a) 
				{
					PLAYER_FOR_X=nn;
					
					SQLiteDatabase db;
			        
			        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
			        db.setVersion(1);
			        db.setLocale(Locale.getDefault());
			        db.setLockingEnabled(true);
			        
			        final String CREATE_TABLE ="CREATE TABLE player_for_x_o (name_x TEXT,name_o TEXT);";
			        final String INSERT_DUMMY_ROW="INSERT INTO player_for_x_o VALUES('Anonymous','Anonymous');";
			        final String UPDATE_X="UPDATE player_for_x_o SET name_x='"+PLAYER_FOR_X+"';";
			        try
			        {
			        	db.execSQL(CREATE_TABLE);			        	
			        	db.execSQL(INSERT_DUMMY_ROW);
			        }
			        catch(SQLiteException e)
			        {
			        	
			        }
			        db.execSQL(UPDATE_X);
			        db.close();
			        
			        Intent intent= new Intent(context, SelectPlayer_O.class);
					startActivity(intent);
					
				}
        		
        	});
        	ll.addView(child);
        	if(!c1.isLast())
        	{
        		c1.moveToNext();
        	}
        	else
        	{
        		break;
        	}
        }while(c1!=c2);
        db.close();
        
	}
	
}
