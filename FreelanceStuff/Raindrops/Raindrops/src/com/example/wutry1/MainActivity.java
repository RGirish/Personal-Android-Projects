package com.example.wutry1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.example.raindrops.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
			setContentView(R.layout.main_layout);
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
		menu.add(Menu.NONE, 10 , Menu.NONE, "  Like Us :)");
		menu.add(Menu.NONE, 20 , Menu.NONE, "  About Us");
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		super.onMenuItemSelected(featureId, item);
		String page = null;
		if(item.getItemId()==10) 
		{
			page="http://www.facebook.com/Raindropss.media";
			URL u;
			try {
				u = new URL(page);
				Intent viewMessage = new Intent(Intent.ACTION_VIEW, Uri.parse(u.toExternalForm()));
				this.startActivity(viewMessage);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}		
		else
		{
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setMessage("Raindropss spreads social awareness messages to public through theme songs and short films. Raindropss has entered INDIA OF RECORDs for being the first team in India to spread social awareness messages through theme songs and short films.");
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok",	new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which){}});
			dialog.show();
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
				    					titles[i]=name+" Posted a \""+msg.getTitle()+"\n"+msg.getDate().substring(0, 22);
				    				}
				    				else
				    				{
				    					titles[i]=name+" Posted \""+msg.getTitle()+"\""+"\n\n"+msg.getDate().substring(0, 22);
				    				}
				    				i++;
				    			
				    				
				    			}
				    			else
				    			{
				    				msgfrmwu.add(msg.copy());
				    			
				    				if(msg.getTitle().equals("Photo")||msg.getTitle().equals("Video"))
				    				{
				    					titles[i]=msg.getTitle()+"\n"+msg.getDate().substring(0, 22);
				    				}
				    				else
				    				{
				    					titles[i]=msg.getTitle()+"\n\n"+msg.getDate().substring(0, 22);
				    				}
				    				i++;
				    		
				    			
				    			}				    		
				    			count++;
				    		}
				    	}
				    	
				    	
			      
				    	pd.dismiss();	
				    	MyAdapter adapter = new MyAdapter(context,R.layout.row ,titles);
				        setListAdapter(adapter);
				    	
			      
			      
			      
			      }
			    });
			  }
			}).start();
	
	
	
		
	  
	
	}
	

}
