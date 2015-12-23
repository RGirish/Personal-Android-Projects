package com.example.bluetoothtry2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class FingerPaint extends Activity {
	private Canvas mCanvas;
	public Paint mPaint;
	public BluetoothAdapter mBluetoothAdapter;
	public AllThreads AT;
	public Context cc;
	public static final int MESSAGE_TOAST = 1;
	public static final int TOUCH_DOWN = 2;
	public static final int TOUCH_MOVE = 3;
	public static final int TOUCH_UP = 4;
	public static final int JOIN = 5;
	public static final int MESSAGE_READ = 6;
	public static final String TOAST = "toast";
	public MyView MYVIEW;

	public FingerPaint() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cc = this;
		getWindow().setWindowAnimations(R.style.Fade);
		getWindow().setTitle("Canvas");
		setTitleColor(Color.BLACK);
		MYVIEW = new MyView(this);
		setContentView(MYVIEW);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF0000FF);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(5);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) 
		{
			Toast.makeText(this, "Device doesn't support Bluetooth!",Toast.LENGTH_LONG).show();
		} 
		else 
		{
			if (!mBluetoothAdapter.isEnabled()) 
			{
				Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
				startActivity(discoverableIntent);
			}
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case JOIN:
			if (resultCode == Activity.RESULT_OK) {
				Log.i("inside activity result", "inside activity result");
				connectDevice(data);
			}
			break;
		}
	}

	private void connectDevice(Intent data) {
		String address = data.getExtras().getString(
				NearbyDevices.EXTRA_DEVICE_ADDRESS);
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		AT.connect(device);
	}

	@Override
	public void onResume() {
		super.onResume();
		getWindow().setTitle("Canvas");
	}

	public class MyView extends View {

		public Bitmap mBitmap;
		private Path mPath, mPath2;
		private Paint mBitmapPaint;

		public MyView(Context c) {
			super(c);

			mPath = new Path();
			mPath2 = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mBitmap.setDensity(1);

			mCanvas = new Canvas(mBitmap);
		}

		@Override
		protected void onDraw(Canvas canvas) {
		//	Log.i("inside on draw", "inside on draw");
			canvas.drawColor(0xFFFFFFFF);
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
			canvas.drawPath(mPath, mPaint);
		}

		private float mX, mY, mX2, mY2;
		private static final float TOUCH_TOLERANCE = 1;

		private void touch_start(float x, float y) {
			Log.i("inside touch down", "inside touch down");
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			Log.i("inside touch move", "inside touch move");
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}

		private void touch_up() {
			Log.i("inside touch up", "inside touch up");
			mPath.lineTo(mX, mY);
			mCanvas.drawPath(mPath, mPaint);
			mPath.reset();
		}

		protected void onDraw_2(Canvas canvas) {
			Log.i("inside on draw", "inside on draw");
			canvas.drawColor(0xFFFFFFFF);
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
			canvas.drawPath(mPath2, mPaint);
		}

		private void touch_start_2(float x, float y) {
			Log.i("inside touch down2", "inside touch down2");
			mPath2.reset();
			mPath2.moveTo(x, y);
			mX2 = x;
			mY2 = y;
			invalidate();
		}

		private void touch_move_2(float x, float y) {
			Log.i("inside touch move2", "inside touch move2");
			float dx = Math.abs(x - mX2);
			float dy = Math.abs(y - mY2);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath2.quadTo(mX2, mY2, (x + mX2) / 2, (y + mY2) / 2);
				mX2 = x;
				mY2 = y;
			}
			invalidate();
		}

		private void touch_up_2() {
			Log.i("inside touch up2", "inside touch up2");
			mPath2.lineTo(mX2, mY2);
			mCanvas.drawPath(mPath2, mPaint);
			mPath2.reset();
			invalidate();
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();
			String MESSAGE, XX, YY;
			byte[] bytes;

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				touch_start(x, y);

				XX = Float.toString(x);
				YY = Float.toString(y);
				MESSAGE = "D" + XX + "*" + YY;
				Log.i("Message down", MESSAGE);
				bytes = MESSAGE.getBytes();

				if (AllThreads.CONNECTED == 1) {
					AT.AT_write(bytes);
				}
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:

				touch_move(x, y);

				XX = Float.toString(x);
				YY = Float.toString(y);
				MESSAGE = "M" + XX + "*" + YY;
				Log.i("Message move", MESSAGE);
				bytes = MESSAGE.getBytes();

				if (AllThreads.CONNECTED == 1) {
					AT.AT_write(bytes);
				}
				invalidate();
				break;
			case MotionEvent.ACTION_UP:

				touch_up();

				XX = Float.toString(x);
				YY = Float.toString(y);
				MESSAGE = "U" + XX + "*" + YY;
				Log.i("Message up", MESSAGE);
				bytes = MESSAGE.getBytes();

				if (AllThreads.CONNECTED == 1) {
					AT.AT_write(bytes);
				}
				invalidate();
				break;
			}
			return true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (AllThreads.CONNECTED == 1) {
			menu.add(0, 10, 0, "Red");
			menu.add(0, 20, 0, "Blue");
			menu.add(0, 30, 0, "Green");
			menu.add(0, 40, 0, "Black");
			menu.add(0, 50, 0, "Grey");
			menu.add(0, 60, 0, "Yellow");
			menu.add(0, 70, 0, "Eraser");
		}
		menu.add(0, 80, 0, "Create Network");
		menu.add(0, 90, 0, "Join Network");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		mPaint.setXfermode(null);
		mPaint.setAlpha(0xFF);

		switch (item.getItemId()) {
		case 10:
			mPaint.setStrokeWidth(5);
			mPaint.setColor(0xffff0000);
			break;
		case 20:
			mPaint.setStrokeWidth(5);
			mPaint.setColor(0xff0000ff);
			break;
		case 30:
			mPaint.setStrokeWidth(5);
			mPaint.setColor(0xff00ff00);
			break;
		case 40:
			mPaint.setStrokeWidth(5);
			mPaint.setColor(0xff000000);
			break;
		case 50:
			mPaint.setStrokeWidth(5);
			mPaint.setColor(0xff777777);
			break;
		case 60:
			mPaint.setStrokeWidth(5);
			mPaint.setColor(0xffffff00);
			break;
		case 70:
			mPaint.setStrokeWidth(18);
			mPaint.setColor(0xffffffff);
			break;
		case 80: {
			if (mBluetoothAdapter.isEnabled()) {
				AT = new AllThreads(mHandler);
				Toast.makeText(cc, "Listening for incoming connections",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(cc, "Turn on Bluetooth and make discoverable!",
						Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case 90: {
			if (mBluetoothAdapter.isEnabled()) {
				AT = new AllThreads(mHandler);
				Intent i = new Intent(cc, NearbyDevices.class);
				startActivityForResult(i, JOIN);
			} else {
				Toast.makeText(cc, "Turn on Bluetooth and make discoverable!",
						Toast.LENGTH_SHORT).show();
			}
		}
			break;
		}

		return true;
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			byte[] readBuf;
			String readMessage, x, y;
			Float X, Y;
			int f;
			switch (msg.what) {

			case MESSAGE_READ:

				readBuf = (byte[]) msg.obj;
				readMessage = new String(readBuf, 0, msg.arg1);
				f = readMessage.indexOf("*");
				x = readMessage.substring(1, f - 1);
				y = readMessage.substring(f + 1);
				X = Float.parseFloat(x);
				Y = Float.parseFloat(y);

				if (readMessage.startsWith("D")) {
					MYVIEW.touch_start_2(X, Y);
					MYVIEW.onDraw_2(mCanvas);
				} else if (readMessage.startsWith("M")) {
					MYVIEW.touch_move_2(X, Y);
					MYVIEW.onDraw_2(mCanvas);

				} else if (readMessage.startsWith("U")) {
					MYVIEW.touch_up_2();
					MYVIEW.onDraw_2(mCanvas);

				}

				break;

			case MESSAGE_TOAST:

				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();

				break;
			}
		}
	};
}