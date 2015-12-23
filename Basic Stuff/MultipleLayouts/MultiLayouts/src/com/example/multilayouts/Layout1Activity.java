package com.example.multilayouts;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Layout1Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.layout1, menu);
		return true;
	}
	
	public void onclick1(View view)
	{
		Intent intent= new Intent(this, Layout2Activity.class);
		startActivity(intent);
	}
	
}
