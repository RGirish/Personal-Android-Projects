package com.example.bluetoothtry1;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NearbyDevices extends Activity 
{
	
	BluetoothAdapter mBluetoothAdapter;
	ProgressDialog pd;
	ArrayAdapter<String> list_view_adapter;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	Context cc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_devices);
		cc=this;
		
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
			
			list_view_adapter = new ArrayAdapter<String>(this, R.layout.device_name);
			ListView newDevicesListView = (ListView) findViewById(R.id.nearby);
	        newDevicesListView.setAdapter(list_view_adapter);
	        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
	        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	        this.registerReceiver(mReceiver, filter);
	        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	        this.registerReceiver(mReceiver, filter);

		}
		
	}
	
	@Override
    protected void onDestroy() 
	{
        super.onDestroy();
        finish();        
    }

	
	public void onBackPressed()
	{
		finish();
	}
	
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() 
	{
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) 
        {
        	mBluetoothAdapter.cancelDiscovery();
        	
        	String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            
            setResult(Activity.RESULT_OK, intent);
            
            if (mBluetoothAdapter != null) 	mBluetoothAdapter.cancelDiscovery();
            cc.unregisterReceiver(mReceiver);
            Intent i=new Intent(cc,FingerPaint.class);
            finish();
            startActivity(i);
        
        
        }
    };
	
	
	
	public void onclickscan(View view)
	{
		TextView tv=(TextView)findViewById(R.id.textView1);
		tv.setVisibility(View.VISIBLE);
		pd=ProgressDialog.show(this, "Scanning","Please Wait..", true);
		mBluetoothAdapter.startDiscovery();
	}
	
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() 
	{
        @Override
        public void onReceive(Context context, Intent intent) 
        {            
        	String action = intent.getAction();
        	
            if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                list_view_adapter.add(device.getName() + "\n" + device.getAddress());
                
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) 
            {
            	pd.dismiss();
            	if (list_view_adapter.getCount() == 0) 
                {
                    String noDevices = "No devices found!";
                    list_view_adapter.add(noDevices);
                }
            }
        }
    };

}
