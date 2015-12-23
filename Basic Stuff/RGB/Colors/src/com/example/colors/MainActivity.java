package com.example.colors;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

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
	
	
	public void what_happens_when_i_click(View abc)
	{
		RelativeLayout rl=(RelativeLayout) findViewById(R.id.my_layout);
		switch(abc.getId())
		{
		
			case R.id.button1:
				rl.setBackgroundColor(getResources().getColor(R.color.black));
				break;
			case R.id.button2:
				rl.setBackgroundColor(getResources().getColor(R.color.grey));
				break;
			case R.id.button3:
				rl.setBackgroundColor(getResources().getColor(R.color.blue));
				break;
			case R.id.button4:
				rl.setBackgroundColor(getResources().getColor(R.color.red));
				break;
			case R.id.button5:
				rl.setBackgroundColor(getResources().getColor(R.color.white));
				break;
			case R.id.button6:
				rl.setBackgroundColor(getResources().getColor(R.color.green));
				break;
			
		}
	}
}
