package d2si.apps.planetedashboard.activities;


        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
        import org.springframework.web.client.RestTemplate;

        import classes.AppData;
        import classes.Greeting;
        import d2si.apps.planetedashboard.R;

public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }




    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                //final String url = "http://rest-service.guides.spring.io/greeting?name=youyou";
                final String url = AppData.REST_SERVER_URL+":"+AppData.REST_SERVER_PORT+"/greeting?name=younes";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            TextView greetingIdText = (TextView) findViewById(R.id.txt);
            greetingIdText.setText(greeting.getContent());
        }

    }

}