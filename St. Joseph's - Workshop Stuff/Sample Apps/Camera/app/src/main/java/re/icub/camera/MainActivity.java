package re.icub.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void captureImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory() + File.separator + "photo.jpg");
        Uri fileUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivity(intent);
    }

    public void captureVideo(View view){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File video = new File(Environment.getExternalStorageDirectory() + File.separator + "videolow.mp4");
        Uri fileUri = Uri.fromFile(video);
        //intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 4);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivity(intent);
    }

}