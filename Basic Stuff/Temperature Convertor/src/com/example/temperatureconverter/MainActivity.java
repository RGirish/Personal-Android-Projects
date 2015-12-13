package com.example.temperatureconverter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public void onClickrb1(View view)
    {
    	RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.radioButton2);
    	RadioButton celsiusButton = (RadioButton) findViewById(R.id.radioButton1);
    	fahrenheitButton.setChecked(false);
    	celsiusButton.setChecked(true);
    }
    
    public void onClickrb2(View view)
    {
    	RadioButton celsiusButton = (RadioButton) findViewById(R.id.radioButton1);
    	RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.radioButton2);
        celsiusButton.setChecked(false);
        fahrenheitButton.setChecked(true);
    }
    
 // This method is called at button click because we assigned the name to the
    // "OnClick property" of the button
    public void onClickb(View view) {
    EditText text = (EditText) findViewById(R.id.editText1);
	switch (view.getId()) {
     case R.id.button1:
        RadioButton celsiusButton = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.radioButton2);
        if (text.getText().length() == 0) {
          Toast.makeText(this, "Please enter a valid number",
              Toast.LENGTH_LONG).show();
          return;
        }

        float inputValue = Float.parseFloat(text.getText().toString());
        if (celsiusButton.isChecked()) {
          text.setText(String
              .valueOf(convertFahrenheitToCelsius(inputValue)));
          celsiusButton.setChecked(false);
          fahrenheitButton.setChecked(true);
        } else {
          text.setText(String
              .valueOf(convertCelsiusToFahrenheit(inputValue)));
          fahrenheitButton.setChecked(false);
          celsiusButton.setChecked(true);
        }
        break;
      }
    }

    // Converts to celsius
    private float convertFahrenheitToCelsius(float fahrenheit) {
      return ((fahrenheit - 32) * 5 / 9);
    }

    // Converts to fahrenheit
    private float convertCelsiusToFahrenheit(float celsius) {
      return ((celsius * 9) / 5) + 32;
    }
    
    
}
