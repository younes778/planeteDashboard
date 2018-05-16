package d2si.apps.planetedashboard.webservice.httpgetter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.data.Tiers;

/**
 * class that represents tiers updater
 *
 * @author younessennadj
 */
public abstract class TiersUpdater extends AsyncTask<Void, Void, ArrayList<Tiers>> {

    private Context context;
    private Date dateFrom;


    /**
     * Method that execute the http task
     *
     * @param context  app actual context
     * @param dateFrom sales from datefrom
     */
    public TiersUpdater(Context context, Date dateFrom) {
        this.context = context;
        this.dateFrom = dateFrom;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected ArrayList<Tiers> doInBackground(Void... params) {
        try {
            // form the url with the fields
            final String url = AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL), Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)), context.getString(R.string.REST_REQUEST_TIERS_UPDATE), new ArrayList<String>() {{
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

            // Set connexion Timeout
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(AppUtils.CONNEXION_TIMEOUT);

            restTemplate.setRequestFactory(clientHttpRequestFactory);

            // format response according to JSON
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            // get the results
            ArrayList<Tiers> salesDB = new ArrayList<>();
            ArrayList<d2si.apps.planetedashboard.webservice.data.Tiers> tiers = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Tiers[].class)));
            for (d2si.apps.planetedashboard.webservice.data.Tiers tier : tiers) {
                salesDB.add(new Tiers(tier.getPcf_code(), tier.getPcf_type(), tier.getPcf_rs(), tier.getPcf_rue(), tier.getPcf_comp(), tier.getPcf_cp(), tier.getPcf_ville(), tier.getPay_code(), tier.getPcf_tel1(), tier.getPcf_tel2(), tier.getPcf_fax(), tier.getPcf_email(), tier.getPcf_url(), tier.getFat_lib()));
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
     * @param tiers return from the web service
     */
    protected void onPostExecute(ArrayList<Tiers> tiers) {
        onPost(tiers);
    }

    /**
     * Method that execute on task finished
     *
     * @param tiers return from the web service
     */
    public abstract void onPost(ArrayList<Tiers> tiers);

}