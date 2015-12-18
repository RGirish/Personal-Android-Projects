package com.example.letmetellyou;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{

	private static final String TAG = "MyService";
	String pattern="UDDU",now="****";
	Context cc;

	SettingsContentObserver mSettingsContentObserver;
	

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() 
	{
		
		cc=this;
		Log.d(TAG, "onCreate");
		
		mSettingsContentObserver = new SettingsContentObserver(this,new Handler());
		getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver );
		
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");	
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");		
		 getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
	}

	public void bring_to_fg() 
	{
		Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName cn = new ComponentName(this, MainActivity.class);
        intent.setComponent(cn);
        startActivity(intent);
	}
	
	
}
