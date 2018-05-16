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
import d2si.apps.planetedashboard.database.data.Article;

/**
 * class that represents articles getter
 *
 * @author younessennadj
 */
public abstract class ArticlesGetter extends AsyncTask<Void, Void, ArrayList<Article>> {

    private Context context;
    private Date dateFrom;
    private Date dateTo;


    /**
     * Method that execute the http task
     *
     * @param context  app actual context
     * @param dateFrom sales from datefrom
     * @param dateTo   sales to dateTo
     */
    public ArticlesGetter(Context context, Date dateFrom, Date dateTo) {
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
    protected ArrayList<Article> doInBackground(Void... params) {
        try {
            // form the url with the fields
            final String url = AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL), Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)), context.getString(R.string.REST_REQUEST_ARTICLES_GET), new ArrayList<String>() {{
                add(context.getString(R.string.REST_FIELD_URL));
                add(context.getString(R.string.REST_FIELD_DB_NAME));
                add(context.getString(R.string.REST_FIELD_DATE_FROM));
                add(context.getString(R.string.REST_FIELD_DATE_TO));
            }}, new ArrayList<String>() {{
                add(AppUtils.serverName);
                add(AppUtils.dBName);
                add(AppUtils.formDateSql(dateFrom));
                add(AppUtils.formDateSql(dateTo));
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
            ArrayList<Article> salesDB = new ArrayList<>();
            ArrayList<d2si.apps.planetedashboard.webservice.data.Article> articles = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Article[].class)));
            for (d2si.apps.planetedashboard.webservice.data.Article article : articles) {
                salesDB.add(new Article(article.getArt_code(), article.getArt_lib(), article.getFar_lib()));
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
     * @param articles return from the web service
     */
    protected void onPostExecute(ArrayList<Article> articles) {
        onPost(articles);
    }

    /**
     * Method that execute on task finished
     *
     * @param articles return from the web service
     */
    public abstract void onPost(ArrayList<Article> articles);

}