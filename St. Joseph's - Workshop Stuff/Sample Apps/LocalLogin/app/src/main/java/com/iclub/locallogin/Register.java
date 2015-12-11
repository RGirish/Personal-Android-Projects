package com.iclub.locallogin;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends ActionBarActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=openOrCreateDatabase("hello.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        try{
            db.execSQL("CREATE TABLE users(name TEXT, username TEXT, password TEXT);");
        }catch (Exception e){}
    }

    public void newuser(View view){
        EditText etname=(EditText)findViewById(R.id.name);
        EditText etuname=(EditText)findViewById(R.id.username);
        EditText etpassword=(EditText)findViewById(R.id.password);

        String name=etname.getText().toString();
        String username=etuname.getText().toString();
        String password=etpassword.getText().toString();

        db.execSQL("INSERT INTO users VALUES('"+name+"','"+username+"','"+password+"');");

        Toast.makeText(this,"New User Created.",Toast.LENGTH_LONG).show();

        finish();
    }

}
