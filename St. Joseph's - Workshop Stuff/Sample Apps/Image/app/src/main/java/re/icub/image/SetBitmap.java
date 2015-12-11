package re.icub.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;


public class SetBitmap extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + File.separator + "bitmap.jpg");
        ImageView imageView=(ImageView)findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);
    }
}