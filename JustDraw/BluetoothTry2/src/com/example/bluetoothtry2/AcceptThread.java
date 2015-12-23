package com.example.bluetoothtry2;

import java.io.IOException;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class AcceptThread extends Thread 
{
    private final BluetoothServerSocket mmServerSocket;
    public String NAME="Girish";
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	private UUID MY_UUID = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
 
    public AcceptThread() 
    {	        
        BluetoothServerSocket tmp = null;
        try 
        {
        	tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } 
        catch (IOException e) { }
        mmServerSocket = tmp;
    }
 
    public void run() 
    {
        BluetoothSocket socket = null;
        while (true) 
        {
            try 
            {
                socket = mmServerSocket.accept();
            } catch (IOException e) { break;}
            if (socket != null) 
            {
            	//DataTransfer.manageConnectedSocket(socket);
                ConnectThread.connected(socket, socket.getRemoteDevice());
            	try 
                {
					mmServerSocket.close();
				} 
                catch (IOException e) 
                {
					e.printStackTrace();
				}
                break;
            }
        }
    }
    public void cancel() {
        
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            
        }
    }
}