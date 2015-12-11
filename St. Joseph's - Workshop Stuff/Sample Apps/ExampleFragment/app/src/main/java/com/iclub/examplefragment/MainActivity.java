package com.iclub.examplefragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclickandroid(View view){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        AndroidFragment af=new AndroidFragment();
        ft.replace(R.id.myFragmentSpace,af).commit();
    }

    public void onclickwindows(View view){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        WindowsFragment wf=new WindowsFragment();
        ft.replace(R.id.myFragmentSpace,wf).commit();
    }

    public static class AndroidFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.android, container, false);
        }
    }

    public static class WindowsFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.windows, container, false);
        }
    }

}