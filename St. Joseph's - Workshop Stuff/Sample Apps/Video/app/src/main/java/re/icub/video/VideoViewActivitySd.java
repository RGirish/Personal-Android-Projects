package re.icub.video;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class VideoViewActivitySd extends Activity {

    ProgressDialog pDialog;
    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoview_main);
        videoview = (VideoView) findViewById(R.id.VideoView);

        pDialog = new ProgressDialog(VideoViewActivitySd.this);
        pDialog.setTitle("Please Wait");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        try {
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoview);
            File f = new File(Environment.getExternalStorageDirectory() + "/sample.mp4");  //
            Uri video = Uri.fromFile(f);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });

    }

}