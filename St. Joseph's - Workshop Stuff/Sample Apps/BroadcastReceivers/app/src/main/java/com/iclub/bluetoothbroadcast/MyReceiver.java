package com.iclub.bluetoothbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"Phone Connected to power supply!",Toast.LENGTH_LONG).show();
    }

}