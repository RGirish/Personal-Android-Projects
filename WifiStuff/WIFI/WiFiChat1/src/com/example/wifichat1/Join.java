
package com.example.wifichat1;

import java.util.List;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Join extends Activity
{
	EditText et;
	LinearLayout msglist;
	Button send;
    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;
    LinearLayout mainll;
    WifiConfiguration conf = new WifiConfiguration();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availablenetworks);
        
         mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
         mainll=(LinearLayout)findViewById(R.id.mainll);
         if (mainWifi.isWifiEnabled() == false)
         {  
             Toast.makeText(getApplicationContext(), "Enabling WiFi", Toast.LENGTH_LONG).show();
             mainWifi.setWifiEnabled(true);
         } 
         receiverWifi = new WifiReceiver();
         registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
           mainWifi.startScan();
           Toast.makeText(this, "Starting to Scan", Toast.LENGTH_LONG).show();
           
        }
    
        public boolean onCreateOptionsMenu(Menu menu) {
            menu.add(0, 0, 0, "Refresh");
            return super.onCreateOptionsMenu(menu);
        }
    
        public boolean onMenuItemSelected(int featureId, MenuItem item) {
            mainWifi.startScan();
            Toast.makeText(this, "Starting to Scan", Toast.LENGTH_LONG).show();
            return super.onMenuItemSelected(featureId, item);
        }
    
        protected void onPause() {
            unregisterReceiver(receiverWifi);
            setProgressBarIndeterminateVisibility(false);
            super.onPause();
        }
    
        protected void onResume() {
            registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            super.onResume();
        }
            
        class WifiReceiver extends BroadcastReceiver {
    
            public void onReceive(Context c, Intent intent)
            {
            	mainll.removeAllViews();
                wifiList = mainWifi.getScanResults();
                for(int i = 0; i < wifiList.size(); i++)
                {
                    Button b=new Button(Join.this);
                    b.setTextColor(Color.WHITE);
                    b.setTag(Integer.toString(i));
                    b.setText((wifiList.get(i)).toString());
                    b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                    
                    b.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Button bb=(Button)v;
							conf.SSID = "\"" + wifiList.get(Integer.parseInt(bb.getTag().toString())).SSID + "\"";
							conf.preSharedKey = String.format("\"%s\"", "micromax");
							int netId = mainWifi.addNetwork(conf); 
							mainWifi.disconnect();
							mainWifi.enableNetwork(netId, true);
							mainWifi.reconnect();
							startActivity(new Intent(Join.this,Connected.class));
							finish();
						}
					});
                    
                    mainll.addView(b);
                }
    
            }
    
        }
    }
