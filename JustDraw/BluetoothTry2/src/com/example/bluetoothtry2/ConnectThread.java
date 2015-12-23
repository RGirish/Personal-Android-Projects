
package com.example.bluetoothtry2;

import java.io.IOException;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnectThread extends Thread
{
       private final BluetoothSocket mmSocket;
       static ConnectedThread mConnectedThread;
       private final BluetoothDevice mmDevice;
       private BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
   	   private UUID MY_UUID = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
       private String mSocketType;

       public ConnectThread(BluetoothDevice device) 
       {
           mmDevice = device;
           BluetoothSocket tmp = null;
          

           // Get a BluetoothSocket for a connection with the
           // given BluetoothDevice
           try {
               
                   tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
               
           } catch (IOException e) {
               Log.e("aasdasdasd", "Socket Type: " + mSocketType + "create() failed", e);
           }
           mmSocket = tmp;
       }
       
       

       public void run() {
           Log.i("wqweqweqwe", "BEGIN mConnectThread SocketType:" + mSocketType);
           setName("ConnectThread" + mSocketType);

           // Always cancel discovery because it will slow down a connection
           mBluetoothAdapter.cancelDiscovery();

           // Make a connection to the BluetoothSocket
           try {
               // This is a blocking call and will only return on a
               // successful connection or an exception
               mmSocket.connect();
               Log.i("Connection result","connected");
               
           } catch (IOException e) {
               // Close the socket
               try {
                   mmSocket.close();
               } catch (IOException e2) {
                   Log.e("dfgdfgdfg", "unable to close() " + mSocketType +
                           " socket during connection failure", e2);
               }
               Log.i("Connection result","unable to connect");
               return;
           }

          
           // Start the connected thread
           connected(mmSocket, mmDevice);
       }
   


       public static void connected(BluetoothSocket socket, BluetoothDevice device ) 
       {
    	   mConnectedThread = new ConnectedThread(socket);
    	   mConnectedThread.start();
       }


}