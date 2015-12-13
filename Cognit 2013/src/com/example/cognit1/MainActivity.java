
package com.example.cognit1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity
{
	
	public static String nAME,cOLLEGE,pHNO,eMAIL,eVENTS;

	public int flag=0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitleColor(Color.GRAY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addHomeFragment();
    }
    
    public void onBackPressed()
    {
    	if(flag==2)
    	{
    		super.onBackPressed();
    		flag=0;
    	}
    	else
    	{
    		finish();
    	}
    }
    
    private int CheckConnection()
	{
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo == null ) return 0;
        else return 1;
    }
    
    public void facebook(View v)
    {
		try 
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/cognit2013"));
			startActivity(browserIntent);
		} 
		catch (Exception e){Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();}
    }
    
    public void twitter(View v)
    {
		try 
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/cognit2013"));
			startActivity(browserIntent);
		} 
		catch (Exception e){Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();}
    }
    
    public void youtube(View v)
    {
		try 
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/cognit2013"));
			startActivity(browserIntent);
		} 
		catch (Exception e){Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();}
    	
    }
    
    public void gotomap(View view)
    {
    	try 
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?ie=UTF8&hq=&ll=12.94656,80.24446&z=16"));
			startActivity(browserIntent);
		} 
		catch (Exception e){Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();}	
    }
    
    public void gotowebsite(View v)
    {
    	try 
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cognit13.in"));
			startActivity(browserIntent);
		} 
		catch (Exception e){Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();}
    }
    
    public void open_dagu_fb(View v)
    {
    	try 
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/cognit13daguerreotype"));
			startActivity(browserIntent);
		} 
		catch (Exception e){Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();}
    }
    
    
    
    
    
    
    public void onclicksubmit(View view)
    {
    	
    	String NAME,COLLEGE,EMAIL,PHNO;
    	
    	EditText name_et=(EditText)findViewById(R.id.et_name);
    	EditText email_et=(EditText)findViewById(R.id.et_email);
    	EditText phno_et=(EditText)findViewById(R.id.et_phno);
    	EditText college_et=(EditText)findViewById(R.id.et_college);
    	
    	CheckBox cb1=(CheckBox)findViewById(R.id.checkBox1);
    	CheckBox cb2=(CheckBox)findViewById(R.id.checkBox2);
    	CheckBox cb3=(CheckBox)findViewById(R.id.checkBox3);
    	CheckBox cb4=(CheckBox)findViewById(R.id.checkBox4);
    	CheckBox cb5=(CheckBox)findViewById(R.id.checkBox5);
    	CheckBox cb6=(CheckBox)findViewById(R.id.checkBox6);
    	CheckBox cb7=(CheckBox)findViewById(R.id.checkBox7);
    	CheckBox cb8=(CheckBox)findViewById(R.id.checkBox8);
    	CheckBox cb9=(CheckBox)findViewById(R.id.checkBox9);
    	CheckBox cb10=(CheckBox)findViewById(R.id.checkBox10);
    	CheckBox cb11=(CheckBox)findViewById(R.id.checkBox11);
    	CheckBox cb12=(CheckBox)findViewById(R.id.checkBox12);
    	CheckBox cb13=(CheckBox)findViewById(R.id.checkBox13);
    	CheckBox cb14=(CheckBox)findViewById(R.id.checkBox14);
    	CheckBox cb15=(CheckBox)findViewById(R.id.checkBox15);
    	CheckBox cb17=(CheckBox)findViewById(R.id.checkBox17);

    	NAME=name_et.getText().toString();
    	EMAIL=email_et.getText().toString();
    	PHNO=phno_et.getText().toString();
    	COLLEGE=college_et.getText().toString();
    	
    	if(name_et.getText().toString().matches(""))
    	{
    		Toast.makeText(this, "Am sure you'll have a name, enter it!",  Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(college_et.getText().toString().matches(""))
    	{
    		Toast.makeText(this, "Don't feel shy, enter your college's name!",  Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(email_et.getText().toString().matches(""))
    	{
    		Toast.makeText(this, "We wont flood your inbox, enter your id!",  Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(phno_et.getText().toString().matches(""))
    	{
    		Toast.makeText(this, "We're not gonna make prank calls, enter your number!",  Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(!cb1.isChecked()&&!cb2.isChecked()&&!cb3.isChecked()&&!cb4.isChecked()&&!cb5.isChecked()&&!cb6.isChecked()&&!cb7.isChecked()&&!cb8.isChecked()&&!cb9.isChecked()&&!cb10.isChecked()&&!cb11.isChecked()&&!cb12.isChecked()&&!cb13.isChecked()&&!cb14.isChecked()&&!cb15.isChecked()&&!cb17.isChecked())
        {
        	Toast.makeText(this, "Dude, come on. You ought to participate in atleast ONE event.",  Toast.LENGTH_LONG).show();
         	return;
        }
    	if(PHNO.length()!=10)
     	{
     		Toast.makeText(this, "I guess all mobile numbers are 10 digits long!", Toast.LENGTH_LONG).show();
     		return;
     	}
     	if(!EMAIL.contains("@"))
     	{
     		Toast.makeText(this, "LOL. Your id doesn't have an '@' !", Toast.LENGTH_LONG).show();
     		return;
     	}
     	
    	String EVENTS="";
     	
    	if(cb1.isChecked()){ EVENTS=EVENTS+"Quiz, ";  }    
    	if(cb2.isChecked()){ EVENTS=EVENTS+"Googler, ";  }
    	if(cb3.isChecked()){ EVENTS=EVENTS+"Web Designing, ";  }
    	if(cb4.isChecked()){ EVENTS=EVENTS+"Paper Presentation, "; }
    	if(cb5.isChecked()){ EVENTS=EVENTS+"Coding and Debugging, "; }
    	if(cb6.isChecked()){ EVENTS=EVENTS+"Image Editing, "; }
    	if(cb7.isChecked()){ EVENTS=EVENTS+"PC Assembly, "; }
    	if(cb8.isChecked()){ EVENTS=EVENTS+"Linux Hunt, "; }
    	if(cb9.isChecked()){ EVENTS=EVENTS+"Debate, "; }
    	if(cb10.isChecked()){ EVENTS=EVENTS+"Adzap, "; }
    	if(cb11.isChecked()){ EVENTS=EVENTS+"Gaming, "; }
    	if(cb12.isChecked()){ EVENTS=EVENTS+"Surprise Event, "; }
    	if(cb13.isChecked()){ EVENTS=EVENTS+"Short Film Making, "; }
    	if(cb14.isChecked()){ EVENTS=EVENTS+"Mock Interview, "; }
    	if(cb15.isChecked()){ EVENTS=EVENTS+"Treasure Hunt, "; }
    	if(cb17.isChecked()){ EVENTS=EVENTS+"Dumb-C, "; }
    	
    	EVENTS=EVENTS.substring(0, EVENTS.length()-2)+".";
    	
    	Log.i("name",NAME);
    	Log.i("name",COLLEGE);
    	Log.i("name",PHNO);
    	Log.i("name",EMAIL);
    	Log.i("name",EVENTS);
    	
    	sendPostRequest(NAME,EMAIL,COLLEGE,PHNO,EVENTS);
    	
    	nAME=NAME;
    	cOLLEGE=COLLEGE;
    	pHNO=PHNO;
    	eMAIL=EMAIL;
    	eVENTS=EVENTS;
    	
    	addLoadingFragment();
    	
    	
    }
    
    
    
    public void sendPostRequest(String name,String email,String college,String phno,String EVENTS)
    {

		class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
		{
			
			String Name,Email,College,Phno,EVENTS;
			
			@Override
			protected String doInBackground(String... params) 
			{

				Name = params[0];
				Email = params[1];
				College = params[2];
				Phno = params[3];
				EVENTS = params[4];
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://www.cognit13.in/reg.php");
				BasicNameValuePair name_pair = new BasicNameValuePair("name", Name);
				BasicNameValuePair email_pair = new BasicNameValuePair("email", Email);
				BasicNameValuePair college_pair = new BasicNameValuePair("coll", College);
				BasicNameValuePair EVENTS_pair = new BasicNameValuePair("evts", EVENTS);
				BasicNameValuePair phno_pair = new BasicNameValuePair("phone", Phno);
				List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
				nameValuePairList.add(name_pair);
				nameValuePairList.add(college_pair);
				nameValuePairList.add(email_pair);
				nameValuePairList.add(phno_pair);
				nameValuePairList.add(EVENTS_pair);

				try 
				{

					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
					httpPost.setEntity(urlEncodedFormEntity);

					try 
					{
						
						HttpResponse httpResponse = httpClient.execute(httpPost);

						InputStream inputStream = httpResponse.getEntity().getContent();

						InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

						StringBuilder stringBuilder = new StringBuilder();

						String bufferedStrChunk = null;

						while((bufferedStrChunk = bufferedReader.readLine()) != null)
						{
							stringBuilder.append(bufferedStrChunk);
						}

						return stringBuilder.toString();

					} 
					catch (ClientProtocolException cpe) {} 
					catch (IOException ioe) {}

				} 
				catch (UnsupportedEncodingException uee) {}

				return null;
			}

			@Override
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);

				if(result.equals("working"))
				{
					Log.i("123",result);
				}
				else
				{
					Log.i("123",result);
					addAfterRegistrationFragment(Name,Email,College,Phno,EVENTS);
				}
			}
		}

		SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
		sendPostReqAsyncTask.execute(name, email, college, phno, EVENTS);
	
    }
    
    public void onclickregisteragain(View view)
    {
    	Button bt = (Button)findViewById(R.id.status_bar);
    	bt.setText("Register   ");
		bt.setBackgroundColor(getResources().getColor(R.color.register_color));
    	addRegisterFragment();
    }
    
    
       
    
    
    private void addHomeFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	HomeFragment firstFragment = new HomeFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).commit();
    }
    
    private void addTechFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	TechFragment secondFragment = new TechFragment();
    	secondFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, secondFragment).commit();
    }
    
    private void addNonTechFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	NonTechFragment firstFragment = new NonTechFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).commit();
    }
    
    private void addOnlineFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	OnlineFragment secondFragment = new OnlineFragment();
    	secondFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, secondFragment).commit();
    }
    
    private void addAfterRegistrationFragment(String NAME, String EMAIL, String COLLEGE, String PHNO, String EVENTS)
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	AfterRegistrationFragment firstFragment = new AfterRegistrationFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).commit();
    }
    
    private void addEmptyFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	EmptyFragment firstFragment = new EmptyFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).commit();
    }
    
    private void addLoadingFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	LoadingFragment firstFragment = new LoadingFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).commit();
    }
    
    private void addWorkshopFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	WorkshopFragment secondFragment = new WorkshopFragment();
    	secondFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, secondFragment).commit();
    }
    
    private void addRegisterFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	RegisterFragment firstFragment = new RegisterFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).commit();
    }
    
    private void addContactFragment()
    {
    	flag=0;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	ContactFragment secondFragment = new ContactFragment();
    	secondFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, secondFragment).commit();
    }
    
    public void onclickfunc(View v)
    {
    	Button bt = (Button)findViewById(R.id.status_bar);
    	switch(v.getId())
    	{
    	
    	case R.id.button1:
    		
    		addHomeFragment();
    		bt.setText("Home   ");
    		bt.setBackgroundColor(getResources().getColor(R.color.home_color));
    		
    		break;
    	case R.id.button2:
    	
    		addTechFragment();
    		bt.setText("Technical Events   ");
    		bt.setBackgroundColor(getResources().getColor(R.color.tech_color));
    		
    		break;
    	
    	case R.id.button3:
    		
    		addNonTechFragment();
    		bt.setText("Non-Technical Events   ");
    		bt.setBackgroundColor(getResources().getColor(R.color.nontech_color));
    		
    		break;
    		
    	case R.id.buttonOL:
    		
    		addOnlineFragment();
    		bt.setText("Online Events   ");
    		bt.setBackgroundColor(getResources().getColor(R.color.online_color));
    		
    		break;
    		
    	case R.id.button4:
    		
    		addWorkshopFragment();
    		bt.setText("Workshops   ");
    		bt.setBackgroundColor(getResources().getColor(R.color.workshop_color));
    		
    		break;
    	
    	case R.id.button5:
    	
    		if(CheckConnection()==1)
    		{
    			addRegisterFragment();
        		bt.setText("Register   ");
        		bt.setBackgroundColor(getResources().getColor(R.color.register_color));
    		}
    		else
    		{
    			addEmptyFragment();
    			Toast.makeText(this,"Check your internet connection!", Toast.LENGTH_SHORT).show();
    			bt.setText("Register   ");
        		bt.setBackgroundColor(getResources().getColor(R.color.register_color));
    		}
    		
    		break;
    		
    	case R.id.button6:
    		
    		addContactFragment();
    		bt.setText("Contact us   ");
    		bt.setBackgroundColor(getResources().getColor(R.color.contact_color));
    		
    		break;
    	}
    }
    
    
    
    public void paper(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	PaperPresentationFragment firstFragment = new PaperPresentationFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    
    public void adzap(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	AdzapFragment firstFragment = new AdzapFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();	
    }
    public void debate(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
	    DebateFragment firstFragment = new DebateFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void quiz(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	QuizFragment firstFragment = new QuizFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void web(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	WebDesigningFragment firstFragment = new WebDesigningFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void googler(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	GooglerFragment firstFragment = new GooglerFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void pcassembly(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
        PcAssemblyFragment firstFragment = new PcAssemblyFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void linux(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
	    LinuxHuntFragment firstFragment = new LinuxHuntFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void debugging(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	DebuggingFragment firstFragment = new DebuggingFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void imageediting(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	ImageEditingFragment firstFragment = new ImageEditingFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void dumbc(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	DumbcFragment firstFragment = new DumbcFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void mock(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	MockInterviewFragment firstFragment = new MockInterviewFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void gaming(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	GamingFragment firstFragment = new GamingFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void photography(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	PhotographyFragment firstFragment = new PhotographyFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    
    public void olcoding(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	OnlineCodingFragment firstFragment = new OnlineCodingFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    
    public void shortfilm(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	ShortFilmFragment firstFragment = new ShortFilmFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void surprise(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	SurpriseEventFragment firstFragment = new SurpriseEventFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    public void treasure(View v)
    {
    	flag=2;
    	FrameLayout fl=(FrameLayout)findViewById(R.id.fragment_container);
    	fl.removeAllViews();
    	TreasureHuntFragment firstFragment = new TreasureHuntFragment();
        firstFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }
    
}

