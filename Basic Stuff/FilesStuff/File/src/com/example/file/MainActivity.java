package com.example.file;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.*;

public class MainActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@SuppressLint("SdCardPath")
	public void read_write(View view)
	{
		Button b=(Button)findViewById(R.id.button1);
		try
		{
					File ff=new File("/sdcard/android/data/xnos");
					if(!ff.exists())
					{
						ff.mkdir();
					}
					RandomAccessFile f=new RandomAccessFile("/sdcard/android/data/xnos/hey.dat","rw");
					f.writeChars("Girish");
					f.writeInt(25);
					f.writeFloat(12.11f);
					f.writeChar('g');
					f.seek(0);
					b.setText(f.readLine());
					f.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
}
