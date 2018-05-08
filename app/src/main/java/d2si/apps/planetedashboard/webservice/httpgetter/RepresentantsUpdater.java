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
import d2si.apps.planetedashboard.database.data.Representant;
/**
 * class that represents representants updater
 *
 * @author younessennadj
 */
public abstract class RepresentantsUpdater extends AsyncTask<Void, Void, ArrayList<Representant>> {

    private Context context;
    private Date dateFrom;


    /**
     * Method that execute the http task
     *
     * @param context app actual context
     * @param dateFrom sales from datefrom
     */
    public RepresentantsUpdater(Context context, Date dateFrom) {
        this.context = context;
        this.dateFrom = dateFrom;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected ArrayList<Representant> doInBackground(Void... params) {
        try {
            // form the url with the fields
            final String url= AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL),Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)),context.getString(R.string.REST_REQUEST_REPRESENTANT_UPDATE),new ArrayList<String>(){{add(context.getString(R.string.REST_FIELD_URL));add(context.getString(R.string.REST_FIELD_DB_NAME));add(context.getString(R.string.REST_FIELD_DATE_FROM));}},new ArrayList<String>(){{add(context.getString(R.string.DB_URL));add(context.getString(R.string.DB_NAME));add(AppUtils.formDateTimeSql(dateFrom));}});

            // use the rest template
            RestTemplate restTemplate = new RestTemplate();
            // format response according to JSON
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            // get the results
            ArrayList<Representant> salesDB=new ArrayList<>();
            ArrayList<d2si.apps.planetedashboard.webservice.data.Representant> representants = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Representant[].class)));
            for (d2si.apps.planetedashboard.webservice.data.Representant representant:representants) {
                      salesDB.add(new Representant(representant.getRep_code(),representant.getRep_nom(),representant.getRep_prenom()));
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
     * @param representants return from the web service
     */
    protected void onPostExecute(ArrayList<Representant> representants){
            onPost(representants);
    }

    /**
     * Method that execute on task finished
     *
     * @param representants return from the web service
     */
    public abstract void onPost(ArrayList<Representant> representants);

}