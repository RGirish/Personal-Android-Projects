package com.iclub.sampleservice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startIt(View view){
        Intent intent=new Intent(this,MyService.class);
        startService(intent);
    }

    public void stopIt(View view){
        Intent intent=new Intent(this,MyService.class);
        stopService(intent);
    }
}