package com.example.rgb;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
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
	
	public void ONCLICK(View view)
	{
		RelativeLayout o1=(RelativeLayout)findViewById(R.id.mainlayout);
		Resources res=getResources();
		int a;
		switch(view.getId())
		{
			
			case R.id.button1:
				a=res.getColor(R.color.Yellow);
				o1.setBackgroundColor(a);
				break;
			case R.id.button2:
				a=res.getColor(R.color.Greenishblue);
				o1.setBackgroundColor(a);
				break;
			case R.id.button3:
				a=res.getColor(R.color.Pink);
				o1.setBackgroundColor(a);
				break;
			case R.id.button4:
				a=res.getColor(R.color.Grey);
				o1.setBackgroundColor(a);
				break;
			case R.id.button5:
				a=res.getColor(R.color.Black);
				o1.setBackgroundColor(a);
				break;
			case R.id.button6:
				a=res.getColor(R.color.White);
				o1.setBackgroundColor(a);
				break;
			case R.id.button7:
				a=res.getColor(R.color.Red);
				o1.setBackgroundColor(a);
				break;
			case R.id.button8:
				a=res.getColor(R.color.Green);
				o1.setBackgroundColor(a);
				break;
			case R.id.button9:
				a=res.getColor(R.color.Blue);
				o1.setBackgroundColor(a);
				break;
		}
	}

}
