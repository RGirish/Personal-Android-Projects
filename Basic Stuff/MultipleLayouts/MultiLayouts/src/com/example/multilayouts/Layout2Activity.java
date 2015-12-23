package com.example.multilayouts;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class Layout2Activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout2);
	}
	
	public void onclick2(View view)
	{
		Intent intent= new Intent(this, Layout3Activity.class);
		startActivity(intent);
	}

}
