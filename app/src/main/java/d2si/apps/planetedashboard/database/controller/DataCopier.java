package d2si.apps.planetedashboard.database.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.data.Article;
import io.realm.RealmObject;

public abstract class DataCopier extends AsyncTask<Void, Void,Void> {

    private List<List<? extends RealmObject>> objectsToCopy;


    /**
     * Method that execute the http task
     *
     * @param objectsToCopy objects to be copied to database
     */
    public DataCopier(List<List<? extends RealmObject>> objectsToCopy) {
        this.objectsToCopy = objectsToCopy;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected Void doInBackground(Void ... params) {
        AppUtils.addObjectToRealm(objectsToCopy);
        return null;
    }

    @Override
    /**
     * Method that execute the http task
     *
     */
    protected void onPostExecute(Void v){
        onPost();
    }

    /**
     * Method that execute on task finished
     *
     */
    public abstract void onPost();

}