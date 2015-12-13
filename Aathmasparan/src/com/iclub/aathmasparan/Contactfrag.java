package com.iclub.aathmasparan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Contactfrag extends Fragment {

	public Contactfrag()
	{
		
	}
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.contact,container, false);
    return view;
  }
  
  public static Fragment newInstance() 
	{
     Contactfrag  mFrgment = new Contactfrag();
      return mFrgment;
  }


} 