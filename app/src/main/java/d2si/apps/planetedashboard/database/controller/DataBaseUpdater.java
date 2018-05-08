package d2si.apps.planetedashboard.database.controller;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public abstract class DataBaseUpdater extends AsyncTask<Void, Void,Void> {

    private List<List<? extends RealmObject>> objectsToCopy;


    /**
     * Method that execute the http task
     *
     * @param objectsToCopy objects to be copied to database
     */
    public DataBaseUpdater(List<List<? extends RealmObject>> objectsToCopy) {
        this.objectsToCopy = objectsToCopy;
    }

    @Override
    /**
     * Method that execute the http task
     *
     * @param params parameters to use in background
     */
    protected Void doInBackground(Void ... params) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (int i=0;i<objectsToCopy.size();i++)
        {
            if (i==0)
            {
                for (Document document:(ArrayList<Document>) objectsToCopy.get(i)){
                    realm.copyToRealmOrUpdate(document);
                    RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                    lignes.deleteAllFromRealm();
                }
            }
            else {
                for (RealmObject object:objectsToCopy.get(i))
                    realm.copyToRealmOrUpdate(object);
            }
        }

        realm.commitTransaction();
        realm.close();

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