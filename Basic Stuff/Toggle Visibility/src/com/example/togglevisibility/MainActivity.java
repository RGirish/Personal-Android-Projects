package com.example.togglevisibility;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void ONCLICK(View view)
	{
		TextView abc = (TextView) findViewById(R.id.textView1);
		float f=abc.getAlpha();
		if(f==0)
		{
			abc.setAlpha(1);
		}
		else if(f==1)
		{
			abc.setAlpha(0);
		}
	}
}
