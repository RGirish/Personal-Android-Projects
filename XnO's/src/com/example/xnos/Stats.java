package com.example.xnos;

import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

public class Stats extends Activity 
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
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
        
        String m1,m2,m3,m4,m5,q;
        
        q="SELECT * FROM stats_forx";
        Cursor c=db.rawQuery(q, null);
        c.moveToFirst();
        m1=c.getString(0);
        m2=c.getString(1);
        m3=c.getString(2);
        m4=c.getString(3);
        m5=c.getString(4);
        
        
        Button g1=(Button)findViewById(R.id.buttong1);
        Button g2=(Button)findViewById(R.id.buttong2);
        Button g3=(Button)findViewById(R.id.buttong3);
        Button g4=(Button)findViewById(R.id.buttong4);
        Button g5=(Button)findViewById(R.id.buttong5);
        
        Button o1=(Button)findViewById(R.id.buttono1);
        Button o2=(Button)findViewById(R.id.buttono2);
        Button o3=(Button)findViewById(R.id.buttono3);
        Button o4=(Button)findViewById(R.id.buttono4);
        Button o5=(Button)findViewById(R.id.buttono5);
        
        Button r1=(Button)findViewById(R.id.buttonr1);
        Button r2=(Button)findViewById(R.id.buttonr2);
        Button r3=(Button)findViewById(R.id.buttonr3);
        Button r4=(Button)findViewById(R.id.buttonr4);
        Button r5=(Button)findViewById(R.id.buttonr5);
        

        Button p1n=(Button)findViewById(R.id.aaa);
        Button p2n=(Button)findViewById(R.id.aaaa);
        Button p1s=(Button)findViewById(R.id.player1score);
        Button p2s=(Button)findViewById(R.id.player2score);
        Button p1n2=(Button)findViewById(R.id.player1);
        Button p2n2=(Button)findViewById(R.id.player2);
        
        
        
        String qq="SELECT * FROM player_for_x_o";
        
        Cursor players=db.rawQuery(qq, null);
        players.moveToFirst();
        String NAME_X=players.getString(0);
        String NAME_O=players.getString(1);
        
        
        String q1="SELECT * FROM all_data WHERE nick_name='"+NAME_X+"'";
        String q2="SELECT * FROM all_data WHERE nick_name='"+NAME_O+"'";
        Cursor c1=db.rawQuery(q1, null);
        Cursor c2=db.rawQuery(q2, null);
        c1.moveToFirst();
        c2.moveToFirst();
        
        int score_x,score_o;
        
        score_x=c1.getInt(6);
        score_o=c2.getInt(6);
        
        
        p1n.setText(NAME_X);
        p2n.setText(NAME_O);
        p1n2.setText(NAME_X);
        p2n2.setText(NAME_O);
        
        p1s.setText(Long.toString(score_x));
        p2s.setText(Long.toString(score_o));
        
        
        if(m1.equals("w"))
        {
        	g1.setBackgroundColor(getResources().getColor(R.color.green));
        	r1.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(m1.equals("l"))
        {
        	g1.setBackgroundColor(getResources().getColor(R.color.red));
        	r1.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if(m1.equals("d"))
        {
        	o1.setBackgroundColor(getResources().getColor(R.color.b11));
        }
        
        
        if(m2.equals("w"))
        {
        	g2.setBackgroundColor(getResources().getColor(R.color.green));
        	r2.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(m2.equals("l"))
        {
        	g2.setBackgroundColor(getResources().getColor(R.color.red));
        	r2.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if(m2.equals("d"))
        {
        	o2.setBackgroundColor(getResources().getColor(R.color.b11));
        }
        
        
        if(m3.equals("w"))
        {
        	g3.setBackgroundColor(getResources().getColor(R.color.green));
        	r3.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(m3.equals("l"))
        {
        	g3.setBackgroundColor(getResources().getColor(R.color.red));
        	r3.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if(m3.equals("d"))
        {
        	o3.setBackgroundColor(getResources().getColor(R.color.b11));
        }
        
        
        if(m4.equals("w"))
        {
        	g4.setBackgroundColor(getResources().getColor(R.color.green));
        	r4.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(m4.equals("l"))
        {
        	g4.setBackgroundColor(getResources().getColor(R.color.red));
        	r4.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if(m4.equals("d"))
        {
        	o4.setBackgroundColor(getResources().getColor(R.color.b11));
        }
        
        
        if(m5.equals("w"))
        {
        	g5.setBackgroundColor(getResources().getColor(R.color.green));
        	r5.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(m5.equals("l"))
        {
        	g5.setBackgroundColor(getResources().getColor(R.color.red));
        	r5.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if(m5.equals("d"))
        {
        	o5.setBackgroundColor(getResources().getColor(R.color.b11));
        }
    
        db.close();
	}
	
	
}
