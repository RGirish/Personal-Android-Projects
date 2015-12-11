package re.icub.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;


public class MainActivity extends ActionBarActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playFromRaw(View view){
        Intent intent=new Intent(this,VideoViewActivityRes.class);
        startActivity(intent);
    }

    public void playYouTube(View view){
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this, "AIzaSyDkc7uq5st7bhPCnBZNhTxDaebmqxJfn4Q", "RB-RcX5DS5A");
        startActivity(intent);
    }

    public void playFromSd(View view) {
        Intent intent=new Intent(this,VideoViewActivitySd.class);
        startActivity(intent);
    }

    public void playFromUrl(View view) {
        Intent intent=new Intent(this,VideoViewActivity.class);
        startActivity(intent);
    }

    public void onDestroy(){
        try {
            mp.release();
            mp = null;
        }catch (Exception e){}
        super.onDestroy();
    }

}