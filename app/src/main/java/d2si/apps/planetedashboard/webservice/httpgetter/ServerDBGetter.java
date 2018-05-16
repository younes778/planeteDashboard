package d2si.apps.planetedashboard.webservice.httpgetter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;

/**
 * class that represents server database getter
 *
 * @author younessennadj
 */
public abstract class ServerDBGetter extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private String server;
    private String database;

    /**
     * Method that execute the http task
     *
     * @param context  app actual context
     * @param server   server url
     * @param database database name
     */
    public ServerDBGetter(Context context, String server, String database) {
        this.context = context;
        this.server = server;
        this.database = database;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected Boolean doInBackground(final Void... params) {
        try {
            // form the url with the fields
            final String url = AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL), Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)), context.getString(R.string.REST_REQUEST_SERVER), new ArrayList<String>() {{
                add(context.getString(R.string.REST_FIELD_URL));
                add(context.getString(R.string.REST_FIELD_DB_NAME));
            }}, new ArrayList<String>() {{
                add(server);
                add(database);
            }});

            // use the rest template
            RestTemplate restTemplate = new RestTemplate();

            // Set connexion Timeout
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(AppUtils.CONNEXION_TIMEOUT);

            restTemplate.setRequestFactory(clientHttpRequestFactory);

            // format response according to JSON
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            // get the results
            Boolean value = Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
            return value;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param checked return from the web service
     */
    protected void onPostExecute(Boolean checked) {
        onPost(checked);
    }

    /**
     * Method that execute on task finished
     *
     * @param checked is connection with server database is established
     */
    public abstract void onPost(Boolean checked);

}