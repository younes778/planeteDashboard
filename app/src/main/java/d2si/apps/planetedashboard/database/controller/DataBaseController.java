package d2si.apps.planetedashboard.database.controller;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import d2si.apps.planetedashboard.database.DataBaseUtils;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import d2si.apps.planetedashboard.database.data.QuickAccessData;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * class that copy data get from server to  database in an asynchronous task
 *
 * @author younessennadj
 */
public abstract class DataBaseController extends AsyncTask<Void, Void, Void> {

    private List<List<? extends RealmObject>> objectsToCopy;
    private boolean getter;


    /**
     * Method that execute the http task
     *
     * @param objectsToCopy objects to be copied to database
     */
    public DataBaseController(List<List<? extends RealmObject>> objectsToCopy, boolean getter) {
        this.objectsToCopy = objectsToCopy;
        this.getter = getter;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected Void doInBackground(Void... params) {
        if (getter) {
            DataBaseUtils.addObjectToRealm(objectsToCopy);
            DataBaseUtils.addOrUpdateOneObjectToRealm(new QuickAccessData(SalesController.getDayData(), SalesController.getWeekData(), SalesController.getMonthData(), SalesController.getYearData()));
        } else {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            // copy all data or upadate when get from server
            // Exception is made for lines if a document has changed
            // all lines must be deleted and copied to database
            for (int i = 0; i < objectsToCopy.size(); i++) {
                if (i == 0) {
                    for (Document document : (ArrayList<Document>) objectsToCopy.get(i)) {
                        realm.copyToRealmOrUpdate(document);
                        RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                        lignes.deleteAllFromRealm();
                    }
                } else {
                    for (RealmObject object : objectsToCopy.get(i))
                        realm.copyToRealmOrUpdate(object);
                }
            }

            realm.commitTransaction();
            realm.close();

            DataBaseUtils.addOrUpdateOneObjectToRealm(new QuickAccessData(SalesController.getDayData(), SalesController.getWeekData(), SalesController.getMonthData(), SalesController.getYearData()));
        }
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