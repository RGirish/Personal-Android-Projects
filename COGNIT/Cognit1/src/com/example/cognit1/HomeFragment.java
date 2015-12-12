package com.example.cognit1;

import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
public class HomeFragment extends Fragment 
{ 
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{    
		View myFragmentView = inflater.inflate(R.layout.homefragmentlayout, container, false);
		
		TextView tv=(TextView)myFragmentView.findViewById(R.id.daystogo);
        
        Date d=new Date();
        int days_rem;
        String message="";
        int month=d.getMonth();
        if(month==8&&d.getDate()>=21)
        {
        	message="THE WAIT IS OVER!";
        }
        else if(month==7)
        {
        	days_rem=31-d.getDate()+21;
        	message=days_rem+" more days to go!";
        }
        else if(month==8)
        {
        	days_rem=21-d.getDate();
        	message=days_rem+" more days to go!";
        	if(days_rem==1){message=days_rem+" more day to go!";}
        }
        else if(month>8)
        {
        	message="THE WAIT IS OVER!";
        }
        
        tv.setText(message);
        return myFragmentView;
	}
	
	public static Fragment newInstance() 
	{
        HomeFragment mFrgment = new HomeFragment();
        return mFrgment;
    }
}