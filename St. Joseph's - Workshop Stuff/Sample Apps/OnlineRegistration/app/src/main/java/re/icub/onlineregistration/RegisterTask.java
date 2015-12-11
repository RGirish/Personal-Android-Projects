
package re.icub.onlineregistration;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;

public class RegisterTask extends AsyncTask<String, Void, String> {

    private final RegisterTaskListener listener;

    public RegisterTask(RegisterTaskListener mylistener) {
        this.listener = mylistener;
    }


    @Override
    protected String doInBackground(String... params) {

        String url = params[0];
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity == null) {
                return null;
            }

            InputStream is = entity.getContent();
            String phpEchoString = streamToString(is);
            return phpEchoString;

        } catch (IOException e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        if (s == null) {
            listener.onFailure();
            return;
        }
        if(s.contains("done")) listener.onComplete();
    }

    public String streamToString(final InputStream is) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                throw e;
            }
        }
        return sb.toString();
    }

}

