package com.example.wifichat1;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity
{
	WifiManager wifi_manager;
	WifiConfiguration wifi_configuration = null;
	public static String FLAG="";
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        wifi_manager = (WifiManager) this.getSystemService(WIFI_SERVICE);
    }
	
	public void onclickcreate(View v)
	{
		FLAG="AP";
	      wifi_manager.setWifiEnabled(false);
	      try
	      {
	         Method method=wifi_manager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
	         method.invoke(wifi_manager, wifi_configuration, true);
	         startActivity(new Intent(this,Connected.class));
	      }
	      catch(Exception e){}
	}
	
	public void onclickjoin(View v)
	{
		FLAG="JOIN";
		try
		{
			Method method=wifi_manager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			method.invoke(wifi_manager, wifi_configuration, false);
		}
		catch(Exception e){}
		startActivity(new Intent(this,Join.class));
	}
	
	@Override
	public void onDestroy()
	{
		try
		{
			Method method=wifi_manager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			method.invoke(wifi_manager, wifi_configuration, false);
		}
		catch(Exception e){}
		wifi_manager.setWifiEnabled(false);
		super.onDestroy();
	}
	
}
