
package com.example.bluetoothtry2;

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
	public ConnectThread ct;
	public ConnectedThread mConnectedThread;
	ProgressDialog pd;
	ArrayAdapter<String> foundDevices;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	static Context cc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_devices);
		cc=this;
		mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
		foundDevices= new ArrayAdapter<String>(this, R.layout.device_name);
		ListView newDevicesListView = (ListView) findViewById(R.id.nearby);
	    newDevicesListView.setAdapter(foundDevices);
	    newDevicesListView.setOnItemClickListener(mDeviceClickListener);
	    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    this.registerReceiver(mReceiver, filter);
	    filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	    this.registerReceiver(mReceiver, filter);		
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
                        
            NearbyDevices.this.unregisterReceiver(mReceiver);
            
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            
            ct = new ConnectThread(device);
            ct.start();
            finish();
            Intent intent=new Intent(cc,FingerPaint.class);
            startActivity(intent);
        }
    };
	
    
    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
        @Override
        public void onReceive(Context context, Intent intent) 
        {            
        	String action = intent.getAction();
        	
            if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                foundDevices.add(device.getName() + "\n" + device.getAddress());
                
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) 
            {
            	pd.dismiss();
            	if (foundDevices.getCount() == 0) 
                {
                    Toast.makeText(cc, "No devices found!", Toast.LENGTH_LONG).show();
                    TextView tv=(TextView)findViewById(R.id.textView1);
            		tv.setVisibility(View.INVISIBLE);
                }
            }
        }
    };
    
    
	
	public void onclickscan(View view)
	{
		TextView tv=(TextView)findViewById(R.id.textView1);
		tv.setVisibility(View.VISIBLE);
		pd=ProgressDialog.show(cc, "Scanning", "Please Wait...");
		mBluetoothAdapter.startDiscovery();
	}
	
	

}
