package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.balysv.materialripple.MaterialRippleLayout;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.background.UpdateJob;
import d2si.apps.planetedashboard.webservice.datagetter.DataGetter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Activity Login Page
 * <p>
 * This activity is used to login the user
 *
 * @author younessennadj
 */
public class LoginActivity extends RealmActivity {
    @BindView(R.id.et_user)
    ExtendedEditText et_user;
    @BindView(R.id.field_user)
    TextFieldBoxes field_user;
    @BindView(R.id.et_password)
    ExtendedEditText et_password;
    @BindView(R.id.field_password)
    TextFieldBoxes field_password;
    @BindView(R.id.btn_login)
    MaterialRippleLayout btn_login;
    private MaterialDialog dialog;
    private String hashPass;

    /**
     * Method call when Login button clicked
     */
    @OnClick(R.id.btn_login)
    void login() {

        // Validate user
        if (!validateUser())
            field_user.setError(getString(R.string.et_error_not_empty), false);

        // Validate Password
        if (!validatePassword())
            field_password.setError(getString(R.string.et_error_not_empty), false);

        // if all validated launch main activity
        if (validateUser() && validatePassword()) {
            SharedPreferences pref = AppUtils.getSharedPreference(this);
            String user = pref.getString(getString(R.string.pref_key_user), "");
            String password = pref.getString(getString(R.string.pref_key_password), "");


            hashPass=et_password.getText().toString();
            try {
                hashPass = AppUtils.encryptString(et_password.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // check if it is the last user then we don't need to update data
            if (user.equalsIgnoreCase(et_user.getText().toString()) && password.replace("\n","").trim().equals(hashPass.trim())) {
                SharedPreferences.Editor editor = AppUtils.getSharedPreferenceEdito(this);
                // save in preferences the connected user
                editor.putBoolean(getString(R.string.pref_key_connected), true);
                editor.apply();

                // start the update job
                UpdateJob.scheduleJob(getApplicationContext());

                AppUtils.launchActivity(LoginActivity.this, MainMenuActivity.class, true, null);

            } else { // it is a new user therefor we need to update the data

                if (AppUtils.isNetworkAvailable(getBaseContext())) {
                    dialog = new MaterialDialog.Builder(this)
                            .title(R.string.progress_login_title)
                            .content(R.string.progress_login_content)
                            .progress(true, 0)
                            .cancelable(false)
                            .show();

                    Calendar calendar = Calendar.getInstance();
                    final Date date1 = new Date(calendar.getTimeInMillis());
                    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.MONTH, 0);
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    final Date date2 = new Date(calendar.getTimeInMillis());

                    final DataGetter dataGetter = new DataGetter() {
                        @Override
                        public void onSalesUpdate(boolean success) {

                        }

                        @Override
                        public void onSalesGet(boolean success) {
                            dialog.dismiss();
                            if (success) {
                                SharedPreferences.Editor editor = AppUtils.getSharedPreferenceEdito(getBaseContext());
                                // save in preferences the connected user
                                editor.putBoolean(getString(R.string.pref_key_connected), true);
                                editor.putString(getString(R.string.pref_key_user), et_user.getText().toString());
                                editor.putString(getString(R.string.pref_key_password), hashPass);
                                editor.apply();

                                // start the update job
                                UpdateJob.scheduleJob(getApplicationContext());

                                AppUtils.launchActivity(LoginActivity.this, MainMenuActivity.class, true, null);
                            }
                            else
                                Toast.makeText(getBaseContext(), R.string.error_connexion, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onUserUpdate(Boolean user) {
                            dialog.dismiss();
                            if (user == null)
                                Toast.makeText(getBaseContext(), R.string.error_connexion, Toast.LENGTH_SHORT).show();
                            else if (user) {

                                dialog = new MaterialDialog.Builder(LoginActivity.this)
                                        .title(R.string.progress_updating_title)
                                        .content(R.string.progress_updating_content)
                                        .progress(true, 0)
                                        .cancelable(false)
                                        .show();
                                this.getSalesByDate(LoginActivity.this, date2, date1);
                            } else {
                                Toast.makeText(getBaseContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    dataGetter.checkUserCrediants(getBaseContext(), et_user.getText().toString(), hashPass);
                } else
                    Toast.makeText(getBaseContext(), getString(R.string.error_no_connexion), Toast.LENGTH_LONG).show();

            }
        }
    }

    /**
     * Method that create the activity
     *
     * @param savedInstanceState The activity instance
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);

        // set the action bar title and font
        AppUtils.setActionBarTitle(this, R.string.activity_login);

        // hide keyboard on click out of the fields
        et_user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AppUtils.hideKeyboard(view,getBaseContext());
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AppUtils.hideKeyboard(view,getBaseContext());
            }
        });


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
