
package com.example.bluetoothtry2;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity 
{

	public BluetoothAdapter mBluetoothAdapter;
	public AcceptThread at;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) 
		{
			Toast.makeText(this, "Device doesn't support Bluetooth!", Toast.LENGTH_LONG).show();
		}
		else
		{
			if(!mBluetoothAdapter.isEnabled())
			{
				Intent discoverableIntent = new	Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
				startActivity(discoverableIntent);
			}
		}
		
		
	}
	
	
	public void onclickcreate(View view)
	{
		if(mBluetoothAdapter.isEnabled())
		{
			at=new AcceptThread();
			at.start();
			Toast.makeText(this, "Listening for incoming connections...", Toast.LENGTH_LONG).show();
			Intent intent=new Intent(this,FingerPaint.class);
			startActivity(intent);
		}
		else
		{
			Toast.makeText(this, "Turn on Bluetooth and make discoverable!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onclickjoin(View view)
	{
		if(mBluetoothAdapter.isEnabled())
		{
			finish();
			Intent i= new Intent(this,NearbyDevices.class);
			startActivity(i);
		}
		else
		{
			Toast.makeText(this, "Turn on Bluetooth and make discoverable!", Toast.LENGTH_SHORT).show();
		}
	}
	
}
