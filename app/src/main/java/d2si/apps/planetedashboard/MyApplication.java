package d2si.apps.planetedashboard;

import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import d2si.apps.planetedashboard.background.UpdateJobCreator;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application
 * <p>
 * Application that represents the main application with specific initialization
 *
 * @author younessennadj
 */
public class MyApplication extends Application {
    @Override
    /**
     * Method that create the application
     *
     */
    public void onCreate() {
        super.onCreate();

        // init the Database Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().schemaVersion(getResources().getInteger(R.integer.db_local_version)).name("myrealm.realm").build();
        // For test delete configuration
        //Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);


        // init the AppUtils resources
        AppUtils.init(getBaseContext());

        // init the update job
        JobManager.create(this).addJobCreator(new UpdateJobCreator());

    }

}