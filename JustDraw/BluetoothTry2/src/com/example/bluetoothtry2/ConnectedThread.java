
package com.example.bluetoothtry2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public class ConnectedThread extends Thread
{
    private static InputStream mmInStream=null;
    private static OutputStream mmOutStream=null;
    public Handler mHandler;
    
    public ConnectedThread(BluetoothSocket socket)
    {
    	ConnectionState.CONNECTED=1;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try 
        {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } 
        catch (IOException e){}
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() 
    {
    	byte[] buffer = new byte[1024];
        int bytes;
        while (true) 
        {
            try 
            {
            	bytes = mmInStream.read(buffer);
            	FingerPaint fp=new FingerPaint();
            	fp.get_data(bytes);
            	//FingerPaint.get_data(bytes);
            } 
            catch (IOException e) 
            {
            	break;
            }
        }
    }
    
    
    //To communicate with the other device
    public static void write(byte[] buffer)
    {
        try 
        {
            mmOutStream.write(buffer);
        } 
        catch (IOException e){}
    }

}