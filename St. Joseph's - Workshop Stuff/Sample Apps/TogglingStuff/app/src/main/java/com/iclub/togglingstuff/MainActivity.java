
package com.iclub.togglingstuff;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    WifiManager wifiManager;
    BluetoothAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        adapter = BluetoothAdapter.getDefaultAdapter();
    }





    public void toggleBluetooth(View view){
        if(adapter.getState() == BluetoothAdapter.STATE_ON) {
            adapter.disable();
        }else if (adapter.getState() == BluetoothAdapter.STATE_OFF){
            adapter.enable();
        }

    }

    public void toggleWifi(View view){
        if(!wifiManager.isWifiEnabled()) wifiManager.setWifiEnabled(true);
        else wifiManager.setWifiEnabled(false);
    }
}