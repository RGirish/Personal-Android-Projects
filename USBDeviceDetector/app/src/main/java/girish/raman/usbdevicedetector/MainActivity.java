package girish.raman.usbdevicedetector;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_USB_PERMISSION = "com.example.udevice.USB_PERMISSION";
    UsbManager usbManager;
    PendingIntent mPermissionIntent;
    UsbDevice usbDevice;

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {


            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {

                    usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    usbManager.requestPermission(usbDevice, mPermissionIntent);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbDevice != null) {
                            //call method to set up device communication


                            int deviceId = usbDevice.getDeviceId();
                            int productId = usbDevice.getProductId();


                            Log.i("device id", "****" + deviceId);
                            Log.i("product id", "****" + productId);

                        } else {
                            Log.i("device id", "No USB device");
                        }

                    } else {
                        Log.d("shiv", "permission denied for device ");
                    }
                }
            }
        }
    };
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        final String ACTION_USB_PERMISSION = "com.example.udevice.USB_PERMISSION";
        IntentFilter filter = new IntentFilter("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
        registerReceiver(mUsbReceiver, filter);
    }

}0