package com.example.cognit1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class AfterRegistrationFragment extends Fragment
{ 
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{    
		View myFragmentView = inflater.inflate(R.layout.afterregistrationfragmentlayout, container, false);
		
		
		TextView name=(TextView)myFragmentView.findViewById(R.id.MyName);
        TextView college=(TextView)myFragmentView.findViewById(R.id.MyCollege);
        TextView phno=(TextView)myFragmentView.findViewById(R.id.MyNumber);
        TextView email=(TextView)myFragmentView.findViewById(R.id.MyId);
        TextView events=(TextView)myFragmentView.findViewById(R.id.MyEvents);
                
        name.setText(MainActivity.nAME);
        college.setText(MainActivity.cOLLEGE);
        phno.setText(MainActivity.pHNO);
        email.setText(MainActivity.eMAIL);
        events.setText(MainActivity.eVENTS);
		
		
		return myFragmentView; 
	}
	
	public static Fragment newInstance() 
	{
        AfterRegistrationFragment mFrgment = new AfterRegistrationFragment();
        return mFrgment;
    }
}