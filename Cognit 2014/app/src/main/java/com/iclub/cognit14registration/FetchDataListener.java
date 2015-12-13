package com.iclub.cognit14registration;

public interface FetchDataListener 
{
	public void onFetchComplete();
    public void onFetchFailure(String msg);
	public void onFetchComplete(String string);
}