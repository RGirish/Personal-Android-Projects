package re.icub.cam;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.logging.FileHandler;


public class Cam extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
    }

    public void Pic(View v){
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File p=new File(Environment.getExternalStorageDirectory()+ File.separator+"Pic1.jpg");
        Uri fileURI=Uri.fromFile(p);
        i.putExtra(MediaStore.EXTRA_OUTPUT,fileURI);
        startActivity(i);

    }

    public void Vid(View v){
        Intent i=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File vid=new File(Environment.getExternalStorageDirectory()+ File.separator+"Vid1.mp4");
        Uri fileURI=Uri.fromFile(vid);
        i.putExtra(MediaStore.EXTRA_OUTPUT,fileURI);
        i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
