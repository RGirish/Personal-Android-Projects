
package com.iclub.locallogin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends ActionBarActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=openOrCreateDatabase("hello.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
    }

    public void login(View view){
        int k=0;
        EditText editText1=(EditText)findViewById(R.id.username_login);
        EditText editText2=(EditText)findViewById(R.id.password_login);
        String u=editText1.getText().toString();
        String p=editText2.getText().toString();

        Cursor cursor=db.rawQuery("SELECT * FROM users;", null);
        cursor.moveToFirst();

        while(true){
            String tempn = cursor.getString(0);
            String tempu = cursor.getString(1);
            String tempp = cursor.getString(2);

            if(tempu.equals(u) && tempp.equals(p)) {
                k++;
                Toast.makeText(this,"Welcome "+tempn,Toast.LENGTH_LONG).show();
                break;
            }
            if(cursor.isLast()) break;
            cursor.moveToNext();
        }

        if(k==0){
            Toast.makeText(this,"User does not exist!",Toast.LENGTH_LONG).show();
        }
    }
}