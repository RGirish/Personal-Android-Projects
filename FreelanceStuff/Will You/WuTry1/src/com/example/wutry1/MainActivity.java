package com.example.wutry1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity 
{
	Context context;
	ProgressDialog pd;
	List<Message> messages=new ArrayList<Message>(19+5+7+3);
	List<Message> msgfrmwu=new ArrayList<Message>(19+5+7+3);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 10 , Menu.NONE, "Like Us :)");
		menu.add(Menu.NONE, 20 , Menu.NONE, "     Contact App's Author     ");
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		super.onMenuItemSelected(featureId, item);
		String page = null;
		if(item.getItemId()==10) page="http://www.facebook.com/Willyoutamilshortfilm";
		else page="http://www.facebook.com/Girish1994";
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
		URL u=msgfrmwu.get(position-1).getLink();
		super.onListItemClick(l, v, position, id);
		Intent viewMessage = new Intent(Intent.ACTION_VIEW,	Uri.parse(u.toExternalForm()));
		this.startActivity(viewMessage);
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
		final Weather[] weather_dat = new Weather[19+4+7+1];
		pd=ProgressDialog.show(this, "Loading","Please Wait..", true);
		new Thread(new Runnable() {
			  @Override
			  public void run()
			  {
			   
				  AndroidSaxFeedParser aaa=new AndroidSaxFeedParser();
				  messages=aaa.parse();
				  
				  runOnUiThread(new Runnable() {
			      @Override
			      public void run()
			      {
			    	  List<String> titles = new ArrayList<String>(messages.size());
				       	int i=0,count=0;
				    	
				    	
				    	for (Message msg : messages)
				    	{
				    		if(count<25+5+7+3)
				    		{
				    		//Here's where you have to separate your posts from those from fans !! 
				    		String rg=msg.getDescription();
				    		if(rg.startsWith("From")) /* If (Description starts with 'From') */
				    		{
				    			//No need to include
				    		}
				    		else
				    		{
				    			msgfrmwu.add(msg.copy());
				    			titles.add(msg.getTitle());
				    			if(msg.getTitle().startsWith("Photo"))
				    			{
				    				if(msg.getTitle().equals("Photo"))
				    				{
				    					weather_dat[i]=new Weather(R.drawable.picture,msg.getTitle()+"                         ");
				    				}
				    				else
				    				{
				    					weather_dat[i]=new Weather(R.drawable.picture,msg.getTitle());
				    				}
				    				
				    			}
				    			else if(msg.getTitle().startsWith("Video"))
				    			{
				    				if(msg.getTitle().equals("Video"))
				    				{
				    					weather_dat[i]=new Weather(R.drawable.video,msg.getTitle()+"                         ");
				    				}
				    				else
				    				{
				    					weather_dat[i]=new Weather(R.drawable.video,msg.getTitle());
				    				}
				    			}
				    			else
				    			{
				    				weather_dat[i]=new Weather(R.drawable.quotation,msg.getTitle());
				    			}
				    			i++;
				    		}
				    		
				    		count++;
				    		}
				    	}
				    
			      
				    	pd.dismiss();						
				    	MyAdapter adapter = new MyAdapter(context,R.layout.row, weather_dat);				    	
				    	ListView listView1 = (ListView)findViewById(android.R.id.list);				    	
				    	View header = (View)getLayoutInflater().inflate(R.layout.header, null);
				    	listView1.addHeaderView(header);
				    	listView1.setAdapter(adapter);				    	
				    	adapter.notifyDataSetChanged();
				    	
			      
			      
			      
			      }
			    });
			  }
			}).start();
	
	
	
		
	  
	
	}
	

}
