package com.example.multiplescreens;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Screen1Activity extends Activity {

	float x=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen1, menu);
		return true;
	}

	public void function(View view)
	{
		switch(view.getId())
		{
			case R.id.button3:
				
				EditText e=(EditText)findViewById(R.id.input);
				x+=Float.parseFloat(e.getText().toString());
				e.setText("");
				break;
			
			case R.id.button1:	
				EditText ee=(EditText)findViewById(R.id.input);
				x+=Float.parseFloat(ee.getText().toString());
				TextView t=(TextView)findViewById(R.id.result);
				t.setText(Float.toString(x));
				break;
				
		}
		
		
			
		
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setMessage("Press yes or no:");
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
		new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
		
			Toast.makeText(Screen1Activity.this, "yes was pressed" , Toast.LENGTH_LONG).show();
			
		}
		});
		
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
			
			Toast.makeText(Screen1Activity.this, "no was pressed" , Toast.LENGTH_LONG).show();
			
		}
		});
		
		dialog.show();
		
		
		
		
		
	}
	
}

