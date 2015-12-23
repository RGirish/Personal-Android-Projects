package com.example.file1;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

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
	
	@SuppressLint("SdCardPath")
	public void asd(View view)
	{
		try
		{
		File f=new File("/sdcard/qwe.txt");
		FileOutputStream fos=new FileOutputStream(f);
		DataOutputStream out=new DataOutputStream(fos);
		out.writeChars("Girish");
		out.writeInt(50);
		out.writeChars("Gayathri");
		out.writeDouble(25.54);
		out.close();
		fos.close();
		}catch(Exception e)
		{}
	}
	

}
