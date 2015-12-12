
package com.iclub.aathmasparan;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Home extends FragmentActivity implements FetchDataListener{
	
	public SQLiteDatabase db;
	public ProgressDialog loading;
	public FrameLayout fl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.home);
		fl=(FrameLayout)findViewById(R.id.frag);
		
		changefrag((View)findViewById(R.id.home));
		
		db = openOrCreateDatabase("aathmasparan.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		createTables();
		//downloadAllDonorDetails();
	}
	
	public void createTables()
	{
		try{
			db.execSQL("CREATE TABLE donors(name TEXT,age TEXT,bloodgroup TEXT,state TEXT,city TEXT,date TEXT,phone TEXT);");
		}catch(Exception e){}
	}

	public void downloadAllDonorDetails()
	{
		loading=ProgressDialog.show(this, "Loading..", null);
		FetchDonorsTask task=new FetchDonorsTask(this,db);
		task.execute("http://aathmasparan.org/donors.asp");
	}
	
	public void onclicksearch(View v)
	{
		Spinner groups=(Spinner)findViewById(R.id.group);
		Spinner states=(Spinner)findViewById(R.id.state);
		String bg=groups.getSelectedItem().toString();
		String state=states.getSelectedItem().toString();
		try
		{
			Toast.makeText(this, bg, Toast.LENGTH_LONG).show();
			Toast.makeText(this, state, Toast.LENGTH_LONG).show();
			Cursor c=db.rawQuery("SELECT * FROM donors WHERE state='"+state+"' AND bloodgroup='"+bg+"'", null);
			c.moveToFirst();
			while(true)
			{
				String name,age,date,phone,city;
				name=c.getString(0);
				age=c.getString(1);
				city=c.getString(4);
				date=c.getString(5);
				phone=c.getString(6);
				addAnItem(name,age,date,phone,city);
				c.moveToNext();
				if(c.isAfterLast()) break;
			}
		}
		catch(Exception e){}
	}
	
	public void addAnItem(String name, String age, String date, String phone, String city)
	{
		
	}
	
	public void addNoDonorsMessage()
	{
		
	}
	
	public void changefrag(View view){
		
		
    	fl.removeAllViews();
			
		switch(view.getId()){
		
		case R.id.home :
			
			Homefrag hf = new Homefrag();
			hf.setArguments(getIntent().getExtras());
	        getSupportFragmentManager().beginTransaction().replace(R.id.frag,hf).commit();
			
			break;
		
		case R.id.contact :
			Contactfrag cf = new Contactfrag();
			cf.setArguments(getIntent().getExtras());
	        getSupportFragmentManager().beginTransaction().replace(R.id.frag,cf).commit();
		
			break;
		
		case R.id.donation :
			Donationfrag df = new Donationfrag();
			df.setArguments(getIntent().getExtras());
	        getSupportFragmentManager().beginTransaction().replace(R.id.frag,df).commit();
			
			break;
		
		case R.id.profile:
			Profilefrag pf = new Profilefrag();
			pf.setArguments(getIntent().getExtras());
	        getSupportFragmentManager().beginTransaction().replace(R.id.frag,pf).commit();
			
			break;
		
		case R.id.gallery :
			Galleryfrag gf= new Galleryfrag();
			gf.setArguments(getIntent().getExtras());
	        getSupportFragmentManager().beginTransaction().replace(R.id.frag,gf).commit();
			break;
		
		case R.id.search :
			Searchfrag sf = new Searchfrag();
			sf.setArguments(getIntent().getExtras());
	        getSupportFragmentManager().beginTransaction().replace(R.id.frag,sf).commit();
	        break;
		
		}
		
	}

	@Override
	public void onFetchComplete() {
		
	}

	@Override
	public void onFetchFailure(String msg) {
		
	}

	@Override
	public void onFetchComplete(String string) {
		
		if(string.equals("donors"))
		{
			loading.dismiss();
		}
		
	}
	
}
