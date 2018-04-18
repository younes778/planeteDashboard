package d2si.apps.planetedashboard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.realm.Realm;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Realm Activity
 *
 * Activity that extends from the database library
 *
 * @author younessennadj
 */
public class RealmActivity extends AppCompatActivity {

    protected Realm mRealm;

    @Override
    /**
     * Method that create the activity
     *
     * @param savedInstanceState The activity instance
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);
    }

    @Override
    /**
     * Method that executes when the activity is destroyed
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }
}