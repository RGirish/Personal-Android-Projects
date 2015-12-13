package com.example.cognit1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class GamingFragment extends Fragment 
{ 
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{    
		View myFragmentView = inflater.inflate(R.layout.gaming, container, false);
		return myFragmentView; 
	}
	
	public static Fragment newInstance() 
	{
        GamingFragment mFrgment = new GamingFragment();
        return mFrgment;
    }
}