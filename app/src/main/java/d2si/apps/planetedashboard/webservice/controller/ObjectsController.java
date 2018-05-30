package d2si.apps.planetedashboard.webservice.controller;

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
import d2si.apps.planetedashboard.database.DataBaseUtils;
import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import d2si.apps.planetedashboard.database.data.Representant;
import d2si.apps.planetedashboard.database.data.Tiers;
import io.realm.RealmObject;

/**
 * class that represents articles getter
 *
 * @author younessennadj
 */
public abstract class ObjectsController extends AsyncTask<Void, Void, ArrayList<? extends RealmObject>> {

    public static enum DATA_TYPE {
        ARTICLES, LIGNES, REPRESENTANT, SALES, TIERS
    }

    private Context context;
    private Date dateFrom;
    private Date dateTo;
    private DATA_TYPE type;

    /**
     * Constructor
     *
     * @param context  app actual context
     * @param dateFrom sales from datefrom
     * @param dateTo   sales to dateTo
     * @param type     data request type
     */
    public ObjectsController(Context context, Date dateFrom, Date dateTo, DATA_TYPE type) {
        this.context = context;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.type = type;
    }


    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected ArrayList<? extends RealmObject> doInBackground(Void... params) {
        try {
            // form the url with the fields
            final String url = (dateTo != null) ? AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL), Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)), getRequestType(), new ArrayList<String>() {{
                add(context.getString(R.string.REST_FIELD_URL));
                add(context.getString(R.string.REST_FIELD_DB_NAME));
                add(context.getString(R.string.REST_FIELD_DB_USER));
                add(context.getString(R.string.REST_FIELD_DB_PASSWORD));
                add(context.getString(R.string.REST_FIELD_DATE_FROM));
                add(context.getString(R.string.REST_FIELD_DATE_TO));
            }}, new ArrayList<String>() {{
                add(AppUtils.serverName);
                add(AppUtils.dBName);
                add(AppUtils.dBUser);
                add(AppUtils.dBPassword);
                add(DataBaseUtils.formDateSql(dateFrom));
                add(DataBaseUtils.formDateSql(dateTo));
            }}) : AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL), Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)), getRequestType(), new ArrayList<String>() {{
                add(context.getString(R.string.REST_FIELD_URL));
                add(context.getString(R.string.REST_FIELD_DB_NAME));
                add(context.getString(R.string.REST_FIELD_DB_USER));
                add(context.getString(R.string.REST_FIELD_DB_PASSWORD));
                add(context.getString(R.string.REST_FIELD_DATE_FROM));
            }}, new ArrayList<String>() {{
                add(AppUtils.serverName);
                add(AppUtils.dBName);
                add(AppUtils.dBUser);
                add(AppUtils.dBPassword);
                add(DataBaseUtils.formDateSql(dateFrom));
            }});

            // use the rest template
            RestTemplate restTemplate = new RestTemplate();

            // Set connexion Timeout
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(AppUtils.CONNEXION_TIMEOUT);
            clientHttpRequestFactory.setReadTimeout(AppUtils.READ_TIMEOUT);

            restTemplate.setRequestFactory(clientHttpRequestFactory);

            // format response according to JSON
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // get the results according to the type
            switch (type) {
                case ARTICLES:
                    ArrayList<Article> articlesDB = new ArrayList<>();
                    ArrayList<d2si.apps.planetedashboard.webservice.data.Article> articles = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Article[].class)));
                    for (d2si.apps.planetedashboard.webservice.data.Article article : articles) {
                        articlesDB.add(new Article(article.getArt_code(), article.getArt_lib(), article.getFar_lib()));
                    }
                    return articlesDB;
                case LIGNES:
                    ArrayList<Ligne> lignesDB = new ArrayList<>();
                    ArrayList<d2si.apps.planetedashboard.webservice.data.Ligne> lignes = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Ligne[].class)));
                    for (d2si.apps.planetedashboard.webservice.data.Ligne ligne : lignes) {
                        lignesDB.add(new Ligne(ligne.getLig_doc_numero(), ligne.getLig_qte(), ligne.getLig_p_net(), ligne.getArt_code()));
                    }
                    return lignesDB;
                case REPRESENTANT:
                    ArrayList<Representant> representantsDB = new ArrayList<>();
                    ArrayList<d2si.apps.planetedashboard.webservice.data.Representant> representants = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Representant[].class)));
                    for (d2si.apps.planetedashboard.webservice.data.Representant representant : representants) {
                        representantsDB.add(new Representant(representant.getRep_code(), representant.getRep_nom(), representant.getRep_prenom()));
                    }
                    return representantsDB;
                case SALES:
                    ArrayList<Document> salesDB = new ArrayList<>();
                    ArrayList<d2si.apps.planetedashboard.webservice.data.Document> sales = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Document[].class)));
                    for (d2si.apps.planetedashboard.webservice.data.Document document : sales) {
                        salesDB.add(new Document(document.getDoc_numero(), document.getDoc_type(), document.getDate(), document.getPcf_code(), document.getRep_code()));
                    }
                    return salesDB;
                case TIERS:
                    ArrayList<Tiers> tiersDB = new ArrayList<>();
                    ArrayList<d2si.apps.planetedashboard.webservice.data.Tiers> tiers = new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, d2si.apps.planetedashboard.webservice.data.Tiers[].class)));
                    for (d2si.apps.planetedashboard.webservice.data.Tiers tier : tiers) {
                        tiersDB.add(new Tiers(tier.getPcf_code(), tier.getPcf_type(), tier.getPcf_rs(), tier.getPcf_rue(), tier.getPcf_comp(), tier.getPcf_cp(), tier.getPcf_ville(), tier.getPay_code(), tier.getPcf_tel1(), tier.getPcf_tel2(), tier.getPcf_fax(), tier.getPcf_email(), tier.getPcf_url(), tier.getFat_lib()));
                    }
                    return tiersDB;

            }

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param objects return from the web service
     */
    protected void onPostExecute(ArrayList<? extends RealmObject> objects) {
        onPost(objects);
    }

    /**
     * Method that execute on task finished
     *
     * @param objects return from the web service
     */
    public abstract void onPost(ArrayList<? extends RealmObject> objects);

    String res = "";

    /**
     * Method that return the request header (Get or Update and which object we are requesting)
     *
     * @return return the request header according to the request type
     */
    private String getRequestType() {
        switch (type) {
            case ARTICLES:
                res = context.getString(R.string.REST_REQUEST_ARTICLES);
                break;
            case LIGNES:
                res = context.getString(R.string.REST_REQUEST_LIGNES);
                break;
            case REPRESENTANT:
                res = context.getString(R.string.REST_REQUEST_REPRESENTANT);
                break;
            case SALES:
                res = context.getString(R.string.REST_REQUEST_SALES);
                break;
            case TIERS:
                res = context.getString(R.string.REST_REQUEST_TIERS);
                break;
        }
        res += (dateTo == null) ? context.getString(R.string.REST_REQUEST_UPDATE) : context.getString(R.string.REST_REQUEST_GET);
        return res;
    }

}