
package com.example.rain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity
{
	Context context;
	Dialog dialog;
	List<Message> messages=new ArrayList<Message>(20);
	
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		context=this;
		
		if(CheckConnection()==1)
		{
			function();
		}
		else
		{
			setContentView(R.layout.empty);
			Toast.makeText(this,"Check your Internet Connection!", Toast.LENGTH_LONG).show();
		}
	}
	
	public void function()
	{
		final String[] titles = new String[20];
		final Dialog dialog = new Dialog(Main.this,R.style.PauseDialog);
        dialog.setContentView(R.layout.start);
        dialog.setCancelable(false);
        //dialog.show();
        

		new Thread(new Runnable() {
			  @Override
			  public void run()
			  {
			   
				  AndroidSaxFeedParser aaa=new AndroidSaxFeedParser();
				  messages=aaa.parse();
				  //notifyAll();
				  
				  runOnUiThread(new Runnable() {
			      @SuppressWarnings("unused")
				@Override
			      public void run()
			      {
			    	 /* try {
						this.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}*/
			    	  
				       	int i=0;
						int count=0;
				       	LinearLayout ll1=(LinearLayout)findViewById(R.id.ll1);
				    	LinearLayout ll2=(LinearLayout)findViewById(R.id.ll2);
				    	LinearLayout ll3=(LinearLayout)findViewById(R.id.ll3);
				    	LinearLayout ll4=(LinearLayout)findViewById(R.id.ll4);
				    	LinearLayout ll5=(LinearLayout)findViewById(R.id.ll5);
				    	LinearLayout ll6=(LinearLayout)findViewById(R.id.ll6);
				    	LinearLayout llmain=(LinearLayout)findViewById(R.id.llmain);
				    	
				    	for (Message msg : messages)
				    	{
				    		if(count<20)
				    		{
				    			String rg=msg.getDescription();
				    			if(rg.contains("fbrss.com"))
				    			{
				    				dialog.dismiss();
				    				setContentView(R.layout.empty);
				    				Toast.makeText(context, "Unable to connect to facebook right now! :(", Toast.LENGTH_LONG).show();
				    				return;
				    			}
				    			if(rg.startsWith("From"))
				    			{
				    				int start=0,end=0;
				    				start=rg.indexOf(">");
				    				end=rg.indexOf("<",start);
				    				String name=rg.substring(start+1,end);
				    			
				    				
				    				
				    				if(msg.getTitle().equals("Photo")||msg.getTitle().equals("Video"))
				    				{
				    					titles[i]=name+" Posted a \""+msg.getTitle()+"\n"+msg.getDate().substring(0, 22);
				    				}
				    				else
				    				{
				    					titles[i]=name+" Posted \""+msg.getTitle()+"\""+"\n\n"+msg.getDate().substring(0, 22);
				    					
				    					final TextView tv=new TextView(context);
										tv.setText(titles[i]);
										final String mm=titles[i];
										tv.setWidth(90);
										tv.setHeight(90);
										tv.setPadding(10, 10, 10, 10);
										
										tv.setOnClickListener(new OnClickListener()
								    	{

											@Override
											public void onClick(View a) 
											{
												String text=mm;
												
												SQLiteDatabase db;
												db = openOrCreateDatabase("TEXT.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
											    db.setVersion(1);
											    db.setLocale(Locale.getDefault());
											    db.setLockingEnabled(true);
										        
										        final String CREATE_TABLE ="CREATE TABLE mytext (tt TEXT,link TEXT);";
										        final String INSERT_DUMMY_ROW="INSERT INTO mytext VALUES('null','null');";
										        final String UPDATE_ROW="UPDATE mytext SET tt='"+text+"';";
										        try
										        {
										        	db.execSQL(CREATE_TABLE);
										        	db.execSQL(INSERT_DUMMY_ROW);
										        }
										        catch(SQLiteException e){}
										        db.execSQL(UPDATE_ROW);
										        db.close();
										        Intent intent= new Intent(context, SeePost.class);
												startActivity(intent);
											}
								    		
								    	});
										
										ll4.addView(tv);
				    				}
				    				i++;
				    			
				    				
				    			}
				    			else
				    			{
				    				if(msg.getTitle().equals("Photo"))
				    				{
				    					titles[i]=msg.getTitle()+"\n"+msg.getDate().substring(0, 22);
				    				}
				    				if(msg.getTitle().equals("Video"))
				    				{
				    					titles[i]=msg.getTitle()+"\n"+msg.getDate().substring(0, 22);
				    				}
				    				if(msg.getTitle().startsWith("Photo"))
				    				{
				    					final TextView tv=new TextView(context);
										tv.setText(titles[i]);
										final String mm=titles[i];
										tv.setWidth(90);
										tv.setHeight(90);
										tv.setPadding(10, 10, 10, 10);
										
										tv.setOnClickListener(new OnClickListener()
								    	{

											@Override
											public void onClick(View a) 
											{
												String text=mm;
												
												SQLiteDatabase db;
												db = openOrCreateDatabase("TEXT.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
											    db.setVersion(1);
											    db.setLocale(Locale.getDefault());
											    db.setLockingEnabled(true);
												
												final String CREATE_TABLE ="CREATE TABLE mytext (tt TEXT,link TEXT);";
										        final String INSERT_DUMMY_ROW="INSERT INTO mytext VALUES('null','null');";
										        final String UPDATE_ROW="UPDATE mytext SET tt='"+text+"';";
										        try
										        {
										        	db.execSQL(CREATE_TABLE);
										        	db.execSQL(INSERT_DUMMY_ROW);
										        }
										        catch(SQLiteException e){}
										        db.execSQL(UPDATE_ROW);
										        db.close();
										        Intent intent= new Intent(context, SeePost.class);
												startActivity(intent);
											}
								    		
								    	});
										
										ll2.addView(tv);
				    				}
				    				if(msg.getTitle().startsWith("Video"))
				    				{
				    					final TextView tv=new TextView(context);
										tv.setText(titles[i]);
										final String mm=titles[i];
										tv.setWidth(90);
										tv.setHeight(90);
										tv.setPadding(10, 10, 10, 10);
										
										tv.setOnClickListener(new OnClickListener()
								    	{
											@Override
											public void onClick(View a) 
											{
												String text=mm;
												
												SQLiteDatabase db;
												db = openOrCreateDatabase("TEXT.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
											    db.setVersion(1);
											    db.setLocale(Locale.getDefault());
											    db.setLockingEnabled(true);
												
												final String CREATE_TABLE ="CREATE TABLE mytext (tt TEXT,link TEXT);";
										        final String INSERT_DUMMY_ROW="INSERT INTO mytext VALUES('null','null');";
										        final String UPDATE_ROW="UPDATE mytext SET tt='"+text+"';";
										        try
										        {
										        	db.execSQL(CREATE_TABLE);
										        	db.execSQL(INSERT_DUMMY_ROW);
										        }
										        catch(SQLiteException e){}
										        db.execSQL(UPDATE_ROW);
										        db.close();
										        Intent intent= new Intent(context, SeePost.class);
												startActivity(intent);
											}
								    		
								    	});
										
										ll3.addView(tv);
				    				}
				    				else
				    				{
				    					titles[i]=msg.getTitle()+"\n\n"+msg.getDate().substring(0, 22);
				    					
				    					final TextView tv=new TextView(context);
										tv.setText(titles[i]);
										final String mm=titles[i];
										tv.setWidth(90);
										tv.setHeight(90);
										tv.setPadding(10, 10, 10, 10);
										
										tv.setOnClickListener(new OnClickListener()
								    	{
											@Override
											public void onClick(View a) 
											{
												String text=mm;
												
												SQLiteDatabase db;
												db = openOrCreateDatabase("TEXT.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
											    db.setVersion(1);
											    db.setLocale(Locale.getDefault());
											    db.setLockingEnabled(true);
												
												final String CREATE_TABLE ="CREATE TABLE mytext (tt TEXT,link TEXT);";
										        final String INSERT_DUMMY_ROW="INSERT INTO mytext VALUES('null','null');";
										        final String UPDATE_ROW="UPDATE mytext SET tt='"+text+"';";
										        try
										        {
										        	db.execSQL(CREATE_TABLE);
										        	db.execSQL(INSERT_DUMMY_ROW);
										        }
										        catch(SQLiteException e){}
										        db.execSQL(UPDATE_ROW);
										        db.close();
										        Intent intent= new Intent(context, SeePost.class);
												startActivity(intent);
											}
								    		
								    	});
										
										ll1.addView(tv);
				    				}
				    				i++;
				    		
				    			
				    			}
				    			count++;
				    		}
				    	}
				    	
				    	
				    	//dialog.dismiss();
				    	
				    	
				        return;
			      }
			    });
			  }
			}).start();
		
	
	
	}
	
	
	
	
	
	
	
	
	
	
	private int CheckConnection() 
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo == null ) return 0;
		else return 1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 10 , Menu.NONE, "Like Us :)");
		menu.add(Menu.NONE, 20 , Menu.NONE, "About Us");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		super.onMenuItemSelected(featureId, item);
		String page = null;
		if(item.getItemId()==10) 
		{
			page="http://www.facebook.com/balebesh";
			URL u;
			try 
			{
				u = new URL(page);
				Intent viewMessage = new Intent(Intent.ACTION_VIEW, Uri.parse(u.toExternalForm()));
				this.startActivity(viewMessage);
			} 
			catch (MalformedURLException e) {e.printStackTrace();}
		}		
		else
		{
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setMessage("'balebesh' is the place to discuss, buy and sell anything and everything about Music & Beyond...\nIf you are an artist or a band looking for visibility and reach, we can create web portfolios on our site and update your schedules.\nWe also cover your concerts and other shows with a very reasonable charge. If you have queries, mail us at care@balebesh.com");
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok",	new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which){}});
			dialog.show();
		}		
		return true;
	}


}


