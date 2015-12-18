
package com.example.letmetellyou;

import java.util.Date;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener
{

	public static TextToSpeech tts;
	static Context cc;
	SettingsContentObserver mSettingsContentObserver;
	String pattern="UDDU",now="****";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent(this,MyService.class));
		cc=this;
		tts = new TextToSpeech(this, this);
		
		
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		RelativeLayout rl=(RelativeLayout)findViewById(R.id.RELATIVE);
		rl.setOnTouchListener(activitySwipeDetector);
	}

	@Override
	public void onInit(int status) {

	    if (status == TextToSpeech.SUCCESS) {

	        int result = tts.setLanguage(Locale.US);
	        tts.setSpeechRate(0.75f);

	        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
	        {
	            Log.e("TTS", "This Language is not supported");
	        } 
	        else 
	        {
	        	speakOut();
	        }

	    } else {
	        Log.e("TTS", "Initilization Failed!");
	    }

	}


	@Override
	public void onDestroy() 
	{
	    if (tts != null) {
	        tts.stop();
	        tts.shutdown();
	    }
	     
	    super.onDestroy();
	}
	
	
	public void speakOut()
	{
		tts.setLanguage(Locale.US);
		tts.speak("The application has been opened.", TextToSpeech.QUEUE_FLUSH, null);
	}

	@SuppressWarnings("deprecation")
	public static void speakOutTheDay()
	{
		Date d=new Date();
		tts.setLanguage(Locale.US);
		String speakthis = null;
		switch(d.getDay())
		{
			case 1:
				speakthis="Monday";
				break;
			case 2:
				speakthis="Tuesday";	
				break;
			case 3:
				speakthis="Wednesday";
				break;
			case 4:
				speakthis="Thursday";
				break;
			case 5:
				speakthis="Friday";
				break;
			case 6:
				speakthis="Saturday";
				break;
			case 7:
				speakthis="Sunday";
				break;
			
		}
		tts.speak(speakthis, TextToSpeech.QUEUE_FLUSH, null);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void speakOutTheTime()
	{
		Date d=new Date();
		tts.setLanguage(Locale.US);
		String speakthis = d.getHours()+" hours "+d.getMinutes()+" minutes "+d.getSeconds()+" seconds ";
		tts.speak(speakthis, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	
	@SuppressWarnings("deprecation")
	public static void speakOutTheDate()
	{
		Date d=new Date();
		tts.setLanguage(Locale.US);
		String month = null;
		String speakthis;
		
		switch(d.getMonth())
		{
			case 0:
				month="January";
				break;
			case 1:
				month="February";
				break;
			case 2:
				month="March";
				break;
			case 3:
				month="April";
				break;
			case 4:
				month="May";
				break;
			case 5:
				month="June";
				break;
			case 6:
				month="July";
				break;
			case 7:
				month="August";
				break;
			case 8:
				month="September";
				break;
			case 9:
				month="October";
				break;
			case 10:
				month="November";
				break;
			case 11:
				month="December";
				break;
		
		}
		if(d.getDate()==1||d.getDate()==21||d.getDate()==31)
		{
			speakthis=d.getDate()+"st of "+month+" "+Integer.toString(d.getYear()).substring(1);
		}
		else if(d.getDate()==2||d.getDate()==22)
		{
			speakthis=d.getDate()+"nd of "+month+" "+Integer.toString(d.getYear()).substring(1);
		}
		else if(d.getDate()==3||d.getDate()==23)
		{
			speakthis=d.getDate()+"rd of "+month+" "+Integer.toString(d.getYear()).substring(1);
		}
		else
		{
			speakthis=d.getDate()+"th "+month+" "+Integer.toString(d.getYear()).substring(1);
		}	
		tts.speak(speakthis, TextToSpeech.QUEUE_FLUSH, null);
	}

	
	
	
}
