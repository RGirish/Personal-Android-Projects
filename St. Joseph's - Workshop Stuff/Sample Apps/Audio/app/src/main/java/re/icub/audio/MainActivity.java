package re.icub.audio;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

public class MainActivity extends Activity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playFromRaw(View view){
        mediaPlayer = MediaPlayer.create(this, R.raw.rajni);
        mediaPlayer.start();
    }

    public void playFromUrl(View view) {
        try {
            String url = "http://mobilesmobi.com/downloads/load/Ringtones/Tamil%20Ringtones/Tamil%20Dialogue%20Ringtones/Actors%20Dialogue%20Tones/Rajini%20Dialogue%20Tones/rajini_kasta_padama_kedaikathu%20-%20Mobilesmobi.Com.mp3";
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
    }

    public void playFromSd(View view){
        try {
            FileDescriptor fd = null;
            File baseDir = Environment.getExternalStorageDirectory();
            String audioPath = baseDir.getAbsolutePath() + "/sample.mp3";
            FileInputStream fis = new FileInputStream(audioPath);
            fd = fis.getFD();

            if (fd != null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(fd);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    @Override
    public void onDestroy(){
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

}