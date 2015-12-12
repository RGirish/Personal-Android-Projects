package com.iclub.cognit14registration;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class CheckRegistrationPhone extends Activity implements FetchDataListener
{
	
	ProgressDialog dialog;
	LinearLayout ll;
	String namE,collegE,phonE,ciD,riD;
	int yeaR,depT;
	public Context c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkregistrationphone);
		ll=(LinearLayout)findViewById(R.id.details_ll);
		c=this;
	}
	
	public void onclicksearch(View v)
	{
		InputMethodManager inputManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		
		ll.removeAllViews();
		dialog = ProgressDialog.show(this,"","Please Wait..");
		
		if(checkConnection())
		{
			EditText et=(EditText)findViewById(R.id.cid_et);
			String phone=et.getText().toString();
			if(phone.length()==10){
			FetchDetails task1 = new FetchDetails(this);
			task1.execute("http://quillonparchment.in/cognit14/returndetailsphone.php?phone="+phone);
			}else{
				Toast.makeText(this, "Enter a valid phone number.", Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		}
		else
		{
			Toast.makeText(this, "Turn on Mobile Data!", Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
		
	}
	
	public boolean checkConnection() 
	{
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo == null ) return false;
        else return true;
    }
	

	@Override
	public void onFetchComplete() {
		
	}

	@Override
	public void onFetchFailure(String msg) {

	}

	
	@Override
	public void onFetchComplete(String string)
	{
		String[] details=string.split(";");
		
		if(details.length<5)
		{
			dialog.dismiss();
			Toast.makeText(this, "No data available!", Toast.LENGTH_LONG).show();
			return;
		}
		
		LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.gravity=Gravity.CENTER;
		LayoutParams params_=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params_.gravity=Gravity.CENTER;
		params_.setMargins(0, 20, 0, 0);
		
		TextView cid=new TextView(ll.getContext());
		cid.setText("CID");
		cid.setLayoutParams(params_);
		cid.setTextColor(Color.DKGRAY);
		cid.setTextSize(20);
		TextView cid2=new TextView(ll.getContext());
		cid2.setText(details[0]);
		ciD=details[0];
		cid2.setLayoutParams(params);
		cid2.setTextColor(Color.BLACK);
		cid2.setTextSize(18);
		
		TextView name=new TextView(ll.getContext());
		name.setText("NAME");
		name.setLayoutParams(params_);
		name.setTextColor(Color.DKGRAY);
		name.setTextSize(20);
		TextView name2=new TextView(ll.getContext());
		name2.setText(details[1]);
		namE=details[1];
		name2.setLayoutParams(params);
		name2.setTextColor(Color.BLACK);
		name2.setTextSize(18);
		
		TextView college=new TextView(ll.getContext());
		college.setText("COLLEGE");
		college.setLayoutParams(params_);
		college.setTextColor(Color.DKGRAY);
		college.setTextSize(20);
		TextView college2=new TextView(ll.getContext());
		college2.setText(details[2]);
		collegE=details[2];
		college2.setLayoutParams(params);
		college2.setTextColor(Color.BLACK);
		college2.setTextSize(18);
		
		TextView year=new TextView(ll.getContext());
		year.setLayoutParams(params_);
		year.setText("YEAR");
		year.setTextColor(Color.DKGRAY);
		year.setTextSize(20);
		TextView year2=new TextView(ll.getContext());
		year2.setText(details[3]);
		yeaR=Integer.parseInt(details[3]);
		year2.setTextColor(Color.BLACK);
		year2.setLayoutParams(params);
		year2.setTextSize(18);
		
		TextView dept=new TextView(ll.getContext());
		dept.setText("DEPT");
		dept.setLayoutParams(params_);
		dept.setTextColor(Color.DKGRAY);
		dept.setTextSize(20);
		TextView dept2=new TextView(ll.getContext());
		dept2.setText(details[4]);
		String d=details[4];
		
		if(d.equals("CSE")) depT=0;
		else if(d.equals("IT")) depT=1;
		else if(d.equals("ECE")) depT=2;
		else if(d.equals("EEE")) depT=3;
		else if(d.equals("Mech")) depT=4;
		else if(d.equals("Civil")) depT=5;
		else if(d.equals("EnI")) depT=6;
		else if(d.equals("Bio-Tech")) depT=7;
		else if(d.equals("Chemical")) depT=8;
		else if(d.equals("Automobile")) depT=9;
		else if(d.equals("Arch")) depT=10;
		else if(d.equals("Other")) depT=11;
		
		dept2.setLayoutParams(params);
		dept2.setTextColor(Color.BLACK);
		dept2.setTextSize(18);
				
		ll.addView(cid);
		ll.addView(cid2);
		ll.addView(name);
		ll.addView(name2);
		ll.addView(college);
		ll.addView(college2);
		ll.addView(year);
		ll.addView(year2);
		ll.addView(dept);
		ll.addView(dept2);
		
		dialog.dismiss();
	}
}