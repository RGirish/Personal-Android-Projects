package com.iclub.aathmasparan;

public interface FetchDataListener {
    void onFetchComplete();
    void onFetchComplete(String s);
    void onFetchFailure();
    void onFetchFailure(String s);
}