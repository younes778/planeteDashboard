package d2si.apps.planetedashboard.activities;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import d2si.apps.planetedashboard.classes.AppData;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.data.Sale;

public class ConnexionActivity extends RealmActivity {

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




    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Sale>> {
        @Override
        /**
         * Method that execute the http task
         *
         * @param params parameters to use in background
         */
        protected ArrayList<Sale> doInBackground(Void... params) {
            try {
                final String url=AppData.formGetUrl(getString(R.string.REST_SERVER_URL),Integer.parseInt(getString(R.string.REST_SERVER_PORT)),getString(R.string.REST_REQUEST_SALES),new ArrayList<String>(){{add(getString(R.string.REST_FIELD_URL));add(getString(R.string.REST_FIELD_DATE_FROM));add(getString(R.string.REST_FIELD_DATE_TO));}},new ArrayList<String>(){{add(getString(R.string.DB_URL));add(getString(R.string.DB_NAME));add("2018-01-01");add("2018-01-02");}});
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ArrayList<Sale> sales = (ArrayList<Sale>) restTemplate.getForObject(url, ArrayList.class);
                return sales;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        /**
         * Method that execute the http task
         *
         * @param greeting Object
         */
        protected void onPostExecute(ArrayList<Sale> sales) {
            TextView greetingIdText = findViewById(R.id.txt);
            greetingIdText.setText(sales.toString());
        }

    }

}