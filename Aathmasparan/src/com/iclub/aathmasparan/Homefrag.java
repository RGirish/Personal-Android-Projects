package com.iclub.aathmasparan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Homefrag extends Fragment {

	public Homefrag()
	{
		
	}
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.homepage,container, false);
    return view;
  }

  public static Fragment newInstance() 
	{
     Homefrag  mFrgment = new Homefrag();
      return mFrgment;
  }
} 