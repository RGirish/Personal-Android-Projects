package com.example.xnos;

import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

public class HighScores extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
		function();
	}
	
	@SuppressWarnings("deprecation")
	public void function()
	{
		Button b1=(Button)findViewById(R.id.buttonone);
		Button b2=(Button)findViewById(R.id.buttontwo);
		Button b3=(Button)findViewById(R.id.buttonthree);
		Button b4=(Button)findViewById(R.id.button2);
		Button b5=(Button)findViewById(R.id.button3);
		Button b6=(Button)findViewById(R.id.button4);
		

		SQLiteDatabase db;
		db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        String q="SELECT * FROM all_data ORDER BY score DESC";
        Cursor c=db.rawQuery(q, null);
        c.moveToFirst();
        b1.setText(c.getString(1));
        b4.setText(Long.toString(c.getInt(6)));
        if(c.isLast()==false)
        {
        c.moveToNext();
        b2.setText(c.getString(1));
        b5.setText(Long.toString(c.getInt(6)));
        if(c.isLast()==false)
        {
        c.moveToNext();
        b3.setText(c.getString(1));
        b6.setText(Long.toString(c.getInt(6)));
        }
        }
        }
	
}
