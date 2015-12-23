package com.example.xmlparsing;

import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class StackOverflowXmlParser 
{

   public String parse(InputStream in) throws XmlPullParserException, IOException {
        try {
        	XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
    		factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    
    
	private String readFeed(XmlPullParser parser) throws XmlPullParserException, IOException 
	{
		StringBuilder sb=new StringBuilder();
		String name;
	//	MyData data = new MyData();
		int eventType=parser.getEventType();
		//String MESSAGE= new String();
 		while(parser.next()!=XmlPullParser.END_DOCUMENT)
 		{
 			if(eventType==XmlPullParser.START_TAG)
 			{
 				name=parser.getName();
 				if(name.equalsIgnoreCase("title"))
 				{
 					//data.TITLE=parser.nextText();
 					sb.append(parser.nextText());
 					parser.nextTag();
 					//MESSAGE.concat(data.TITLE);
 					//MESSAGE.concat(" ");
 				}
 				else if(name.equalsIgnoreCase("pubdate"))
 				{
 					sb.append(parser.nextText());
 					parser.nextTag();
 					//data.DATE=parser.nextText();
 					//MESSAGE.concat(data.TITLE);
 					//MESSAGE.concat(" ");
 				}
 				else if(name.equalsIgnoreCase("description"))
 				{
 					sb.append(parser.nextText());
 					parser.nextTag();
 					//data.DESC=parser.nextText();
 					//MESSAGE.concat(data.TITLE);
 					//MESSAGE.concat(" ");
 				}
 			}
 			eventType=parser.next();
 		}
		return sb.toString();
	}
}










/*
parser.require(XmlPullParser.START_TAG, ns, "rss");
while (parser.next() != XmlPullParser.END_TAG) 
{
    if (parser.getEventType() != XmlPullParser.START_TAG) 
    {
        continue;
    }
    String name = parser.getName();
    if (name.equals("channel")) 
    {
    	parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) 
        {
            if (parser.getEventType() != XmlPullParser.START_TAG) 
            {
                continue;
            }
            String namee = parser.getName();
            if (namee.equals("item")) 
            {
            	
            	int eventType=parser.getEventType();
        		
        		TextView tsd=(TextView)findViewById(R.id.textView1);
        		TextView ted=(TextView)findViewById(R.id.textView2);
        		TextView tst=(TextView)findViewById(R.id.textView3);
        		TextView tet=(TextView)findViewById(R.id.textView4);
        		TextView tt=(TextView)findViewById(R.id.textView5);
        		
        		while(eventType!=XmlPullParser.END_DOCUMENT)
        		{
        			if(eventType==XmlPullParser.START_DOCUMENT)
        			{
        				tsd.setText("Start Document");
        			}
        			else if(eventType==XmlPullParser.END_DOCUMENT)
        			{
        				ted.setText("End Document");
        			}
        			else if(eventType==XmlPullParser.START_TAG)
        			{
        				tst.setText("Start Tag");
        			}
        			else if(eventType==XmlPullParser.END_TAG)
        			{
        				tet.setText("End Tag");
        			}
        			else if(eventType==XmlPullParser.TEXT)
        			{
        				tt.setText("Text");
        			}
        			eventType=parser.next();
        		}
            	
            	
            } 
            else 
            {
                skip(parser);
            }
        }  
    } 
    else 
    {
        skip(parser);
    }
}  */




/* parser.require(XmlPullParser.START_TAG, ns, "channel");
 while (parser.next() != XmlPullParser.END_TAG) 
 {
     if (parser.getEventType() != XmlPullParser.START_TAG) 
     {
         continue;
     }
     String name = parser.getName();
     if (name.equals("item")) 
     {*/
    	      	
             	
             	
     /*} 
     else 
     {
    	 skip(parser);
     }
 }  */
                  





/* private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
if (parser.getEventType() != XmlPullParser.START_TAG) {
    throw new IllegalStateException();
}
int depth = 1;
while (depth != 0) {
    switch (parser.next()) {
    case XmlPullParser.END_TAG:
        depth--;
        break;
    case XmlPullParser.START_TAG:
        depth++;
        break;
    }
}
}*/