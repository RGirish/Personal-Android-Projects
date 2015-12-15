package com.example.xnos;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class NewPlayer extends Activity
{
	
	public String GENDER="M";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newplayer);
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("SdCardPath")
	public void onclicksubmit(View view) 
	{
		
		
		EditText t1=(EditText)findViewById(R.id.editText1);
		EditText t2=(EditText)findViewById(R.id.editText2);
		RadioButton rb1=(RadioButton)findViewById(R.id.radioButton1);
		RadioButton rb2=(RadioButton)findViewById(R.id.radioButton2);
		
		
		if((rb1.isChecked()==false&&rb2.isChecked()==false))
		{
			Toast.makeText(this, "Sorry, this game is not for people who are neither male, nor female! :)", Toast.LENGTH_LONG).show();
			return;
		}
		
		SQLiteDatabase db;
        
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        final String CREATE_TABLE ="CREATE TABLE all_data ("+ "name TEXT,"+ "nick_name TEXT UNIQUE,"+ "gender TEXT,"+ "wins INTEGER,"+ "losses INTEGER,"+ "draws INTEGER,"+"score INTEGER);";
        try
        {
        	db.execSQL(CREATE_TABLE);
        }
        catch(SQLiteException e)
        {
        	
        }
        
        
        	String NAME=(t1.getText()).toString();
        	String NICK_NAME=(t2.getText()).toString();
        	final String INSERT_ROW="INSERT INTO all_data VALUES('"+NAME+"','"+NICK_NAME+"','"+GENDER+"',0,0,0,0);";
        	try
        	{
        		db.execSQL(INSERT_ROW);
        	}
        	catch(SQLiteConstraintException e)
        	{
        		Toast.makeText(getBaseContext(), "OOPS! Nick name already taken! :( Give us another one, just for u! :) ", Toast.LENGTH_LONG).show();
        		return;
        		
        	}
        
        db.close();
		Toast.makeText(getBaseContext(), "Submitted :)", Toast.LENGTH_LONG).show();
		Intent intent=new Intent(this, MainMenu.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		//Intent.FLAG_ACTIVITY_SINGLE_TOP | 
	}
	
	public void onclickrb1(View view)
	{
		RadioButton rb1=(RadioButton)findViewById(R.id.radioButton1);
		RadioButton rb2=(RadioButton)findViewById(R.id.radioButton2);
		rb2.setChecked(false);
		rb1.setChecked(true);
		GENDER="M";
	}
	
	public void onclickrb2(View view)
	{
		RadioButton rb1=(RadioButton)findViewById(R.id.radioButton1);
		RadioButton rb2=(RadioButton)findViewById(R.id.radioButton2);
		rb1.setChecked(false);
		rb2.setChecked(true);
		GENDER="F";
	}
	
	
}
