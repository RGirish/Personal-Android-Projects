package com.example.xnos;

import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Options extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
	}
	
	public void onclickcolorschemes(View view)
	{
		Intent intent=new Intent(this, ColorSchemes.class);
		startActivity(intent);
	}
	@SuppressWarnings({ "deprecation", "unused" })
	public void onclickcleardata(View view)
	{
		SQLiteDatabase db;
        
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        
        final String q="SELECT * FROM all_data";
        try
        {
        	Cursor c=db.rawQuery(q, null);
        }
        catch(Exception e)
        {
        	Toast.makeText(getBaseContext(), "Already Erased! :)", Toast.LENGTH_SHORT).show();
        	return;
        }
        
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setMessage("Do You Really Wanna Erase All Player Data?!");
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
		new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
		
			SQLiteDatabase db;
	        
	        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	        db.setVersion(1);
	        db.setLocale(Locale.getDefault());
	        db.setLockingEnabled(true);
	        
	        final String trunc="DROP TABLE all_data;";
			try
			{
				db.execSQL(trunc);
				Toast.makeText(getBaseContext(), "Erased! :)", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e)
			{
				
			}
	        db.close();
		}
		});
		
		
		
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
			dialog.dismiss();
		}
		});
		
		dialog.show();
	}
	
}
