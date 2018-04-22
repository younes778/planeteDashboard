package d2si.apps.planetedashboard.webservice.httpgetter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.data.Sale;
import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.webservice.data.WSSale;

public abstract class SalesGetter extends AsyncTask<Void, Void, ArrayList<Sale>> {

    private Context context;
    private String dateFrom;
    private String dateTo;

    /**
     * Method that execute the http task
     *
     * @param context app actual context
     * @param dateFrom sales from datefrom
     * @param dateTo sales to dateTo
     */
    public SalesGetter(Context context, String dateFrom, String dateTo) {
        this.context = context;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected ArrayList<Sale> doInBackground(Void... params) {
        try {
            // form the url with the fields
            final String url= AppData.formGetUrl(context.getString(R.string.REST_SERVER_URL),Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)),context.getString(R.string.REST_REQUEST_SALES_TEMPLATE),new ArrayList<String>(){{add(context.getString(R.string.REST_FIELD_URL));add(context.getString(R.string.REST_FIELD_DB_NAME));add(context.getString(R.string.REST_FIELD_DATE_FROM));add(context.getString(R.string.REST_FIELD_DATE_TO));}},new ArrayList<String>(){{add(context.getString(R.string.DB_URL));add(context.getString(R.string.DB_NAME));add(dateFrom);add(dateTo);}});

            // use the rest template
            RestTemplate restTemplate = new RestTemplate();
            // format response according to JSON
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            // get the results
            ArrayList<WSSale> wssales = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, WSSale[].class)));
            // transform results from Webservice data to database data
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
     * @param sales sales return from the web service
     */
    protected void onPostExecute(ArrayList<Sale> sales){
            onPost(sales);
    }

    /**
     * Method that execute on task finished
     *
     * @param sales sales return from the web service
     */
    public abstract void onPost(ArrayList<Sale> sales);

}