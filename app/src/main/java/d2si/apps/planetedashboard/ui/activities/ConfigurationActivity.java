package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.webservice.controller.ServerController;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Activity Configuration page
 * <p>
 * This activity is used to configure the app
 *
 * @author younessennadj
 */
public class ConfigurationActivity extends RealmActivity {
    @BindView(R.id.et_server)
    ExtendedEditText et_server;
    @BindView(R.id.field_server)
    TextFieldBoxes field_server;
    @BindView(R.id.et_database)
    ExtendedEditText et_database;
    @BindView(R.id.field_database)
    TextFieldBoxes field_database;
    @BindView(R.id.et_user)
    ExtendedEditText et_user;
    @BindView(R.id.field_user)
    TextFieldBoxes field_user;
    @BindView(R.id.et_password)
    ExtendedEditText et_password;
    @BindView(R.id.field_password)
    TextFieldBoxes field_password;
    private MaterialDialog dialog;
    private String hashPass;

    /**
     * Method call when test connectivity button clicked
     */
    @OnClick(R.id.btn_connectivity_test)
    void testConnectivity() {

        // Validate server
        if (!validateServer())
            field_server.setError(getString(R.string.et_error_not_empty), false);

        // Validate database name
        if (!validateDatabase())
            field_database.setError(getString(R.string.et_error_not_empty), false);

        // Validate user
        if (!validateUser())
            field_user.setError(getString(R.string.et_error_not_empty), false);

        // Validate Password
        if (!validatePassword())
            field_password.setError(getString(R.string.et_error_not_empty), false);



        // if all validated launch main activity
        if (validateServer() && validateDatabase() && validateUser() && validatePassword()) {

            // Encrypt the password according to the encryption algorithm
            hashPass = et_password.getText().toString();
            try {
                hashPass = AppUtils.encryptString(et_password.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (AppUtils.isNetworkAvailable(getBaseContext())) {
                dialog = new MaterialDialog.Builder(this)
                        .title(R.string.progress_server_title)
                        .content(R.string.progress_server_content)
                        .progress(true, 0)
                        .cancelable(false)
                        .show();

                new ServerController(getBaseContext(), et_server.getText().toString(), et_database.getText().toString(),et_user.getText().toString(),hashPass) {
                    @Override
                    public void onPost(Boolean checked) {
                        dialog.dismiss();
                        if (checked == null)
                            Toast.makeText(getBaseContext(), R.string.error_connexion, Toast.LENGTH_SHORT).show();
                        else if (checked) {
                            SharedPreferences.Editor editor = AppUtils.getSharedPreferenceEdito(getBaseContext());
                            // save in preferences the database server
                            AppUtils.serverName = et_server.getText().toString();
                            AppUtils.dBName = et_database.getText().toString();
                            AppUtils.dBUser = et_user.getText().toString();
                            AppUtils.dBPassword = hashPass;

                            editor.putString(getString(R.string.pref_key_server), et_server.getText().toString());
                            editor.putString(getString(R.string.pref_key_database), et_database.getText().toString());
                            editor.putString(getString(R.string.pref_key_database_user), et_user.getText().toString());
                            editor.putString(getString(R.string.pref_key_database_password), hashPass);
                            editor.apply();

                            AppUtils.launchActivity(ConfigurationActivity.this, LoginActivity.class, true, null);
                        } else {
                            Toast.makeText(getBaseContext(), R.string.error_server, Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            } else
                Toast.makeText(getBaseContext(), getString(R.string.error_no_connexion), Toast.LENGTH_LONG).show();


        }
    }

    /**
     * Method that create the activity
     *
     * @param savedInstanceState The activity instance
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);

        // set the action bar title and font
        AppUtils.setActionBarTitle(this, R.string.app_name);

        // hide keyboard on click out of the fields
        et_server.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AppUtils.hideKeyboard(view, getBaseContext());
            }
        });

        et_database.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AppUtils.hideKeyboard(view, getBaseContext());
            }
        });

        et_user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AppUtils.hideKeyboard(view, getBaseContext());
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AppUtils.hideKeyboard(view, getBaseContext());
            }
        });


    }

    /**
     * Method call to validate the server syntax
     */
    private boolean validateServer() {
        // check field not empty
        return !et_server.getText().toString().equals("");
    }

    /**
     * Method call to validate the database syntax
     */
    private boolean validateDatabase() {
        // check field not empty
        return !et_database.getText().toString().equals("");
    }

    /**
     * Method call to validate the user syntax
     */
    private boolean validateUser() {
        // check field not empty
        return !et_user.getText().toString().equals("");
    }

    /**
     * Method call to validate the password syntax
     */
    private boolean validatePassword() {
        // check field not empty
        return !et_password.getText().toString().equals("");
    }


}
