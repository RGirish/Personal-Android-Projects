
package com.example.wutry1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.example.wutry2.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ListActivity;
import android.os.Bundle;

public class MainActivity extends ListActivity 
{
	Context context;
	ProgressDialog pd;
	List<Message> messages=new ArrayList<Message>(35);
	List<Message> msgfrmwu=new ArrayList<Message>(35);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		context=this;
		if(CheckConnection()==1)
		{
			 
			function();
		}
		else
		{
			Toast.makeText(this,"Oops! Check Your Internet Connection! :)", Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void function2()
	{
		Log.i("123","123func2started");
		Calendar cal = Calendar.getInstance();

		Intent intent = new Intent(this, DownloadService.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);

		AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 300*1000, pintent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 10 , Menu.NONE, "  Like Us :)");
		menu.add(Menu.NONE, 20 , Menu.NONE, "  About Us");
		menu.add(Menu.NONE, 30 , Menu.NONE, "  Contact App's Author");
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		super.onMenuItemSelected(featureId, item);
		String page = null;
		if(item.getItemId()==10) page="http://www.facebook.com/Willyoutamilshortfilm";
		else if(item.getItemId()==30) page="http://www.facebook.com/Girish1994";
		else
		{
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setMessage("\nWill You...? \n\nWhat happens when two dreams turn into reality..?\nThis is our debut short film... Like our Facebook page, keep track of all updates online, and mail us at\n\ncaifenerstudios@gmail.com\n");
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok",	new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which){}});
			dialog.show();
		}
		URL u;
		try {
			u = new URL(page);
			Intent viewMessage = new Intent(Intent.ACTION_VIEW, Uri.parse(u.toExternalForm()));
			this.startActivity(viewMessage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		if(position<msgfrmwu.size())
		{
			URL u=msgfrmwu.get(position).getLink();
			Intent viewMessage = new Intent(Intent.ACTION_VIEW,	Uri.parse(u.toExternalForm()));
			this.startActivity(viewMessage);
		}
	}
	
	
	
	private int CheckConnection() 
	{
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo == null ) return 0;
        else return 1;
    }
	
	
	
	public void function()
	{
		pd=ProgressDialog.show(this, "Loading","Please Wait..", true);
		new Thread(new Runnable() {
			  @Override
			  public void run()
			  {
			   
				  AndroidSaxFeedParser aaa=new AndroidSaxFeedParser();
				  messages=aaa.parse();
				  
				  runOnUiThread(new Runnable() {
			      @SuppressWarnings("deprecation")
				@Override
			      public void run()
			      {
			    	  String[] titles = new String[35];
				       	int i=0,count=0;
				    	
				    	
				    	for (Message msg : messages)
				    	{
				    		if(count<35)
				    		{
				    		String rg=msg.getDescription();
				    		if(rg.contains("fbrss.com"))
				    		{
				    			Toast.makeText(context, "Oops! Check Your Internet Connection! :)", Toast.LENGTH_LONG).show();
				    			setContentView(R.layout.empty);
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
				    
			      
				    	pd.dismiss();
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
				        String UPDATE="UPDATE last_entry SET title='"+titles[0]+"';";
				        db.execSQL(UPDATE);
				        db.close();
				    	MyAdapter adapter = new MyAdapter(context, titles);
				    	setListAdapter(adapter);
				    	function2();
				    	
			      }
			    });
			  }
			}).start();
	
	
	
		
	  
	
	}
	

}
