package d2si.apps.planetedashboard;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application
 *
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

    }

    /**
     * Method call to initialize the icons library context
     *
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}