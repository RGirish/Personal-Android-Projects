package com.example.simplexmlpullapp;

import android.os.Bundle;
import android.app.Activity;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.*;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			function();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void function() throws XmlPullParserException,IOException 
	{
		XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp=factory.newPullParser();
		InputStream raw = getApplicationContext().getAssets().open("image_of_the_day.xml");
		xpp.setInput(raw, null);
		//xpp.setInput(new StringReader ("<foo>Hello World!</foo>"));
		int eventType=xpp.getEventType();
		
		TextView tsd=(TextView)findViewById(R.id.textView1);
		TextView ted=(TextView)findViewById(R.id.textView2);
		TextView tst=(TextView)findViewById(R.id.textView3);
		TextView tet=(TextView)findViewById(R.id.textView4);
		TextView tt=(TextView)findViewById(R.id.textView5);
		int x=1;
		
		
		while(eventType!=XmlPullParser.END_DOCUMENT)
		{
			switch(eventType)
			{
			case XmlPullParser.START_DOCUMENT:
			{
				tsd.setText("Start Document");
			}
			case XmlPullParser.END_DOCUMENT:
			{
				ted.setText("End Document");
			}
			case XmlPullParser.START_TAG:
			{
				if(xpp.getName().equalsIgnoreCase("title"))
				{
					tst.setText(xpp.nextText());
				}				
			}
			case XmlPullParser.END_TAG:
			{
				tet.setText(xpp.getName());
			}
			case XmlPullParser.TEXT:
			{
				tt.setText("Text");
			}
			}
			eventType=xpp.next();
			
		}
	}
}


/*
InputStream raw = getApplicationContext().getAssets().open("image_of_the_day.xml");
xpp.setInput(raw, null);
*/