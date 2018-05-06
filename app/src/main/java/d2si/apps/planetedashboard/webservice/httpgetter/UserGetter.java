package d2si.apps.planetedashboard.webservice.httpgetter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;

public abstract class UserGetter extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private String user;
    private String password;


    /**
     * Method that execute the http task
     *
     * @param context app actual context
     * @param user database user
     * @param password database user password
     */
    public UserGetter(Context context, String user, String password) {
        this.context = context;
        this.user = user;
        this.password = password;
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
            final String url= AppUtils.formGetUrl(context.getString(R.string.REST_SERVER_URL),Integer.parseInt(context.getString(R.string.REST_SERVER_PORT)),context.getString(R.string.REST_REQUEST_USER),new ArrayList<String>(){{add(context.getString(R.string.REST_FIELD_URL));add(context.getString(R.string.REST_FIELD_DB_NAME));add(context.getString(R.string.REST_FIELD_USER));add(context.getString(R.string.REST_FIELD_PASSWORD));}},new ArrayList<String>(){{add(context.getString(R.string.DB_URL));add(context.getString(R.string.DB_NAME));add(user);add(password);}});

            // use the rest template
            RestTemplate restTemplate = new RestTemplate();
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
     * @param sales sales return from the web service
     */
    protected void onPostExecute(Boolean login){
            onPost(login);
    }

    /**
     * Method that execute on task finished
     *
     * @param login is User permitted to connect
     */
    public abstract void onPost(Boolean login);

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}