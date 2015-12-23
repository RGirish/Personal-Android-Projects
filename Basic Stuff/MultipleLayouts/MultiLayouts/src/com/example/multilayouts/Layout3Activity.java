package com.example.multilayouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Layout3Activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout3);
	}
	
	public void onclick3(View view)
	{
		Intent intent= new Intent(this, Layout1Activity.class);
		startActivity(intent);
	}
	
}
