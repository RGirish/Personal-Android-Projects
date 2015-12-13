package com.iclub.aathmasparan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Galleryfrag extends Fragment {

	public Galleryfrag()
	{
		
	}
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.gallery,container, false);
    return view;
  }
  
  public static Fragment newInstance() 
	{
     Galleryfrag  mFrgment = new Galleryfrag();
      return mFrgment;
  }

} 