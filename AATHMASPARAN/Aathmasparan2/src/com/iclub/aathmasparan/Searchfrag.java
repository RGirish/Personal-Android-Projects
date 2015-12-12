package com.iclub.aathmasparan;

import java.util.Locale;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Searchfrag extends Fragment {

	public Searchfrag(){}
	
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.search,container, false);
    return view;
  }

  public static Fragment newInstance() 
	{
     Searchfrag  mFrgment = new Searchfrag();
      return mFrgment;
  }
  
  
} 