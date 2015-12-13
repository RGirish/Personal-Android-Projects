package com.iclub.aathmasparan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profilefrag extends Fragment {

	public Profilefrag()
	{
		
	}
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.profile,container, false);
    return view;
  }

  public static Fragment newInstance() 
	{
     Profilefrag  mFrgment = new Profilefrag();
      return mFrgment;
  }
} 