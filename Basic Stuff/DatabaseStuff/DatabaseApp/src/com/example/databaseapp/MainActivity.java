package com.example.databaseapp;

import java.io.IOException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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
	
	public void onclickbutton(View view)
	{
		EditText et=(EditText)findViewById(R.id.editText1);
		String name = null;
		name=et.toString();
		new InsertDataTask().execute(name);
	}
	
	private class InsertDataTask extends AsyncTask<String, Void, String> {
		
		
		@Override
		protected String doInBackground(String... args) {
			DataBaseHelper dd=new DataBaseHelper(getBaseContext());
			try {
				dd.createDataBase();
				dd.openDataBase();
				dd.insert(args[0]);
			} catch (IOException e) 
			{
				
			}
			dd.close();
			return args[0];
		}

	}
	
	
	public void onclickview(View view)
	{
		
	}

}
