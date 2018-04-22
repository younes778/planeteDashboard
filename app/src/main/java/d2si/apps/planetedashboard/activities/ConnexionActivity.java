package d2si.apps.planetedashboard.activities;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import d2si.apps.planetedashboard.classes.AppData;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.data.Sale;
import d2si.apps.planetedashboard.webservice.WSSale;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class ConnexionActivity extends RealmActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);

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
                final String url=AppData.formGetUrl(getString(R.string.REST_SERVER_URL),Integer.parseInt(getString(R.string.REST_SERVER_PORT)),getString(R.string.REST_REQUEST_SALES),new ArrayList<String>(){{add(getString(R.string.REST_FIELD_URL));add(getString(R.string.REST_FIELD_DB_NAME));add(getString(R.string.REST_FIELD_DATE_FROM));add(getString(R.string.REST_FIELD_DATE_TO));}},new ArrayList<String>(){{add(getString(R.string.DB_URL));add(getString(R.string.DB_NAME));add("2018-01-01");add("2018-01-02");}});
                Log.e("url",url);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ArrayList<WSSale> wssales = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, WSSale[].class)));
                ArrayList<Sale> sales = new ArrayList<>();
                for (WSSale wssale:wssales)
                    sales.add(new Sale(wssale));
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
            String builder="{\n";
            for (Sale sale:sales)
                builder+="num:"+sale.getNumero()+",date:"+sale.getDate().toString()+",total:"+sale.getTotal()+"\n";
            builder+="}";
            greetingIdText.setText(builder);
        }

    }


    /*
    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Sale>> {
        @Override
        /**
         * Method that execute the http task
         *
         * @param params parameters to use in background
         */
       /* protected ArrayList<Sale> doInBackground(Void... params) {
            try {
                final String url=AppData.formGetUrl(getString(R.string.REST_SERVER_URL),Integer.parseInt(getString(R.string.REST_SERVER_PORT)),getString(R.string.REST_REQUEST_SALES),new ArrayList<String>(){{add(getString(R.string.REST_FIELD_URL));add(getString(R.string.REST_FIELD_DB_NAME));add(getString(R.string.REST_FIELD_DATE_FROM));add(getString(R.string.REST_FIELD_DATE_TO));}},new ArrayList<String>(){{add(getString(R.string.DB_URL));add(getString(R.string.DB_NAME));add("2018-01-01");add("2018-01-02");}});
                Log.e("url",url);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ArrayList<WSSale> wssales = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, WSSale[].class)));
                ArrayList<Sale> sales = new ArrayList<>();
                for (WSSale wssale:wssales)
                    sales.add(new Sale(wssale));
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
        /*protected void onPostExecute(ArrayList<Sale> sales) {
            TextView greetingIdText = findViewById(R.id.txt);
            String builder="{\n";
            for (Sale sale:sales)
                builder+="num:"+sale.getNumero()+",date:"+sale.getDate().toString()+",total:"+sale.getTotal()+"\n";
            builder+="}";
            greetingIdText.setText(builder);
        }

    }*/

}