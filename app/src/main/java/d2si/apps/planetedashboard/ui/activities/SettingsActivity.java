package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.background.UpdateJob;
import d2si.apps.planetedashboard.database.DataBaseUtils;
import d2si.apps.planetedashboard.database.controller.SalesController;

/**
 * Preferences activity
 * <p>
 * class that represents the preferences
 *
 * @author younessennadj
 */
public class SettingsActivity extends com.fnp.materialpreferences.PreferenceActivity {
    @Override
    /**
     * Method that create the activity
     *
     * @param savedInstanceState save instance of the activity
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // add the preferences XML
        addPreferencesFromResource(R.xml.preferences);

        // Customize some preferences
        findPreference(getString(R.string.pref_key_sync_delay)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences pref = AppUtils.getSharedPreference(getBaseContext());
                Integer delay = pref.getInt(getString(R.string.pref_key_sync_delay), 60);
                ((EditTextPreference) preference).getEditText().setText(String.valueOf(delay));
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_sync_delay)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                SharedPreferences.Editor editor = AppUtils.getSharedPreferenceEdito(getBaseContext());

                // save in preferences the delay value
                editor.putInt(getString(R.string.pref_key_sync_delay), Integer.parseInt((String) o));
                editor.apply();

                // cancel the update job and start a new job with the new periodic delay value
                SharedPreferences pref = AppUtils.getSharedPreference(getBaseContext());
                int jobID = pref.getInt(getString(R.string.pref_key_update_job_id), -1);
                if (jobID != -1) {
                    UpdateJob.cancelJob(jobID);
                    // start the update job
                    UpdateJob.scheduleJob(getApplicationContext());
                }


                return false;
            }
        });

        findPreference(getString(R.string.pref_key_sync_report)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((EditTextPreference) preference).getEditText().setText(SalesController.getSyncReport());
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_last_sync)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((EditTextPreference) preference).getEditText().setText(DataBaseUtils.getFormattedDate(SalesController.getLastSyncDate()));
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_server)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((EditTextPreference) preference).getEditText().setText(AppUtils.serverName);
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_database)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((EditTextPreference) preference).getEditText().setText(AppUtils.dBName);
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_server)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                AppUtils.serverName = (String) o;
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_database)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                AppUtils.dBName = (String) o;
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_database_user)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((EditTextPreference) preference).getEditText().setText(AppUtils.dBUser);
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_database_user)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                AppUtils.dBUser = (String) o;
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_database_password)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((EditTextPreference) preference).getEditText().setText(AppUtils.dBPassword);
                return false;
            }
        });

        findPreference(getString(R.string.pref_key_database_password)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                // Encrypt the password according to the encryption algorithm
                String hashPass = (String) o;
                try {
                    hashPass = AppUtils.encryptString((String) o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppUtils.dBPassword = hashPass;
                return false;
            }
        });



    }
}