package com.example.bluetoothtry2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AllThreads {

	private final BluetoothAdapter mBluetoothAdapter;

	public Handler mHandler;
	private AcceptThread accept;
	private ConnectThread connect;
	private ConnectedThread connected;
	private final String NAME = "Girish";
	private final UUID MY_UUID = UUID
			.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
	public static int CONNECTED = 0;

	public AllThreads(Handler handler) {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mHandler = handler;
		accept = new AcceptThread();
		accept.start();
	}

	public class AcceptThread extends Thread {
		private final BluetoothServerSocket mmServerSocket;
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();

		public AcceptThread() {
			Log.i("AcceptThread", "Inside AcceptThread");
			BluetoothServerSocket tmp = null;
			try {
				tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
						NAME, MY_UUID);
			} catch (IOException e) {
				Log.i("AcceptThread", "Inside AcceptThreadCatch");
			}
			mmServerSocket = tmp;
		}

		public void run() {
			Log.i("AcceptThread", "Inside AcceptThreadRun");
			BluetoothSocket socket = null;
			while (true) {
				try {
					socket = mmServerSocket.accept();
				} catch (IOException e) {
					Log.i("AcceptThread", "Inside AcceptThreadRunCatch");
				}
				if (socket != null) {
					connected(socket, socket.getRemoteDevice());
					try {
						mmServerSocket.close();
					} catch (IOException e) {
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

	public class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			mmDevice = device;
			BluetoothSocket tmp = null;
			Log.i("ConnectThread", "Inside ConnectThread");

			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);

			} catch (IOException e) {
			}
			mmSocket = tmp;
		}

		public void run() {
			mBluetoothAdapter.cancelDiscovery();

			try {
				mmSocket.connect();
				Log.i("Connection result", "connected");
			} catch (IOException e) {
				try {
					mmSocket.close();
				} catch (IOException e2) {
				}
				toaster(2, mmDevice);
				Log.i("Connection result", "unable to connect");
				return;
			}

			connected(mmSocket, mmDevice);

		}
	}

	public void toaster(int n, BluetoothDevice mmDevice) {
		switch (n) {
		case 1:
			String mes = "Connected with " + mmDevice.getName();
			Message msg = mHandler.obtainMessage(FingerPaint.MESSAGE_TOAST);
			Bundle bundle = new Bundle();
			bundle.putString(FingerPaint.TOAST, mes);
			msg.setData(bundle);
			mHandler.sendMessage(msg);
			break;

		case 2:
			String mes1 = "Unable to connect with " + mmDevice.getName();
			Message msg1 = mHandler.obtainMessage(FingerPaint.MESSAGE_TOAST);
			Bundle bundle1 = new Bundle();
			bundle1.putString(FingerPaint.TOAST, mes1);
			msg1.setData(bundle1);
			mHandler.sendMessage(msg1);
			break;
		}
	}

	public class ConnectedThread extends Thread {
		private InputStream mmInStream = null;
		private OutputStream mmOutStream = null;

		public ConnectedThread(BluetoothSocket socket, BluetoothDevice mmDev) {
			toaster(1, mmDev);
			Log.i("ConnectedThread", "Inside ConnectedThread");
			CONNECTED = 1;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e) {
			}
			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int bytes;
			while (true) {
				try {
					bytes = mmInStream.read(buffer);
					mHandler.obtainMessage(FingerPaint.MESSAGE_READ, bytes, -1,
							buffer).sendToTarget();

				} catch (IOException e) {
					break;
				}
			}
		}

		public void write(byte[] buffer) {
			try {
				mmOutStream.write(buffer);
			} catch (IOException e) {
			}
		}

	}

	public void AT_write(byte[] buffer) {
		connected.write(buffer);
	}

	public void connect(BluetoothDevice device) {
		connect = new ConnectThread(device);
		connect.start();
	}

	public void connected(BluetoothSocket socket, BluetoothDevice device) {
		connected = new ConnectedThread(socket, device);
		connected.start();
	}

}
