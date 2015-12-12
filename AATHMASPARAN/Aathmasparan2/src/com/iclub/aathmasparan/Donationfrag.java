package com.iclub.aathmasparan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Donationfrag extends Fragment {

	public Donationfrag()
	{
		
	}
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.donation,container, false);
    return view;
  }

  public static Fragment newInstance() 
	{
     Donationfrag  mFrgment = new Donationfrag();
      return mFrgment;
  }
  
} 