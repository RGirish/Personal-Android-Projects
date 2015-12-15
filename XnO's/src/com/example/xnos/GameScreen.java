package com.example.xnos;

import java.util.Locale;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
@TargetApi(Build.VERSION_CODES.ECLAIR)
public class GameScreen extends Activity
{
	
	int b11=0,b12=0,b13=0,b21=0,b22=0,b23=0,b31=0,b32=0,b33=0,count=0,gameover=0;
	int ctv=0,cb1=0,cb2=0,cb3=0,cb4=0,cb5=0,cb6=0,cb7=0,cb8=0,cb9=0;
	String XrO="X";
	String PLAYER_FOR_X=null,PLAYER_FOR_O=null;
	String who_started=null;
	Context cc;
	SQLiteDatabase db;
	BluetoothAdapter mBluetoothAdapter;
	AllThreads AT;
	
	
	public static final int MESSAGE_TOAST = 1;
	public static final int TOUCH_DOWN = 2;
	public static final int TOUCH_MOVE = 3;
	public static final int TOUCH_UP = 4;
	public static final int JOIN = 5;
	public static final int MESSAGE_READ = 6;
	public static final String TOAST = "toast";
	public static boolean CONNECTION=false;
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu m)
	{
		if(CONNECTION)
		{
			m.add(0, 10, 0, "Create Network");
			m.add(0, 20, 0, "Join Network");
		}
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case JOIN:
			if (resultCode == Activity.RESULT_OK) {
				Log.i("inside activity result", "inside activity result");
				connectDevice(data);
			}
			break;
		}
	}

	private void connectDevice(Intent data) {
		String address = data.getExtras().getString(
				NearbyDevices.EXTRA_DEVICE_ADDRESS);
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		AT.connect(device);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case 10:
				
				if (mBluetoothAdapter.isEnabled()) 
				{
					AT = new AllThreads(mHandler);
					Toast.makeText(cc, "Listening for incoming connections",Toast.LENGTH_LONG).show();
				} 
				else 
				{
					Toast.makeText(cc, "Turn on Bluetooth and make discoverable!",Toast.LENGTH_SHORT).show();
				}
				
				break;
				
			case 20:
				
				if (mBluetoothAdapter.isEnabled()) 
				{
					AT = new AllThreads(mHandler);
					Intent i = new Intent(cc, NearbyDevices.class);
					startActivityForResult(i, JOIN);
				} 
				else 
				{
					Toast.makeText(cc, "Turn on Bluetooth and make discoverable!",Toast.LENGTH_SHORT).show();
				}
				break;
				
			}
		return true;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savehere)
	{
		Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
		Button b3=(Button)findViewById(R.id.Button03);
		Button b4=(Button)findViewById(R.id.Button04);
		Button b5=(Button)findViewById(R.id.Button05);
		Button b6=(Button)findViewById(R.id.Button06);
		Button b7=(Button)findViewById(R.id.Button07);
		Button b8=(Button)findViewById(R.id.Button08);
		Button b9=(Button)findViewById(R.id.Button09);	
		TextView tt=(TextView)findViewById(R.id.textView1);
		
		String tb1=(String) b1.getText();
		String tb2=(String) b2.getText();
		String tb3=(String) b3.getText();
		String tb4=(String) b4.getText();
		String tb5=(String) b5.getText();
		String tb6=(String) b6.getText();
		String tb7=(String) b7.getText();
		String tb8=(String) b8.getText();
		String tb9=(String) b9.getText();
		String tv=(String) tt.getText();
		savehere.putString("tb1", tb1);
		savehere.putString("tb2", tb2);
		savehere.putString("tb3", tb3);
		savehere.putString("tb4", tb4);
		savehere.putString("tb5", tb5);
		savehere.putString("tb6", tb6);
		savehere.putString("tb7", tb7);
		savehere.putString("tb8", tb8);
		savehere.putString("tb9", tb9);
		savehere.putString("tv", tv);

		savehere.putInt("b11", b11);
		savehere.putInt("b12", b12);
		savehere.putInt("b13", b13);
		savehere.putInt("b21", b21);
		savehere.putInt("b22", b22);
		savehere.putInt("b23", b23);
		savehere.putInt("b31", b31);
		savehere.putInt("b32", b32);
		savehere.putInt("b33", b33);
		
		savehere.putInt("cb1", cb1);
		savehere.putInt("cb2", cb2);
		savehere.putInt("cb3", cb3);
		savehere.putInt("cb4", cb4);
		savehere.putInt("cb5", cb5);
		savehere.putInt("cb6", cb6);
		savehere.putInt("cb7", cb7);
		savehere.putInt("cb8", cb8);
		savehere.putInt("cb9", cb9);
		
		savehere.putString("XrO", XrO);
		savehere.putString("PLAYER_FOR_X", PLAYER_FOR_X);
		savehere.putString("PLAYER_FOR_O", PLAYER_FOR_O);
		savehere.putString("who_started", who_started);
		savehere.putInt("gameover", gameover);
		savehere.putInt("count", count);
		super.onSaveInstanceState(savehere);
		
		
		
	
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedState) 
	{
		super.onRestoreInstanceState(savedState);
		b11=savedState.getInt("b11");
		b12=savedState.getInt("b12");
		b13=savedState.getInt("b13");
		b21=savedState.getInt("b21");
		b22=savedState.getInt("b22");
		b23=savedState.getInt("b23");
		b31=savedState.getInt("b31");
		b32=savedState.getInt("b32");
		b33=savedState.getInt("b33");
		
		cb1=savedState.getInt("cb1");
		cb2=savedState.getInt("cb2");
		cb3=savedState.getInt("cb3");
		cb4=savedState.getInt("cb4");
		cb5=savedState.getInt("cb5");
		cb6=savedState.getInt("cb6");
		cb7=savedState.getInt("cb7");
		cb8=savedState.getInt("cb8");
		cb9=savedState.getInt("cb9");
		
		gameover=savedState.getInt("gameover");
		count=savedState.getInt("count");
		XrO=savedState.getString("XrO");
		PLAYER_FOR_O=savedState.getString("PLAYER_FOR_O");
		PLAYER_FOR_X=savedState.getString("PLAYER_FOR_X");
		who_started=savedState.getString("who_started");
		
		Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
		Button b3=(Button)findViewById(R.id.Button03);
		Button b4=(Button)findViewById(R.id.Button04);
		Button b5=(Button)findViewById(R.id.Button05);
		Button b6=(Button)findViewById(R.id.Button06);
		Button b7=(Button)findViewById(R.id.Button07);
		Button b8=(Button)findViewById(R.id.Button08);
		Button b9=(Button)findViewById(R.id.Button09);
		TextView tt=(TextView)findViewById(R.id.textView1);
		
		b1.setText(savedState.getString("tb1"));
		b2.setText(savedState.getString("tb2"));
		b3.setText(savedState.getString("tb3"));
		b4.setText(savedState.getString("tb4"));
		b5.setText(savedState.getString("tb5"));
		b6.setText(savedState.getString("tb6"));
		b7.setText(savedState.getString("tb7"));
		b8.setText(savedState.getString("tb8"));
		b9.setText(savedState.getString("tb9"));
		tt.setText(savedState.getString("tv"));
		
		if(cb1==1) b1.setTextColor(getResources().getColor(R.color.white));
		if(cb2==1) b2.setTextColor(getResources().getColor(R.color.white));
		if(cb3==1) b3.setTextColor(getResources().getColor(R.color.white));
		if(cb4==1) b4.setTextColor(getResources().getColor(R.color.white));
		if(cb5==1) b5.setTextColor(getResources().getColor(R.color.white));
		if(cb6==1) b6.setTextColor(getResources().getColor(R.color.white));
		if(cb7==1) b7.setTextColor(getResources().getColor(R.color.white));
		if(cb8==1) b8.setTextColor(getResources().getColor(R.color.white));
		if(cb9==1) b9.setTextColor(getResources().getColor(R.color.white));
		
		String tvtext=(String) tt.getText();		
		if(tvtext.endsWith("n!")) tt.setTextColor(getResources().getColor(R.color.green));
		
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		cc=this;

		
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) 
		{
			Toast.makeText(this, "Device doesn't support Bluetooth!",Toast.LENGTH_LONG).show();
		} 
		else 
		{
			if (!mBluetoothAdapter.isEnabled()) 
			{
				Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
				startActivity(discoverableIntent);
			}
		}
		
		
		
		
        
        db = openOrCreateDatabase("XNOS_DATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        
        final String CREATE_TABLE_stats ="CREATE TABLE stats_forx ("+ "m1 TEXT,"+ "m2 TEXT,"+ "m3 TEXT,"+"m4 TEXT,"+ "m5 TEXT);"; 
        final String insert="INSERT INTO stats_forx VALUES('x','x','x','x','x');";
        try
        {
        	db.execSQL(CREATE_TABLE_stats);
        	db.execSQL(insert);
        }
        catch(SQLiteException e)
        {
        	
        }
        
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
        
        Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
		Button b3=(Button)findViewById(R.id.Button03);
		Button b4=(Button)findViewById(R.id.Button04);
		Button b5=(Button)findViewById(R.id.Button05);
		Button b6=(Button)findViewById(R.id.Button06);
		Button b7=(Button)findViewById(R.id.Button07);
		Button b8=(Button)findViewById(R.id.Button08);
		Button b9=(Button)findViewById(R.id.Button09);
		Button ba=(Button)findViewById(R.id.ButtonA);
		Button bb=(Button)findViewById(R.id.ButtonB);
		
        
        Cursor c=db.rawQuery("SELECT * FROM color_scheme", null);
        c.moveToFirst();
        String bc=null;
        String fc=null;
        bc=c.getString(0);
        fc=c.getString(1);
        if(bc.equals("b1"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b1));
        	b2.setBackgroundColor(getResources().getColor(R.color.b1));
        	b3.setBackgroundColor(getResources().getColor(R.color.b1));
        	b4.setBackgroundColor(getResources().getColor(R.color.b1));
        	b5.setBackgroundColor(getResources().getColor(R.color.b1));
        	b6.setBackgroundColor(getResources().getColor(R.color.b1));
        	b7.setBackgroundColor(getResources().getColor(R.color.b1));
        	b8.setBackgroundColor(getResources().getColor(R.color.b1));
        	b9.setBackgroundColor(getResources().getColor(R.color.b1));
        	}
        else if(bc.equals("b2"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b2));
        	b2.setBackgroundColor(getResources().getColor(R.color.b2));
        	b3.setBackgroundColor(getResources().getColor(R.color.b2));
        	b4.setBackgroundColor(getResources().getColor(R.color.b2));
        	b5.setBackgroundColor(getResources().getColor(R.color.b2));
        	b6.setBackgroundColor(getResources().getColor(R.color.b2));
        	b7.setBackgroundColor(getResources().getColor(R.color.b2));
        	b8.setBackgroundColor(getResources().getColor(R.color.b2));
        	b9.setBackgroundColor(getResources().getColor(R.color.b2));
        	}
        else if(bc.equals("b3"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b3));
        	b2.setBackgroundColor(getResources().getColor(R.color.b3));
        	b3.setBackgroundColor(getResources().getColor(R.color.b3));
        	b4.setBackgroundColor(getResources().getColor(R.color.b3));
        	b5.setBackgroundColor(getResources().getColor(R.color.b3));
        	b6.setBackgroundColor(getResources().getColor(R.color.b3));
        	b7.setBackgroundColor(getResources().getColor(R.color.b3));
        	b8.setBackgroundColor(getResources().getColor(R.color.b3));
        	b9.setBackgroundColor(getResources().getColor(R.color.b3));
        	        }
        else if(bc.equals("b4"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b4));
        	b2.setBackgroundColor(getResources().getColor(R.color.b4));
        	b3.setBackgroundColor(getResources().getColor(R.color.b4));
        	b4.setBackgroundColor(getResources().getColor(R.color.b4));
        	b5.setBackgroundColor(getResources().getColor(R.color.b4));
        	b6.setBackgroundColor(getResources().getColor(R.color.b4));
        	b7.setBackgroundColor(getResources().getColor(R.color.b4));
        	b8.setBackgroundColor(getResources().getColor(R.color.b4));
        	b9.setBackgroundColor(getResources().getColor(R.color.b4));
        	
        }
        else if(bc.equals("b5"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b5));
        	b2.setBackgroundColor(getResources().getColor(R.color.b5));
        	b3.setBackgroundColor(getResources().getColor(R.color.b5));
        	b4.setBackgroundColor(getResources().getColor(R.color.b5));
        	b5.setBackgroundColor(getResources().getColor(R.color.b5));
        	b6.setBackgroundColor(getResources().getColor(R.color.b5));
        	b7.setBackgroundColor(getResources().getColor(R.color.b5));
        	b8.setBackgroundColor(getResources().getColor(R.color.b5));
        	b9.setBackgroundColor(getResources().getColor(R.color.b5));
        	}
        else if(bc.equals("b6"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b6));
        	b2.setBackgroundColor(getResources().getColor(R.color.b6));
        	b3.setBackgroundColor(getResources().getColor(R.color.b6));
        	b4.setBackgroundColor(getResources().getColor(R.color.b6));
        	b5.setBackgroundColor(getResources().getColor(R.color.b6));
        	b6.setBackgroundColor(getResources().getColor(R.color.b6));
        	b7.setBackgroundColor(getResources().getColor(R.color.b6));
        	b8.setBackgroundColor(getResources().getColor(R.color.b6));
        	b9.setBackgroundColor(getResources().getColor(R.color.b6));
        	}
        else if(bc.equals("b7"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b7));
        	b2.setBackgroundColor(getResources().getColor(R.color.b7));
        	b3.setBackgroundColor(getResources().getColor(R.color.b7));
        	b4.setBackgroundColor(getResources().getColor(R.color.b7));
        	b5.setBackgroundColor(getResources().getColor(R.color.b7));
        	b6.setBackgroundColor(getResources().getColor(R.color.b7));
        	b7.setBackgroundColor(getResources().getColor(R.color.b7));
        	b8.setBackgroundColor(getResources().getColor(R.color.b7));
        	b9.setBackgroundColor(getResources().getColor(R.color.b7));
        	}
        else if(bc.equals("b8"))
        {
        	b1.setBackgroundColor(getResources().getColor(R.color.b8));
        	b2.setBackgroundColor(getResources().getColor(R.color.b8));
        	b3.setBackgroundColor(getResources().getColor(R.color.b8));
        	b4.setBackgroundColor(getResources().getColor(R.color.b8));
        	b5.setBackgroundColor(getResources().getColor(R.color.b8));
        	b6.setBackgroundColor(getResources().getColor(R.color.b8));
        	b7.setBackgroundColor(getResources().getColor(R.color.b8));
        	b8.setBackgroundColor(getResources().getColor(R.color.b8));
        	b9.setBackgroundColor(getResources().getColor(R.color.b8));
        	}
        
        
        
        if(fc.equals("b9"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b9));
        	b2.setTextColor(getResources().getColor(R.color.b9));
        	b3.setTextColor(getResources().getColor(R.color.b9));
        	b4.setTextColor(getResources().getColor(R.color.b9));
        	b5.setTextColor(getResources().getColor(R.color.b9));
        	b6.setTextColor(getResources().getColor(R.color.b9));
        	b7.setTextColor(getResources().getColor(R.color.b9));
        	b8.setTextColor(getResources().getColor(R.color.b9));
        	b9.setTextColor(getResources().getColor(R.color.b9));
        	}
        else if(fc.equals("b10"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b10));
        	b2.setTextColor(getResources().getColor(R.color.b10));
        	b3.setTextColor(getResources().getColor(R.color.b10));
        	b4.setTextColor(getResources().getColor(R.color.b10));
        	b5.setTextColor(getResources().getColor(R.color.b10));
        	b6.setTextColor(getResources().getColor(R.color.b10));
        	b7.setTextColor(getResources().getColor(R.color.b10));
        	b8.setTextColor(getResources().getColor(R.color.b10));
        	b9.setTextColor(getResources().getColor(R.color.b10));
        	}
        else if(fc.equals("b11"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b11));
        	b2.setTextColor(getResources().getColor(R.color.b11));
        	b3.setTextColor(getResources().getColor(R.color.b11));
        	b4.setTextColor(getResources().getColor(R.color.b11));
        	b5.setTextColor(getResources().getColor(R.color.b11));
        	b6.setTextColor(getResources().getColor(R.color.b11));
        	b7.setTextColor(getResources().getColor(R.color.b11));
        	b8.setTextColor(getResources().getColor(R.color.b11));
        	b9.setTextColor(getResources().getColor(R.color.b11));
        	 }
        else if(fc.equals("b12"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b12));
        	b2.setTextColor(getResources().getColor(R.color.b12));
        	b3.setTextColor(getResources().getColor(R.color.b12));
        	b4.setTextColor(getResources().getColor(R.color.b12));
        	b5.setTextColor(getResources().getColor(R.color.b12));
        	b6.setTextColor(getResources().getColor(R.color.b12));
        	b7.setTextColor(getResources().getColor(R.color.b12));
        	b8.setTextColor(getResources().getColor(R.color.b12));
        	b9.setTextColor(getResources().getColor(R.color.b12));
        	}
        else if(fc.equals("b13"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b13));
        	b2.setTextColor(getResources().getColor(R.color.b13));
        	b3.setTextColor(getResources().getColor(R.color.b13));
        	b4.setTextColor(getResources().getColor(R.color.b13));
        	b5.setTextColor(getResources().getColor(R.color.b13));
        	b6.setTextColor(getResources().getColor(R.color.b13));
        	b7.setTextColor(getResources().getColor(R.color.b13));
        	b8.setTextColor(getResources().getColor(R.color.b13));
        	b9.setTextColor(getResources().getColor(R.color.b13));
        	}
        else if(fc.equals("b14"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b14));
        	b2.setTextColor(getResources().getColor(R.color.b14));
        	b3.setTextColor(getResources().getColor(R.color.b14));
        	b4.setTextColor(getResources().getColor(R.color.b14));
        	b5.setTextColor(getResources().getColor(R.color.b14));
        	b6.setTextColor(getResources().getColor(R.color.b14));
        	b7.setTextColor(getResources().getColor(R.color.b14));
        	b8.setTextColor(getResources().getColor(R.color.b14));
        	b9.setTextColor(getResources().getColor(R.color.b14));
        	}
        else if(fc.equals("b15"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b15));
        	b2.setTextColor(getResources().getColor(R.color.b15));
        	b3.setTextColor(getResources().getColor(R.color.b15));
        	b4.setTextColor(getResources().getColor(R.color.b15));
        	b5.setTextColor(getResources().getColor(R.color.b15));
        	b6.setTextColor(getResources().getColor(R.color.b15));
        	b7.setTextColor(getResources().getColor(R.color.b15));
        	b8.setTextColor(getResources().getColor(R.color.b15));
        	b9.setTextColor(getResources().getColor(R.color.b15));
        	}
        else if(fc.equals("b16"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b16));
        	b2.setTextColor(getResources().getColor(R.color.b16));
        	b3.setTextColor(getResources().getColor(R.color.b16));
        	b4.setTextColor(getResources().getColor(R.color.b16));
        	b5.setTextColor(getResources().getColor(R.color.b16));
        	b6.setTextColor(getResources().getColor(R.color.b16));
        	b7.setTextColor(getResources().getColor(R.color.b16));
        	b8.setTextColor(getResources().getColor(R.color.b16));
        	b9.setTextColor(getResources().getColor(R.color.b16));
        	
        }
		
        ba.setTextColor(getResources().getColor(R.color.white));
    	bb.setTextColor(getResources().getColor(R.color.white));
	
        
    	
    	
    	
    	Cursor c2 = null;
        String q="SELECT * FROM player_for_x_o";
        try
        {
         	c2=db.rawQuery(q, null);
        	c2.moveToFirst();
        }
        catch(Exception e)
        {
        	
        }
    	
        PLAYER_FOR_X=c2.getString(0);
        PLAYER_FOR_O=c2.getString(1);
    	
    	TextView tv=(TextView)findViewById(R.id.textView1);
    	tv.setText(PLAYER_FOR_X+" has to start!");
    	who_started=PLAYER_FOR_X;
    	
	
	}
	
		
	public void onBackPressed()
	{
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setMessage("Quit Game?");
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
		new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
		
			
			String update_stats1="UPDATE stats_forx SET m1='x';";
			String update_stats2="UPDATE stats_forx SET m2='x';";
			String update_stats3="UPDATE stats_forx SET m3='x';";
			String update_stats4="UPDATE stats_forx SET m4='x';";
			String update_stats5="UPDATE stats_forx SET m5='x';";
			
			db.execSQL(update_stats1);
			db.execSQL(update_stats2);
			db.execSQL(update_stats3);
			db.execSQL(update_stats4);
			db.execSQL(update_stats5);
			
			Intent intent= new Intent(cc, MainMenu.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
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
	
	
	public void onclickrestart(View view)
	{
		Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
		Button b3=(Button)findViewById(R.id.Button03);
		Button b4=(Button)findViewById(R.id.Button04);
		Button b5=(Button)findViewById(R.id.Button05);
		Button b6=(Button)findViewById(R.id.Button06);
		Button b7=(Button)findViewById(R.id.Button07);
		Button b8=(Button)findViewById(R.id.Button08);
		Button b9=(Button)findViewById(R.id.Button09);
		TextView t=(TextView)findViewById(R.id.textView1);
		
		
        Cursor c=db.rawQuery("SELECT * FROM color_scheme", null);
        c.moveToFirst();
        
        String fc=null;
        fc=c.getString(1);
		
        if(fc.equals("b9"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b9));
        	b2.setTextColor(getResources().getColor(R.color.b9));
        	b3.setTextColor(getResources().getColor(R.color.b9));
        	b4.setTextColor(getResources().getColor(R.color.b9));
        	b5.setTextColor(getResources().getColor(R.color.b9));
        	b6.setTextColor(getResources().getColor(R.color.b9));
        	b7.setTextColor(getResources().getColor(R.color.b9));
        	b8.setTextColor(getResources().getColor(R.color.b9));
        	b9.setTextColor(getResources().getColor(R.color.b9));
        	}
        else if(fc.equals("b10"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b10));
        	b2.setTextColor(getResources().getColor(R.color.b10));
        	b3.setTextColor(getResources().getColor(R.color.b10));
        	b4.setTextColor(getResources().getColor(R.color.b10));
        	b5.setTextColor(getResources().getColor(R.color.b10));
        	b6.setTextColor(getResources().getColor(R.color.b10));
        	b7.setTextColor(getResources().getColor(R.color.b10));
        	b8.setTextColor(getResources().getColor(R.color.b10));
        	b9.setTextColor(getResources().getColor(R.color.b10));
        	}
        else if(fc.equals("b11"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b11));
        	b2.setTextColor(getResources().getColor(R.color.b11));
        	b3.setTextColor(getResources().getColor(R.color.b11));
        	b4.setTextColor(getResources().getColor(R.color.b11));
        	b5.setTextColor(getResources().getColor(R.color.b11));
        	b6.setTextColor(getResources().getColor(R.color.b11));
        	b7.setTextColor(getResources().getColor(R.color.b11));
        	b8.setTextColor(getResources().getColor(R.color.b11));
        	b9.setTextColor(getResources().getColor(R.color.b11));
        	 }
        else if(fc.equals("b12"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b12));
        	b2.setTextColor(getResources().getColor(R.color.b12));
        	b3.setTextColor(getResources().getColor(R.color.b12));
        	b4.setTextColor(getResources().getColor(R.color.b12));
        	b5.setTextColor(getResources().getColor(R.color.b12));
        	b6.setTextColor(getResources().getColor(R.color.b12));
        	b7.setTextColor(getResources().getColor(R.color.b12));
        	b8.setTextColor(getResources().getColor(R.color.b12));
        	b9.setTextColor(getResources().getColor(R.color.b12));
        	}
        else if(fc.equals("b13"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b13));
        	b2.setTextColor(getResources().getColor(R.color.b13));
        	b3.setTextColor(getResources().getColor(R.color.b13));
        	b4.setTextColor(getResources().getColor(R.color.b13));
        	b5.setTextColor(getResources().getColor(R.color.b13));
        	b6.setTextColor(getResources().getColor(R.color.b13));
        	b7.setTextColor(getResources().getColor(R.color.b13));
        	b8.setTextColor(getResources().getColor(R.color.b13));
        	b9.setTextColor(getResources().getColor(R.color.b13));
        	}
        else if(fc.equals("b14"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b14));
        	b2.setTextColor(getResources().getColor(R.color.b14));
        	b3.setTextColor(getResources().getColor(R.color.b14));
        	b4.setTextColor(getResources().getColor(R.color.b14));
        	b5.setTextColor(getResources().getColor(R.color.b14));
        	b6.setTextColor(getResources().getColor(R.color.b14));
        	b7.setTextColor(getResources().getColor(R.color.b14));
        	b8.setTextColor(getResources().getColor(R.color.b14));
        	b9.setTextColor(getResources().getColor(R.color.b14));
        	}
        else if(fc.equals("b15"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b15));
        	b2.setTextColor(getResources().getColor(R.color.b15));
        	b3.setTextColor(getResources().getColor(R.color.b15));
        	b4.setTextColor(getResources().getColor(R.color.b15));
        	b5.setTextColor(getResources().getColor(R.color.b15));
        	b6.setTextColor(getResources().getColor(R.color.b15));
        	b7.setTextColor(getResources().getColor(R.color.b15));
        	b8.setTextColor(getResources().getColor(R.color.b15));
        	b9.setTextColor(getResources().getColor(R.color.b15));
        	}
        else if(fc.equals("b16"))
        {
        	b1.setTextColor(getResources().getColor(R.color.b16));
        	b2.setTextColor(getResources().getColor(R.color.b16));
        	b3.setTextColor(getResources().getColor(R.color.b16));
        	b4.setTextColor(getResources().getColor(R.color.b16));
        	b5.setTextColor(getResources().getColor(R.color.b16));
        	b6.setTextColor(getResources().getColor(R.color.b16));
        	b7.setTextColor(getResources().getColor(R.color.b16));
        	b8.setTextColor(getResources().getColor(R.color.b16));
        	b9.setTextColor(getResources().getColor(R.color.b16));
        	
        }
		t.setText(R.string.blank);
		b1.setText(R.string.blank);
		b2.setText(R.string.blank);
		b3.setText(R.string.blank);
		b4.setText(R.string.blank);
		b5.setText(R.string.blank);
		b6.setText(R.string.blank);
		b7.setText(R.string.blank);
		b8.setText(R.string.blank);
		b9.setText(R.string.blank);
		
		b11=b12=b13=b21=b22=b23=b31=b32=b33=count=gameover=0;
		cb1=cb2=cb3=cb4=cb5=cb6=cb7=cb8=cb9=0;
		TextView tv=(TextView)findViewById(R.id.textView1);
		
		if(who_started.equals(PLAYER_FOR_X))//if x had started the last game,
		{
			who_started=PLAYER_FOR_O;//make o start this game.
			XrO="O";
			tv.setTextColor(getResources().getColor(R.color.black));
			tv.setText(PLAYER_FOR_O+" has to start!");
		}
		else//else if o had started the last game,
		{
			who_started=PLAYER_FOR_X;//make x start this game.
			XrO="X";
			tv.setTextColor(getResources().getColor(R.color.black));
			tv.setText(PLAYER_FOR_X+" has to start!");
		}
				
	}
	
	public void onclickstats(View view)
	{
		Intent intent=new Intent(this, Stats.class);
		startActivity(intent);
	}
	

	public void onclick(View view)
	{
		
        
		if(gameover==0)
		{
		
			
			
		
		Button B = null;
		String c;
		if(XrO.equals("X"))
		{
			c="X";
		}
		else
		{
			c="O";
		}
		switch(view.getId())
		{
			case R.id.Button01:
				
				B=(Button)findViewById(R.id.Button01);
				if(b11==0)
				{
					if(c.equals("X"))
					{
						b11=1;
					}
					else
					{
						b11=2;
					}
					B.setText(c);
					count++;
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
			case R.id.Button02:
				
				B=(Button)findViewById(R.id.Button02);
				if(b12==0)
				{
					if(c.equals("X"))
					{
						b12=1;
					}
					else
					{
						b12=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
			case R.id.Button03:
				
				B=(Button)findViewById(R.id.Button03);
				if(b13==0)
				{
					if(c.equals("X"))
					{
						b13=1;
					}
					else
					{
						b13=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
			case R.id.Button04:
				
				B=(Button)findViewById(R.id.Button04);
				if(b21==0)
				{
					if(c.equals("X"))
					{
						b21=1;
					}
					else
					{
						b21=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
					
				}
				break;
			case R.id.Button05:
				
				B=(Button)findViewById(R.id.Button05);
				if(b22==0)
				{
					if(c.equals("X"))
					{
						b22=1;
					}
					else
					{
						b22=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
			case R.id.Button06:
				
				B=(Button)findViewById(R.id.Button06);
				if(b23==0)
				{
					if(c.equals("X"))
					{
						b23=1;
					}
					else
					{
						b23=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
			case R.id.Button07:
				
				B=(Button)findViewById(R.id.Button07);
				if(b31==0)
				{
					if(c.equals("X"))
					{
						b31=1;
					}
					else
					{
						b31=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
			case R.id.Button08:
				
				B=(Button)findViewById(R.id.Button08);
				if(b32==0)
				{
					if(c.equals("X"))
					{
						b32=1;
					}
					else
					{
						b32=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
			case R.id.Button09:
				
				B=(Button)findViewById(R.id.Button09);
				if(b33==0)
				{
					if(c.equals("X"))
					{
						b33=1;
					}
					else
					{
						b33=2;
					}
					count++;
					B.setText(c);
					if(XrO.equals("X")){XrO="O";}
					else{XrO="X";}
				}
				break;
		}
		
	
		if(b11==b12&&b12==b13&&(b11==1||b11==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button01);
			Button b2=(Button)findViewById(R.id.Button02);
			Button b3=(Button)findViewById(R.id.Button03);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb1=cb2=cb3=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				db.execSQL(inc_win_x);				
				db.execSQL(inc_los_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
				
			}
		}
		else if(b21==b22&&b22==b23&&(b23==1||b23==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button04);
			Button b2=(Button)findViewById(R.id.Button05);
			Button b3=(Button)findViewById(R.id.Button06);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb4=cb5=cb6=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_win_x);
				db.execSQL(inc_los_o);
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
		}
		else if(b31==b32&&b32==b33&&(b33==1||b33==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button07);
			Button b2=(Button)findViewById(R.id.Button08);
			Button b3=(Button)findViewById(R.id.Button09);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb7=cb8=cb9=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_win_x);
				db.execSQL(inc_los_o);
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
		}
		else if(b11==b21&&b21==b31&&(b31==1||b31==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button01);
			Button b2=(Button)findViewById(R.id.Button04);
			Button b3=(Button)findViewById(R.id.Button07);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb1=cb4=cb7=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_win_x);
				db.execSQL(inc_los_o);
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
				
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
		}
		else if(b12==b22&&b22==b32&&(b32==1||b32==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button02);
			Button b2=(Button)findViewById(R.id.Button05);
			Button b3=(Button)findViewById(R.id.Button08);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb2=cb5=cb8=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_win_x);
				db.execSQL(inc_los_o);
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
		}
		else if(b13==b23&&b23==b33&&(b33==1||b33==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button03);
			Button b2=(Button)findViewById(R.id.Button06);
			Button b3=(Button)findViewById(R.id.Button09);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb3=cb6=cb9=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_win_x);
				db.execSQL(inc_los_o);
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
		}
		else if(b11==b22&&b22==b33&&(b33==1||b33==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button01);
			Button b2=(Button)findViewById(R.id.Button05);
			Button b3=(Button)findViewById(R.id.Button09);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb1=cb5=cb9=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_win_x);
				db.execSQL(inc_los_o);
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
		}
		else if(b31==b22&&b22==b13&&(b13==1||b13==2))
		{
			gameover=1;
			Button b1=(Button)findViewById(R.id.Button03);
			Button b2=(Button)findViewById(R.id.Button05);
			Button b3=(Button)findViewById(R.id.Button07);
			b1.setTextColor(getResources().getColor(R.color.white));
			b2.setTextColor(getResources().getColor(R.color.white));
			b3.setTextColor(getResources().getColor(R.color.white));
			cb3=cb5=cb7=1;
			b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
			count=0;
			TextView t=(TextView)findViewById(R.id.textView1);
			t.setTextColor(getResources().getColor(R.color.green));
			if(c.equals("X"))
			{
				t.setText(PLAYER_FOR_X+" has won!");
				String inc_win_x="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_los_o="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_win_x);
				db.execSQL(inc_los_o);
				String inc_score_x="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String dec_score_o="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(dec_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='w';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			else
			{
				t.setText(PLAYER_FOR_O+" has won!");
				String inc_win_o="UPDATE all_data SET wins=wins+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String inc_los_x="UPDATE all_data SET losses=losses+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_win_o);
				db.execSQL(inc_los_x);
				String inc_score_o="UPDATE all_data SET score=score+2 WHERE nick_name='"+PLAYER_FOR_O+"'";
				String dec_score_x="UPDATE all_data SET score=score-2 WHERE nick_name='"+PLAYER_FOR_X+"'";
				db.execSQL(inc_score_o);
				db.execSQL(dec_score_x);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='l';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
		}
		else
		{
			if(count==9)
			{
				gameover=1;
				TextView t=(TextView)findViewById(R.id.textView1);
				t.setTextColor(getResources().getColor(R.color.green));
				t.setText("Match Drawn!");
				b11=b12=b13=b21=b22=b23=b31=b32=b33=3;
				
				String inc_drw_x="UPDATE all_data SET draws=draws+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_drw_o="UPDATE all_data SET draws=draws+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_drw_o);
				db.execSQL(inc_drw_x);
				String inc_score_x="UPDATE all_data SET score=score+1 WHERE nick_name='"+PLAYER_FOR_X+"'";
				String inc_score_o="UPDATE all_data SET score=score+1 WHERE nick_name='"+PLAYER_FOR_O+"'";
				db.execSQL(inc_score_x);
				db.execSQL(inc_score_o);
				String update_stats1="UPDATE stats_forx SET m1=m2;";
				String update_stats2="UPDATE stats_forx SET m2=m3;";
				String update_stats3="UPDATE stats_forx SET m3=m4;";
				String update_stats4="UPDATE stats_forx SET m4=m5;";
				String update_stats5="UPDATE stats_forx SET m5='d';";
				db.execSQL(update_stats1);
				db.execSQL(update_stats2);
				db.execSQL(update_stats3);
				db.execSQL(update_stats4);
				db.execSQL(update_stats5);
			}
			
		}
				
	}//if(gameover!=0) ends
		
	}//function ends
	
	
	
	
	
	private final Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{

			switch (msg.what) 
			{

			case MESSAGE_READ:

				

				break;

			case MESSAGE_TOAST:

				

				break;
			}
		}
	};
	
	
	
	
	
}//class ends

