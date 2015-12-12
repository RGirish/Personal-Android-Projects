package com.iclub.cognit14registration;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setTitle("Registration");
		
	}
	
	public void newentry(View v)
	{
		startActivity(new Intent(this,NewEntry.class));
	}
	
	public void checkregistration(View v)
	{
		startActivity(new Intent(this,CheckRegistration.class));
	}
	
}