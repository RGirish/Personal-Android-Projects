package com.iclub.rgb;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll=(LinearLayout)findViewById(R.id.myLinearLayout);
    }

    public void changeToRed(View view){
        ll.setBackgroundColor(Color.RED);
        //Or, ll.setBackgroundColor(Color.parseColor("#ff0000"));
    }

    public void changeToGreen(View view){
        ll.setBackgroundColor(Color.GREEN);
        //Or, ll.setBackgroundColor(Color.parseColor("#00ff00"));
    }

    public void changeToBlue(View view){
        ll.setBackgroundColor(Color.BLUE);
        //Or, ll.setBackgroundColor(Color.parseColor("#0000ff"));
    }

}