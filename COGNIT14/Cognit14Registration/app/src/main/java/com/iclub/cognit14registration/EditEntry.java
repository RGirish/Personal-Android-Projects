
package com.iclub.cognit14registration;

import java.net.URI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditEntry extends Activity implements FetchDataListener
{

	String name,college,phone,dept,rid,year;
	public static ProgressDialog dialog;
	EditText ETname,ETcollege,ETemail,ETphone,ETcid,ETrid;
	Spinner Syear,Sdept;
	String CID;
	TextView TVcid;
	
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.newentry);
		ETname=(EditText)findViewById(R.id.name);
		ETcollege=(EditText)findViewById(R.id.college);
		ETphone=(EditText)findViewById(R.id.phone);
		ETcid=(EditText)findViewById(R.id.cid);
		TVcid=(TextView)findViewById(R.id.tvcid);
		ETrid=(EditText)findViewById(R.id.rid);
		Sdept=(Spinner)findViewById(R.id.dept);
		Syear=(Spinner)findViewById(R.id.year);
		
		
		Intent i = getIntent();
		
		ETname.setText(i.getStringExtra("name"));
		ETcollege.setText(i.getStringExtra("college"));
		ETphone.setText(i.getStringExtra("phone"));
		ETcid.setVisibility(View.GONE);
		TVcid.setVisibility(View.GONE);
		CID=i.getStringExtra("cid");
		
		if(!i.getStringExtra("rid").equals("0")) ETrid.setText(i.getStringExtra("rid"));
		
		Sdept.setSelection(i.getIntExtra("dept",102));
		Syear.setSelection(i.getIntExtra("year",102)-1);
	}
	
	private boolean checkConnection()
	{
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo == null ) return false;
        else return true;
    }
	
	public void onclicksubmit(View v)
	{		
		name=ETname.getText().toString();
		college=ETcollege.getText().toString();
		phone=ETphone.getText().toString();
		rid=ETrid.getText().toString();
		dept=Sdept.getSelectedItem().toString();
		year=Syear.getSelectedItem().toString();
		
	
		if(name.length()<1)
		{
			Toast.makeText(EditEntry.this, "Enter the participant's name.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(college.length()<1)
		{
			Toast.makeText(EditEntry.this, "Enter the participant's College's name.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(phone.length()!=10)
		{
			Toast.makeText(EditEntry.this, "Enter the participant's Phone number.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(rid.length()!=4 && rid.length()!=5 && rid.length()>0)
		{
			Toast.makeText(EditEntry.this, "Enter a valid Refferal ID.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(rid.equals("")) rid="0";
		
		
		if(checkConnection())
		{
			dialog=ProgressDialog.show(EditEntry.this, "Please Wait...", "Adding to Database...");
			
			String url="";
			try
			{
				url = new URI("http", "//quillonparchment.in/cognit14//update.php?name="+name+"&college="+college+"&dept="+dept+"&year="+year+"&phone="+phone+"&cid="+CID+"&rid="+rid, null).toASCIIString();
			}
			catch(Exception e){}
			
			RegisterTask task = new RegisterTask(EditEntry.this);
			task.execute(url);
			
		}
		else
		{
			Toast.makeText(EditEntry.this, "Check Your Internet Connection!", Toast.LENGTH_LONG).show();
			return;
		}
		
		
	}

	@Override
	public void onFetchComplete() {	}

	@Override
	public void onFetchFailure(String msg) { }

	@Override
	public void onFetchComplete(String s)
	{
		if(s.equals("done\n") || s.equals("done"))
		{
			dialog.dismiss();
			Toast.makeText(this, "Successfully updated!", Toast.LENGTH_LONG).show();
			finish();
		}
		else if(s.equals("s\n") || s.equals("s"))
		{
			dialog.dismiss();
			Toast.makeText(this, "Cid already exists!", Toast.LENGTH_LONG).show();
		}
	}
	
}
