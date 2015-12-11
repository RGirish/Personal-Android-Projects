package com.iclub.scrollview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll=(LinearLayout)findViewById(R.id.myLinearLayout);
    }

    public void addToScrollView(View view){
        TextView textView=new TextView(this);
        textView.setText("New Text View");
        textView.setTextSize(24);
        ll.addView(textView);
    }

}