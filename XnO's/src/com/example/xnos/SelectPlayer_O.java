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

public class SelectPlayer_O extends Activity
{
	Context context;
	String PLAYER_FOR_X=null;
	String PLAYER_FOR_O=null;
	SQLiteDatabase db;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.player_for_o);
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
		player_for_o();
	}
	
	
	public void player_for_o()
	{
		
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
        	
        }
        
        
        String findx="SELECT * FROM player_for_x_o";
        Cursor ccc=db.rawQuery(findx, null);
        ccc.moveToFirst();
        PLAYER_FOR_X=ccc.getString(0);
		       
        do
        {
        	final String nn=c1.getString(1);
        	if(nn.equalsIgnoreCase(PLAYER_FOR_X)==false)
        	{
        	LinearLayout ll=(LinearLayout)findViewById(R.id.player_o_ll);
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
					PLAYER_FOR_O=nn;
			        
			        final String CREATE_TABLE ="CREATE TABLE player_for_x_o (name_x TEXT,name_o TEXT);";
			        final String INSERT_DUMMY_ROW="INSERT INTO player_for_x_o VALUES('Anonymous','Anonymous');";
			        
			        final String UPDATE_O="UPDATE player_for_x_o SET name_o='"+PLAYER_FOR_O+"';";
			        try
			        {
			        	db.execSQL(CREATE_TABLE);			        	
			        	db.execSQL(INSERT_DUMMY_ROW);
			        }
			        catch(SQLiteException e)
			        {
			        	
			        }
			        db.execSQL(UPDATE_O);
			        
			        
			        Intent intent= new Intent(context, GameScreen.class);
					startActivity(intent);
			        
				}
        		
        	});
        	ll.addView(child);
        	}
        	
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
