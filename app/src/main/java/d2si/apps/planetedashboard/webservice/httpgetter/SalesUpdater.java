package d2si.apps.planetedashboard.webservice.httpgetter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.data.Document;

/**
 * class that represents sales updater
 *
 * @author younessennadj
 */
public abstract class SalesUpdater extends AsyncTask<Void, Void, ArrayList<Document>> {

    private Context context;
    private Date dateFrom;


    /**
     * Method that execute the http task
     *
     * @param context  app actual context
     * @param dateFrom sales from datefrom
     */
    public SalesUpdater(Context context, Date dateFrom) {
        this.context = context;
        this.dateFrom = dateFrom;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected ArrayList<Document> doInBackground(Void... params) {
        try {
            // form the url with the fields
            final String url = AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL), Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)), context.getString(R.string.REST_REQUEST_SALES_UPDATE), new ArrayList<String>() {{
                add(context.getString(R.string.REST_FIELD_URL));
                add(context.getString(R.string.REST_FIELD_DB_NAME));
                add(context.getString(R.string.REST_FIELD_DATE_FROM));
            }}, new ArrayList<String>() {{
                add(AppUtils.serverName);
                add(AppUtils.dBName);
                add(AppUtils.formDateTimeSql(dateFrom));
            }});

            // use the rest template
            RestTemplate restTemplate = new RestTemplate();
            // format response according to JSON
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            // get the results
            ArrayList<Document> salesDB = new ArrayList<>();
            ArrayList<d2si.apps.planetedashboard.webservice.data.Document> sales = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Document[].class)));
            for (d2si.apps.planetedashboard.webservice.data.Document document : sales) {
                salesDB.add(new Document(document.getDoc_numero(), document.getDoc_type(), document.getDate(), document.getPcf_code(), document.getRep_code()));
            }
            return salesDB;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param sales return from the web service
     */
    protected void onPostExecute(ArrayList<Document> sales) {
        onPost(sales);
    }

    /**
     * Method that execute on task finished
     *
     * @param sales return from the web service
     */
    public abstract void onPost(ArrayList<Document> sales);

}