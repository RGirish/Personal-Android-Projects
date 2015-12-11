package re.icub.onlineregistration;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements RegisterTaskListener {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reg(View view){

        dialog = ProgressDialog.show(this,"Please Wait","Loasding");

        String NAME = ((EditText)findViewById(R.id.name)).getText().toString();
        String COLLEGE = ((EditText)findViewById(R.id.college)).getText().toString();
        String DEPT = ((EditText)findViewById(R.id.dept)).getText().toString();
        String PHONE = ((EditText)findViewById(R.id.phone)).getText().toString();
        String EMAIL = ((EditText)findViewById(R.id.email)).getText().toString();

        RegisterTask task = new RegisterTask(this);
        task.execute("http://yourdomain.com/yourphpfilename.php?name="+NAME+"&college="+COLLEGE+"&dept="+DEPT+"&phone="+PHONE+"&email="+EMAIL);
    }

    @Override
    public void onFailure() {
        dialog.dismiss();
        Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {
        dialog.dismiss();
        Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
    }
}