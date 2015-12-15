
package com.iclub.aathmasparan;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchTask extends AsyncTask<String, Void, String>
{

	private final FetchDataListener listener;
    private String msg;
    public SearchTask(FetchDataListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params){
        try{
            URL url = new URL("");
            Log.e("URL","");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println("Response Code: " + conn.getResponseCode());
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = streamToString(in);
            Log.e("RESPONSE",response);
            return response;
        }catch(IOException e){
            Log.e("OMFG",e.toString());
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(String s){
    	if(s == null){
            if(listener != null) listener.onFetchFailure();
            return;
        }
    	if(s.startsWith("donors") || s.startsWith("nodonors")){
            String parts[] = s.split("endofstring");
            listener.onFetchComplete(parts[0]);
        }else{
            listener.onFetchFailure();
        }
    }

    public String streamToString(final InputStream is) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                throw e;
            }
        }
        return sb.toString();
    }
    
}