package d2si.apps.planetedashboard.database.controller;

import android.os.AsyncTask;

import java.util.List;

import d2si.apps.planetedashboard.AppUtils;
import io.realm.RealmObject;

/**
 * class that copy data get from server to  database in an asynchronous task
 *
 * @author younessennadj
 */
public abstract class DataBaseHandler extends AsyncTask<Void, Void, Void> {

    private List<List<? extends RealmObject>> objectsToCopy;


    /**
     * Method that execute the http task
     *
     * @param objectsToCopy objects to be copied to database
     */
    public DataBaseHandler(List<List<? extends RealmObject>> objectsToCopy) {
        this.objectsToCopy = objectsToCopy;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected Void doInBackground(Void... params) {
        AppUtils.addObjectToRealm(objectsToCopy);
        return null;
    }

    @Override
    /**
     * Method that execute the http task
     *
     */
    protected void onPostExecute(Void v) {
        onPost();
    }

    /**
     * Method that execute on task finished
     */
    public abstract void onPost();

}