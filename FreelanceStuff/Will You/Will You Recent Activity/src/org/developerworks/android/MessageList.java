package org.developerworks.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MessageList extends ListActivity {
	
	private List<Message> messages=new ArrayList<Message>(25);
	private List<Message> msgfrmwu=new ArrayList<Message>(25);
    
	@Override
    public void onCreate(Bundle icicle) 
	{
        super.onCreate(icicle);
        setContentView(R.layout.main);
        loadFeed(ParserType.ANDROID_SAX);
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Like Us :)");
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		String likepage="http://www.facebook.com/Willyoutamilshortfilm";
		URL u;
		try {
			u = new URL(likepage);
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
		this.startActivity(viewMessage);//messages.get(position).getLink().toExternalForm())
	}

	
	private void loadFeed(ParserType type)
	{
    	try
    	{
    		FeedParser parser = FeedParserFactory.getParser(type);
	    	messages = parser.parse();
	    	
	    	List<String> titles = new ArrayList<String>(messages.size());
	    	
	    	int i=0,count=0;
	    	Weather[] weather_dat = new Weather[19+6];
	    	
	    	for (Message msg : messages)
	    	{
	    		if(count<25+8)
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
	    				weather_dat[i]=new Weather(R.drawable.picture,msg.getTitle());
	    			}
	    			else if(msg.getTitle().startsWith("Video"))
	    			{
	    				weather_dat[i]=new Weather(R.drawable.video,msg.getTitle());
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
	    	
	    		
	    	MyAdapter adapter = new MyAdapter(this,R.layout.row, weather_dat);
	    	
	    	ListView listView1 = (ListView)findViewById(android.R.id.list);
	    	
	    	View header = (View)getLayoutInflater().inflate(R.layout.header, null);
	    	listView1.addHeaderView(header);
	    	
	    	listView1.setAdapter(adapter);
	    	
	    	adapter.notifyDataSetChanged();
	    	
    	
    	}
    	catch (Throwable t)
    	{
    		Log.e("AndroidNews",t.getMessage(),t);
    	}
    
	
	}
}


