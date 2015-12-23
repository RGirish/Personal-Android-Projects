package com.example.wutry1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.wutry2.R;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class DownloadService extends IntentService 
{

  List<Message> messages=new ArrayList<Message>(35);
	List<Message> msgfrmwu=new ArrayList<Message>(35);
     
  public DownloadService() 
  {
    super("DownloadService");
  }

  // Will be called asynchronously be Android
  @SuppressWarnings("deprecation")
protected void onHandleIntent(Intent intent)
  {
	  
	  
	  if(CheckConnection()==1)
	  {
	  
	  AndroidSaxFeedParser aaa=new AndroidSaxFeedParser();
	  messages=aaa.parse();
	  String[] titles = new String[35];
     	int i=0,count=0;
  	
  	
  	for (Message msg : messages)
  	{
  		if(count<35)
  		{
  		String rg=msg.getDescription();
  		if(rg.contains("fbrss.com"))
  		{
  			
  		}
  		else if(rg.startsWith("From"))
  		{
  			int start=0,end=0;
  			start=rg.indexOf(">");
  			end=rg.indexOf("<",start);
  			String name=rg.substring(start+1,end);
  			
  			msgfrmwu.add(msg.copy());
  			
  			if(msg.getTitle().equals("Photo")||msg.getTitle().equals("Video"))
				{
  				titles[i]=name+" Posted a \""+msg.getTitle()+"                                                  ";
				}
  			else
  			{
  				titles[i]=name+" Posted \""+msg.getTitle()+"\"";				    				
  			}
  			i++;
  			
  			
  		}
  		else
  		{
  			msgfrmwu.add(msg.copy());
  			
  			if(msg.getTitle().equals("Photo")||msg.getTitle().equals("Video"))
				{
  				titles[i]=msg.getTitle()+"                                                  ";
				}
  			else
  			{
  				titles[i]=msg.getTitle();
  			}
  			i++;
  		
  			
  		}
  		
  		count++;
  		}
  	}
  	
  	
  	
  	SQLiteDatabase db;
    
    db = openOrCreateDatabase("ABCD.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
    db.setVersion(1);
    db.setLocale(Locale.getDefault());
    db.setLockingEnabled(true);
    
    final String CREATE_TABLE ="CREATE TABLE last_entry(title TEXT);";
    final String INSERT_DUMMY_ROW="INSERT INTO last_entry VALUES('ssss');";
    try
    {
    	db.execSQL(CREATE_TABLE);
    	db.execSQL(INSERT_DUMMY_ROW);
    }
    catch(SQLiteException e)
    {
    	
    }
    String SELECT="SELECT * FROM last_entry";
    Cursor c=db.rawQuery(SELECT, null);
    c.moveToFirst();
    String LAST_TITLE=c.getString(0);
    db.close();
  	
  	
  	
  	
  	
  	if(titles[0].equals(LAST_TITLE)==false)
  	{
  			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
  			int icon = R.drawable.icon;
  			CharSequence notiText = "New post in Will You...? Page";
  			long meow = System.currentTimeMillis();

  			Notification notification = new Notification(icon, notiText, meow);

  			Context context = getApplicationContext();
  			CharSequence contentTitle = "New post in Will You...? Page";
  			CharSequence contentText = "Click here to check it out!";
  			Intent notificationIntent = new Intent(this, MainActivity.class);
  			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

  			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

  			notification.flags=Notification.DEFAULT_SOUND|Notification.FLAG_AUTO_CANCEL|Notification.FLAG_ONLY_ALERT_ONCE;
  			notificationManager.notify(1, notification);
  	}
	  
	 
	Log.i("asdasd","dfgdfg");
		
	  }
		
  }
  
  
  
  
  private int CheckConnection() 
	{
      ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
      if (activeInfo == null ) return 0;
      else return 1;
  }
  
  
}
