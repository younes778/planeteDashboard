package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class SettingsActivity extends com.fnp.materialpreferences.PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        typeface(this);

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
                ((EditTextPreference) preference).getEditText().setText(AppUtils.getFormattedDate(SalesController.getLastSyncDate()));
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


    }
}