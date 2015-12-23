package com.example.activity1layoutsmany;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Activity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.layout1, menu);
		return true;
	}

	
	public void function(View view)
	{
		setContentView(R.layout.layout2);
	}
	
	public void onBackPressed() 
	{
		Toast.makeText(this, "Pressed BACK Key",
		Toast.LENGTH_LONG).show();
	}
	
	
	public void function2(View view)
	{
		
		TextView tv=(TextView)findViewById(R.id.tttt);
		tv.setText("Ohhhh Yeeaaahhhhhh");
	}
	
	public void function1(View view)
	{
		setContentView(R.layout.layout3);
	}
	
	
}
