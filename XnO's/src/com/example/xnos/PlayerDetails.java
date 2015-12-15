package com.example.xnos;

import java.util.Locale;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerDetails extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_details);
		function();
	}
	
	
	@SuppressWarnings("deprecation")
	public void function()
	{
		TextView n=(TextView)findViewById(R.id.textViewname);
		TextView nn=(TextView)findViewById(R.id.textViewnickname);
		TextView s=(TextView)findViewById(R.id.textViewscore);
		TextView w=(TextView)findViewById(R.id.textViewwins);
		TextView l=(TextView)findViewById(R.id.textViewlosses);
		TextView d=(TextView)findViewById(R.id.textViewdraws);
		SQLiteDatabase db;
        
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        String q="SELECT * FROM currentplayer";
        Cursor c=db.rawQuery(q, null);
        c.moveToFirst();
        String cp=c.getString(0);
        String qq="SELECT * FROM all_data WHERE nick_name='"+cp+"'";
        Cursor cad=db.rawQuery(qq, null);
        cad.moveToFirst();
        long numb;
        
        n.setText(cad.getString(0));//name
        
        nn.setText(cad.getString(1));//nickname
        
        numb=cad.getInt(3);//wins
        w.setText(Long.toString(numb));
        
        numb=cad.getInt(4);//losses
        l.setText(Long.toString(numb));
        
        numb=cad.getInt(5);//draws
        d.setText(Long.toString(numb));
        
        numb=cad.getInt(6);//score
        s.setText(Long.toString(numb));
        
        String gender=cad.getString(2);//gender
        ImageView iv=(ImageView)findViewById(R.id.imageViewpd);
        int resid_male=R.drawable.male;
        int resid_female=R.drawable.female;
        if(gender.equals("M"))
        {
        	iv.setImageResource(resid_male);
        }
        else
        {
        	iv.setImageResource(resid_female);
        }
        db.close();
	}
}
