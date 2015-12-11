package com.iclub.tictactoe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    String character;
    Button b1,b2,b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        character="X";
        b1=(Button)findViewById(R.id.one);
        b2=(Button)findViewById(R.id.two);
        b3=(Button)findViewById(R.id.three);
        b4=(Button)findViewById(R.id.four);
    }

    public void doIt(View view){
        Button b=(Button)view;
        b.setClickable(false);
        b.setText(character);
        if(character.equals("X"))character="O";
        else character="X";
        check();
    }

    public void check(){

        String s1 = b1.getText().toString();
        String s2 = b2.getText().toString();
        String s3 = b3.getText().toString();
        String s4 = b4.getText().toString();

        if( s1.equals(s2) && !s1.equals("")){
            announceGameResult();
        }
        else if( s3.equals(s4) && !s3.equals("")){
            announceGameResult();
        }
        else if( s1.equals(s3) && !s1.equals("")){
            announceGameResult();
        }
        else if( s4.equals(s2) && !s4.equals("")){
            announceGameResult();
        }
        else if( s1.equals(s4) && !s1.equals("")){
            announceGameResult();
        }
        else if( s2.equals(s3) && !s2.equals("")){
            announceGameResult();
        }

    }

    public void announceGameResult(){
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);
        Toast.makeText(MainActivity.this,"Game Over",Toast.LENGTH_LONG).show();
    }

}