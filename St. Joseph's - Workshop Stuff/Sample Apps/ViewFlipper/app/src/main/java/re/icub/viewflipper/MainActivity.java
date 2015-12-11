package re.icub.viewflipper;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewFlipper flipper=(ViewFlipper)findViewById(R.id.host);
        flipper.setFlipInterval(500);
        flipper.startFlipping();
    }
}