package com.iclub.cognit14registration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
 
public class FetchDetails extends AsyncTask<String, Void, String>
{
    private final FetchDataListener listener;
    private String msg;
    public String email=null,query=null;
    
    public FetchDetails(FetchDataListener listener) 
    {
        this.listener = listener;
    }
     
    @Override
    protected String doInBackground(String... params) 
    {
        if(params == null) return null;
        String url = params[0];        
        try 
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            if(entity == null)
            {
                msg = "No response from the Server!";
                return null;        
            }
            InputStream is = entity.getContent();
            return streamToString(is);
        }
        catch(IOException e)
        {
            msg = "Check your Network Connection!";
        }
         
        return null;
    }
    
    @Override
    protected void onPostExecute(String sJson)
    {
    	if(sJson == null) 
        {
            if(listener != null) listener.onFetchFailure(msg);
            return;
        }
    	Log.e("CRAPPPP",sJson);
    	Log.e("CRAPPPP",sJson);
    	Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);Log.e("CRAPPPP",sJson);
    	if(listener != null) listener.onFetchComplete(sJson);
    	
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