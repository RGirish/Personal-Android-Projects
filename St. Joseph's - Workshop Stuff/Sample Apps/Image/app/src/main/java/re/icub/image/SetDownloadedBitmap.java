package re.icub.image;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class SetDownloadedBitmap extends ActionBarActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        dialog = ProgressDialog.show(this,"Loading","Please Wait...");
        DownloadFileAsync task = new DownloadFileAsync();
        task.execute("http://lifestylehunters.com/media/uploads/2013/05/harveyspecter4.png");
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + File.separator + "dbitmap.jpg");

                byte data[] = new byte[512];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            }
            catch(Exception e){}
            return null;
        }

        @Override
        protected synchronized void onPostExecute(String unused) {
            Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + File.separator + "dbitmap.jpg");
            ImageView imageView=(ImageView)findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);
            dialog.dismiss();
        }
    }


}