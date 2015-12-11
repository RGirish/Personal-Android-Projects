package com.iclub.invisibletextview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    public void abcd(View view){
        TextView tv=new TextView(MainActivity.this);
        tv.setText("Hello!");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
            }
        });
        ll.addView(tv);
    }

}