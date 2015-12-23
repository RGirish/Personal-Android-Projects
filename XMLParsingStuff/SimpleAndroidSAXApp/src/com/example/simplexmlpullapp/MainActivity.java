package com.example.simplexmlpullapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.*;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends ListActivity 
{

	private final String willyou_url="http://fbrss.com/f/85518f0417a99f19f5bf30b7e3d71b66_779Vg7MJIGKLKSa1o2nH.xml";	
	List<Message> messages;
	private URL feedUrl=null;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		new ParsingTask().execute(willyou_url);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent viewMessage = new Intent(Intent.ACTION_VIEW, 
				Uri.parse(messages.get(position).getLink().toExternalForm()));
		this.startActivity(viewMessage);
	}
	
/*	protected InputStream getInputStream() {
		try {
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
*/	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	public List<Message> function() throws XmlPullParserException,IOException 
	{
		
		
		final Message currentMessage = new Message();
		RootElement root = new RootElement("rss");
		final List<Message> messages = new ArrayList<Message>();
		Element channel = root.getChild("channel");
		Element item = channel.getChild("item");
		item.setEndElementListener(new EndElementListener(){
			public void end() {
				messages.add(currentMessage.copy());
			}
		});
		item.getChild("title").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setTitle(body);
			}
		});
		item.getChild("link").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setLink(body);
			}
		});
		item.getChild("description").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setDescription(body);
			}
		});
		item.getChild("pubDate").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setDate(body);
			}
		});
		
		try 
		{
			InputStream in=feedUrl.openConnection().getInputStream();
			Xml.parse(in, Xml.Encoding.UTF_8, root.getContentHandler());
		} 
		catch (Exception e1) 
		{
			throw new RuntimeException(e1);
		}	
		return messages;
	}
	
	
	
	
	
	private class ParsingTask extends AsyncTask<String, Void, List<Message>>
	{
	    
	    protected List<Message> doInBackground(String... urls) 
	    {
	    	List<Message> k=null;
			try 
			{
				k = function();
			} 
			catch (XmlPullParserException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return  k;
	    }
	    
	    protected void onPostExecute(List<Message> mssg)
	    {
	    	ListView lv=(ListView)findViewById(R.layout.main);
	    	List<String> titles = new ArrayList<String>(mssg.size());
	    	for (Message msg : mssg){
	    		titles.add(msg.getTitle());
	    	}
	    	for(String s:titles)
	    	{
	    		
	    	}
	    	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row,titles);
	    	//this.setListAdapter(adapter);
	    }
	}
	
	
	
	
	
}