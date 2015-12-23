package com.example.wutry1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

public class AndroidSaxFeedParser {

	static final String RSS = "rss";
	String CHANNEL="channel";
	String ITEM="item";
	String TITLE="title";
	String LINK="link";
	String DESCRIPTION="description";
	String PUB_DATE="PubDate";
	
	
	public List<Message> parse() 
	{
		final Message currentMessage = new Message();
		RootElement root = new RootElement(RSS);
		final List<Message> messages = new ArrayList<Message>(25);
		String CHANNEL="channel";
		Element channel = root.getChild(CHANNEL);
		Element item = channel.getChild(ITEM);
		item.setEndElementListener(new EndElementListener(){
			public void end() {
				messages.add(currentMessage.copy());
			}
		});
		item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setTitle(body);
			}
		});
		item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setLink(body);
			}
		});
		item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setDescription(body);
			}
		});
		item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setDate(body);
			}
		});
		try {
			Xml.parse(getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
		return messages;
	}
	
	public InputStream getInputStream()
	{
		String MyUrl="";
		try 
		{
			URL feedUrl=new URL(MyUrl);
			return feedUrl.openConnection().getInputStream();
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}
	}
	
}
