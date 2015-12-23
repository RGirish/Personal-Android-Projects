package com.example.xmlparsing;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.xmlpull.v1.XmlPullParserException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	ProgressDialog dialog;
	String s=new String();
	String MyTitle = null;
	String MyDate = null;
	String MyDesc = null;
	public static final String WIFI = "Wi-Fi";
	public static final String ANY = "Any";
	//http://stackoverflow.com /feeds/tag?tagnames=android&sort=newest
	//http://www.nasa.gov/rss/image_of_the_day.rss
	private static final String URL = "http://www.nasa.gov/rss/image_of_the_day.rss";
	private static boolean wifiConnected = false; 
	private static boolean mobileConnected = false;
	public static String sPref = null;
	
	
	

	public static boolean refreshDisplay = true; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		final Handler handler1=new Handler();
		final Handler handler2=new Handler();
		dialog=ProgressDialog.show(this,"Loading","Loading Image of the Day");
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		NetworkReceiver receiver = new NetworkReceiver();
		this.registerReceiver(receiver, filter);
		
		
		
		Thread th=new Thread()
		{
			
			
			public void run()
			{
			
				sPref=ANY;
				mobileConnected=true;
				
				//updateConnectedFlags();
				
				if(refreshDisplay)
				{	
					if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) 
					{
						try 
						{
							s=loadXmlFromNetwork(URL);
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if ((sPref.equals(WIFI)) && (wifiConnected)) 
					{
						try 
						{
							s=loadXmlFromNetwork(URL);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					} 
				
				
					handler1.post(
							new Runnable()
							{
								public void run()
								{
									resetDisplay(s);
									dialog.dismiss();	
								}
							}
						);
				
				
				
				}
				else
				{
					handler2.post(
							new Runnable()
							{
								public void run()
								{
									dialog.dismiss();
									Toast.makeText(getApplicationContext(), R.string.connection_lost, Toast.LENGTH_LONG).show();	
								}
							}
						);
				}
				
				
			}
		};th.start();
        
        
		unregisterReceiver(receiver);
        
        
	}
	
	
	
	
	private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException 
    {
    	String mm=new String();
        InputStream stream = null;
        StackOverflowXmlParser stackOverflowXmlParser = new StackOverflowXmlParser();
        try 
        {
        	stream = downloadUrl(urlString);   
        	try 
        	{
        		mm=stackOverflowXmlParser.parse(stream);
			} 
        	catch (XmlPullParserException e) 
        	{
				e.printStackTrace();
			}
        }
        finally
        {
            if (stream != null) 
            {
                stream.close();
            } 
        }
        return mm;
    }
	
	
	
	private InputStream downloadUrl(String urlString) throws IOException 
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
	
	
	
	
	
	
	public void resetDisplay(String mmm)
	{
		setContentView(R.layout.activity_main);
        TextView t1=(TextView)findViewById(R.id.textView1);
		t1.setText(mmm);
		TextView t2=(TextView)findViewById(R.id.textView2);
		t2.setText(mmm);
		TextView t3=(TextView)findViewById(R.id.textView3);
		t3.setText(mmm);
		Toast.makeText(getBaseContext(), "567567567", Toast.LENGTH_SHORT).show();
	}
	
	
	
	
	
	
	
	

	
	
	
	/*@Override
	public void onStart()
	{
		super.onStart();
        if(refreshDisplay)
        {
        	//Toast.makeText(getApplicationContext(), "inside if in onCreate", Toast.LENGTH_LONG).show();
        	loadPage();
        }
	}
	*/
	public void loadPage() 
	{  
		
		if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) 
		{
			new DownloadXmlTask().execute(URL);
			Toast.makeText(getApplicationContext(), "123123123", Toast.LENGTH_SHORT).show();
		}
		else if ((sPref.equals(WIFI)) && (wifiConnected)) 
		{
			new DownloadXmlTask().execute(URL);	
	    } 
		else{}
	}
	
	private class DownloadXmlTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	        try {
	        	
	            return loadXmlFromNetwork(urls[0]);

	        } catch (IOException e) {
	        	return getResources().getString(R.string.connection_lost);
	        } catch (XmlPullParserException e) {
	        	return getResources().getString(R.string.xml_error);
	        }
	    }

	    @Override
	    protected void onPostExecute(String mmm)
	    {  
	        setContentView(R.layout.activity_main);
	        TextView t1=(TextView)findViewById(R.id.textView1);
			t1.setText(mmm);
			TextView t2=(TextView)findViewById(R.id.textView2);
			t2.setText(mmm);
			TextView t3=(TextView)findViewById(R.id.textView3);
			t3.setText(mmm);
			//Toast.makeText(getApplicationContext(), "456456", Toast.LENGTH_SHORT).show();
			Toast.makeText(getBaseContext(), "567567567", Toast.LENGTH_SHORT).show();
	    }
	    
	    
	    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException 
	    {
	    	String mm=null;
	        InputStream stream = null;
	        StackOverflowXmlParser stackOverflowXmlParser = new StackOverflowXmlParser();
	        try 
	        {
	        	stream = downloadUrl(urlString);   
	        	try 
	        	{
	        		mm=stackOverflowXmlParser.parse(stream);
				} 
	        	catch (XmlPullParserException e) 
	        	{
					e.printStackTrace();
				}
	        }
	        finally 
	        {
	            if (stream != null) 
	            {
	                stream.close();
	            } 
	        }
	        return mm;
	    }

	    private InputStream downloadUrl(String urlString) throws IOException 
	    {
	        URL url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000);
	        conn.setConnectTimeout(15000);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        conn.connect();
	        return conn.getInputStream();
	    }
	    
	}
	
	
	
	private void updateConnectedFlags()
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) 
		{
			wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
			mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
		} 
		else 
		{
			wifiConnected = false;
			mobileConnected = false;
		}
	}
	
	
	
	 public class NetworkReceiver extends BroadcastReceiver {

	        @Override
	        public void onReceive(Context context, Intent intent) {
	            ConnectivityManager connMgr =
	                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

	            if (WIFI.equals(sPref) && networkInfo != null
	                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
	            	 refreshDisplay = true;
	            	 //Toast.makeText(context, R.string.wifi, Toast.LENGTH_SHORT).show();
	              
	            } else if (ANY.equals(sPref) && networkInfo != null) {
	            	//Toast.makeText(getApplicationContext(), "inside network receiver", Toast.LENGTH_SHORT).show();
	            	refreshDisplay = true;

	            } else {
	                refreshDisplay = false;
	                //Toast.makeText(context, R.string.connection_lost, Toast.LENGTH_SHORT).show();
	            }
	        }
	    }
}
