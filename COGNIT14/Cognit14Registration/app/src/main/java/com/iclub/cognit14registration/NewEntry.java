
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
import android.widget.Toast;

public class NewEntry extends Activity implements FetchDataListener
{

	String name,college,phone,dept,cid,rid,year;
	public static ProgressDialog pdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setTitle("Registration");
		setContentView(R.layout.newentry);
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
		
		
		EditText ETname,ETcollege,ETphone,ETcid,ETrid;
		Spinner Syear,Sdept;
		
		ETname=(EditText)findViewById(R.id.name);
		ETcollege=(EditText)findViewById(R.id.college);
		ETphone=(EditText)findViewById(R.id.phone);
		ETcid=(EditText)findViewById(R.id.cid);
		ETrid=(EditText)findViewById(R.id.rid);
		Sdept=(Spinner)findViewById(R.id.dept);
		Syear=(Spinner)findViewById(R.id.year);
		
		name=ETname.getText().toString();
		college=ETcollege.getText().toString();
		phone=ETphone.getText().toString();
		cid=ETcid.getText().toString();
		rid=ETrid.getText().toString();
		dept=Sdept.getSelectedItem().toString();
		year=Syear.getSelectedItem().toString();
		
	
		if(name.length()<1)
		{
			Toast.makeText(this, "Enter the participant's name.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(college.length()<1)
		{
			Toast.makeText(this, "Enter the participant's College's name.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(phone.length()!=10)
		{
			Toast.makeText(this, "Enter the participant's Phone number.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(cid.length()!=4 && cid.length()!=5)
		{
			Toast.makeText(this, "Enter a valid CID.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(rid.length()!=4 && rid.length()!=5 && rid.length()>0)
		{
			Toast.makeText(this, "Enter a valid Refferal ID.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(rid.equals("")) rid="0";
	
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure? Verify CID once.");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	
            	if(checkConnection())
        		{
        			pdialog=ProgressDialog.show(NewEntry.this, "Please Wait...", "Adding to Database...");
        			
        			String url="";
        			try
        			{
        				url = new URI("http", "//quillonparchment.in/cognit14/insert.php?name="+name+"&college="+college+"&dept="+dept+"&year="+year+"&phone="+phone+"&cid="+cid+"&rid="+rid, null).toASCIIString();
        			}
        			catch(Exception e){}
        			
        			RegisterTask task = new RegisterTask(NewEntry.this);
        			task.execute(url);
        			
        		}
        		else
        		{
        			Toast.makeText(NewEntry.this, "Check Your Internet Connection!", Toast.LENGTH_LONG).show();
        			return;
        		}
            
            }
        });
 
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
             dialog.cancel();
            }
        });
        alertDialog.show();
		
		
	}

	@Override
	public void onFetchComplete() {	}

	@Override
	public void onFetchFailure(String msg) { }

	@Override
	public void onFetchComplete(String s)
	{
		if(s.equals("done\n"))
		{
			pdialog.dismiss();
			Toast.makeText(this, "Successfully submitted!", Toast.LENGTH_LONG).show();
			startActivity(new Intent(this,NewEntry.class));
			finish();
		}
		else if(s.equals("s\n"))
		{
			pdialog.dismiss();
			Toast.makeText(this, "Cid already exists!", Toast.LENGTH_LONG).show();
		}
	}
	
}
