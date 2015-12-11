package com.iclub.spinnerlistener;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner countries = (Spinner)findViewById(R.id.countries);
        final Spinner states = (Spinner)findViewById(R.id.states);

        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter adapter;
                if(countries.getSelectedItem().toString().equals("INDIA")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.india_states,R.layout.spinner_item);
                }else if(countries.getSelectedItem().toString().equals("USA")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.us_states,R.layout.spinner_item);
                }else{
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.canada_states,R.layout.spinner_item);
                }
                states.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }


}