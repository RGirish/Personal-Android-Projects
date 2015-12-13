package com.iclub.cognit14registration;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class CheckRegistration extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkhow);
	}
	
	public void s_cid(View v)
	{
		startActivity(new Intent(this, CheckRegistrationCid.class));
	}
	
	public void s_phone(View v)
	{
		startActivity(new Intent(this, CheckRegistrationPhone.class));
	}
	
	public void s_email(View v)
	{
		startActivity(new Intent(this, CheckRegistrationMail.class));
	}

}