package com.example.xnos;

import java.util.Locale;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ColorSchemes extends Activity
{
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colorschemes);
	
	
	
	
		
		
		SQLiteDatabase db;
        
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        final String CREATE_TABLE ="CREATE TABLE color_scheme ("+ "bc TEXT,"+ "fc TEXT);";
        final String INSERT_DUMMY_ROW="INSERT INTO color_scheme VALUES('b8','b16');";
        try
        {
        	db.execSQL(CREATE_TABLE);
        	db.execSQL(INSERT_DUMMY_ROW);
        }
        catch(SQLiteException e)
        {
        	
        }
        
        Button cc=(Button)findViewById(R.id.buttoncc);
		
        
        Cursor c=db.rawQuery("SELECT * FROM color_scheme", null);
        c.moveToFirst();
        String bc=null;
        String fc=null;
        bc=c.getString(0);
        fc=c.getString(1);
        if(bc.equals("b1"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b1));
        }
        else if(bc.equals("b2"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b2));
        }
        else if(bc.equals("b3"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b3));
        }
        else if(bc.equals("b4"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b4));
        }
        else if(bc.equals("b5"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b5));        	
        }
        else if(bc.equals("b6"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b6));
        }
        else if(bc.equals("b7"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b7));
        }
        else if(bc.equals("b8"))
        {
        	cc.setBackgroundColor(getResources().getColor(R.color.b8));
        }
        
        
        
        if(fc.equals("b9"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b9));
        }
        else if(fc.equals("b10"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b10));
        }
        else if(fc.equals("b11"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b11));
        }
        else if(fc.equals("b12"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b12));
        }
        else if(fc.equals("b13"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b13));        	
        }
        else if(fc.equals("b14"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b14));
        }
        else if(fc.equals("b15"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b15));
        }
        else if(fc.equals("b16"))
        {
        	cc.setTextColor(getResources().getColor(R.color.b16));
        }
		
		
	
        db.close();
	
	
	
	}
	
	@SuppressWarnings("deprecation")
	public void onclickcolor(View view)
	{
		
		SQLiteDatabase db;
        
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        final String CREATE_TABLE ="CREATE TABLE color_scheme ("+ "bc TEXT,"+ "fc TEXT);";
        final String INSERT_DUMMY_ROW="INSERT INTO color_scheme VALUES('b8','b16');";
        try
        {
        	db.execSQL(CREATE_TABLE);
        	db.execSQL(INSERT_DUMMY_ROW);
        }
        catch(SQLiteException e)
        {
        	
        }
        
        Button cc=(Button)findViewById(R.id.buttoncc);
		
        String query=null;
		int id=view.getId();
		switch(id)
		{
			case R.id.button1:
				cc.setBackgroundColor(getResources().getColor(R.color.b1));
				query="UPDATE color_scheme SET bc='b1';";
				break;
			case R.id.button2:
				cc.setBackgroundColor(getResources().getColor(R.color.b2));
				query="UPDATE color_scheme SET bc='b2';";
				break;
			case R.id.button3:
				cc.setBackgroundColor(getResources().getColor(R.color.b3));
				query="UPDATE color_scheme SET bc='b3';";
				break;
			case R.id.button4:
				cc.setBackgroundColor(getResources().getColor(R.color.b4));
				query="UPDATE color_scheme SET bc='b4';";
				break;
			case R.id.button5:
				cc.setBackgroundColor(getResources().getColor(R.color.b5));
				query="UPDATE color_scheme SET bc='b5';";
				break;
			case R.id.button6:
				cc.setBackgroundColor(getResources().getColor(R.color.b6));
				query="UPDATE color_scheme SET bc='b6';";
				break;
			case R.id.button7:
				cc.setBackgroundColor(getResources().getColor(R.color.b7));
				query="UPDATE color_scheme SET bc='b7';";
				break;
			case R.id.button8:
				cc.setBackgroundColor(getResources().getColor(R.color.b8));
				query="UPDATE color_scheme SET bc='b8';";
				break;
			case R.id.button9:
				cc.setTextColor(getResources().getColor(R.color.b9));
				query="UPDATE color_scheme SET fc='b9';";
				break;
			case R.id.button10:
				cc.setTextColor(getResources().getColor(R.color.b10));
				query="UPDATE color_scheme SET fc='b10';";
				break;
			case R.id.button11:
				cc.setTextColor(getResources().getColor(R.color.b11));
				query="UPDATE color_scheme SET fc='b11';";
				break;
			case R.id.button12:
				cc.setTextColor(getResources().getColor(R.color.b12));
				query="UPDATE color_scheme SET fc='b12';";
				break;
			case R.id.button13:
				cc.setTextColor(getResources().getColor(R.color.b13));
				query="UPDATE color_scheme SET fc='b13';";
				break;
			case R.id.button14:
				cc.setTextColor(getResources().getColor(R.color.b14));
				query="UPDATE color_scheme SET fc='b14';";
				break;
			case R.id.button15:
				cc.setTextColor(getResources().getColor(R.color.b15));
				query="UPDATE color_scheme SET fc='b15';";
				break;
			case R.id.button16:
				cc.setTextColor(getResources().getColor(R.color.b16));
				query="UPDATE color_scheme SET fc='b16';";
				break;
		}
			
		db.execSQL(query);
		db.close();
		
		
		
		
	}
	
}
