package com.example.wifichat1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Connected extends Activity implements ReceiveDataListener
{
	public EditText et;
    public LinearLayout msglist;
    public WifiManager mainWifi;
    public ReceiveDataAsyncTask task;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	 	setContentView(R.layout.connected);
	 	mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	 	
	 	et=(EditText)findViewById(R.id.mess);
	 	if(MainMenu.FLAG.equals("AP"))
	 	{
	 		task=new ReceiveDataAsyncTask(this);
	 		task.execute("");
	 		Button send=(Button)findViewById(R.id.send);
	 		send.setVisibility(View.GONE);
	 		et.setVisibility(View.GONE);
	 	}
	 	else
	 	{	
	 		msglist=(LinearLayout)findViewById(R.id.messagelist);
	 		et.setImeOptions(EditorInfo.IME_ACTION_SEND);
	 		et.setOnEditorActionListener(new OnEditorActionListener() 
			{   
				@Override
				public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
				{
					onclicksend();
					return true;
				}
			});
	
	 	}
	}
	
	public void onclicksend(View v)
	{
		String text=et.getText().toString();
		et.setText("");
		sendToNetwork(text);
		addToList(text,"abcd");
	}
	
	public void onclicksend()
	{
		String text=et.getText().toString();
		et.setText("");
		sendToNetwork(text);
		addToList(text,"abcd");
	}
	 
	@SuppressWarnings("deprecation")
	public void sendToNetwork(String text)
	{	
		Socket socket;
		byte buf[]  = new byte[1024];
		
		try
		{
			socket=new Socket(InetAddress.getLocalHost(),8888);
			Log.e("IPIPIPIP","faaack");
			socket.bind(null);
			Log.e("IPIPIPIP","faaack");
			String ip=Formatter.formatIpAddress(mainWifi.getConnectionInfo().getIpAddress());
		    Log.e("IPIPIPIP",ip);
		    OutputStream outputStream = socket.getOutputStream();
		    buf=text.getBytes();
		    outputStream.write(buf,0,buf.length);
		    outputStream.close();
		    socket.close();
		}
		catch(Exception e)
		{
			Log.e("ERRRORR",e.getStackTrace().toString());
		}
		
		
	}
	
	public void addToList(String text, String received)
	{
		if(text.length()==0) return;
		TextView tv=new TextView(this);
		tv.setText(text);
		if(received.equals("received")) tv.setTextColor(Color.RED);
		else tv.setTextColor(Color.BLACK);
		LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(lp);
		msglist.addView(tv);
	}
	
	
	public static class ReceiveDataAsyncTask extends AsyncTask<String,Void,String>
	{
		private ReceiveDataListener listener;
	    
	    public ReceiveDataAsyncTask(ReceiveDataListener listener)
	    {
	        this.listener = listener;
	    }

	    @Override
	    protected String doInBackground(String... params)
	    {
	    	String text="";
	    	try
	        {
	            ServerSocket serverSocket = new ServerSocket(8888);
	            Socket client = serverSocket.accept();
	            Log.e("After Server Accept","After Server Accept");
	            InputStream inputstream = client.getInputStream();
	            text=streamToString(inputstream);
	            Log.e("After streamToString","After streamToString");
	            serverSocket.close();
	        }
	        catch(Exception e){}
	    	Log.e("Received: " ,text);
	        return text;
	    }

	    @Override
	    protected void onPostExecute(String text)
	    {
	    	listener.onFetchComplete(text);
	    }
	}
	
	
	public static String streamToString(final InputStream is) throws IOException
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

	@Override
	public void onFetchComplete(String string)
	{
		addToList(string,"received");
		task=new ReceiveDataAsyncTask(this);
	 	task.execute("");
	}
	
}