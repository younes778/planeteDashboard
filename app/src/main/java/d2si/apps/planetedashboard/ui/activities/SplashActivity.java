package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Activity splash screen
 * <p>
 * This activity is used to start the application
 *
 * @author younessennadj
 */
public class SplashActivity extends RealmActivity {

    /**
     * Method that create the activity
     *
     * @param savedInstanceState The activity instance
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //set the activity and actionBar font
        typeface(this);

        // stop the activity for some second
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Test if connexion isnot established with server
                if (AppUtils.serverName.equals(""))  // launch the configuration activity
                    AppUtils.launchActivity(SplashActivity.this, ConfigurationActivity.class, true, null);
                else {
                    // Test if already logged
                    SharedPreferences pref = AppUtils.getSharedPreference(getBaseContext());
                    Boolean isLogged = pref.getBoolean(getString(R.string.pref_key_connected), false);
                    if (isLogged)  // launched the main menu activity
                        AppUtils.launchActivity(SplashActivity.this, MainMenuActivity.class, true, null);
                    else  // launch the login activity
                        AppUtils.launchActivity(SplashActivity.this, LoginActivity.class, true, null);
                }
            }
        }, 3000);


    }


}
