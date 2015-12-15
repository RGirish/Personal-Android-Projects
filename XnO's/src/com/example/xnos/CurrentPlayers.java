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

public class CurrentPlayers extends Activity
{
	Context context=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.activity_current_players);
		function();
	}
	
	@SuppressWarnings("deprecation")
	public void function()
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
        	return;
        }
        
        do
        {
        	final String nn=c1.getString(1);
        	LinearLayout ll=(LinearLayout)findViewById(R.id.cpll);
        	Button child=new Button(this);
        	child.setBackgroundColor(getResources().getColor(R.color.white));
        	child.setTextColor(getResources().getColor(R.color.blue));
        	child.setText(nn);
        	child.setTextSize(25);
        	//child.setPadding(5,5,5,5);
        	child.setOnClickListener(new OnClickListener()
        	{

				@Override
				public void onClick(View a) 
				{
					//Button bb=(Button)findViewById(a.getId());
					String NAME=nn;
					SQLiteDatabase db;
			        
			        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
			        db.setVersion(1);
			        db.setLocale(Locale.getDefault());
			        db.setLockingEnabled(true);
			        
			        final String CREATE_TABLE ="CREATE TABLE currentplayer (name TEXT);";
			        final String INSERT_DUMMY_ROW="INSERT INTO currentplayer VALUES('null');";
			        final String UPDATE_ROW="UPDATE currentplayer SET name='"+NAME+"';";
			        try
			        {
			        	db.execSQL(CREATE_TABLE);			        	
			        	db.execSQL(INSERT_DUMMY_ROW);
			        }
			        catch(SQLiteException e)
			        {
			        	
			        }
			        db.execSQL(UPDATE_ROW);
			        db.close();
			        Intent intent= new Intent(context, PlayerDetails.class);
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
        
        
	}
	
}
