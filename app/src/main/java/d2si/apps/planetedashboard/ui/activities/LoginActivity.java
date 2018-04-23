package d2si.apps.planetedashboard.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.balysv.materialripple.MaterialRippleLayout;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.webservice.datagetter.DataGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesGetter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Activity Login Page
 *
 * This activity is used to login the user
 *
 * @author younessennadj
 */
public class LoginActivity extends RealmActivity {
    @BindView(R.id.et_user) ExtendedEditText et_user;
    @BindView(R.id.field_user) TextFieldBoxes field_user;
    @BindView(R.id.et_password) ExtendedEditText et_password;
    @BindView(R.id.field_password) TextFieldBoxes field_password;
    @BindView(R.id.btn_login) MaterialRippleLayout btn_login;
    MaterialDialog dialog;

    /**
     * Method call when Login button clicked
     *
     */
    @OnClick(R.id.btn_login) void login() {

        // Validate user
        if (!validateUser())
            field_user.setError(getString(R.string.et_error_not_empty), false);

        // Validate Password
        if (!validatePassword())
            field_password.setError(getString(R.string.et_error_not_empty),false);

        // if all validated launch main activity
        if (validateUser() && validatePassword()) {
            dialog = new MaterialDialog.Builder(this)
                    .title(R.string.progress_updating_title)
                    .content(R.string.progress_updating_content)
                    .progress(true, 0)
                    .cancelable(false)
                    .show();

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.DAY_OF_MONTH,1);
            calendar1.set(Calendar.MONTH,0);
            Date date1 = new Date(calendar1.getTimeInMillis());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.DAY_OF_MONTH,1);
            calendar2.set(Calendar.MONTH,2);
            Date date2 = new Date(calendar2.getTimeInMillis());

            new DataGetter() {
                @Override
                public void onSalesUpdate() {
                    dialog.dismiss();
                    AppData.launchActivity(LoginActivity.this, MainMenuActivity.class, true, null);
                }
            }.updateSalesByDate(this,date1,date2);
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
        AppData.setActionBarTitle(this,R.string.activity_login);

        //Test
        //if (AppData.VERSION_TEST)
          //  AppData.launchActivity(this,MainMenuActivity.class,true,null);

    }

    /**
     * Method call to validate the user syntax
     *
     */
    private boolean validateUser(){
        // check field not empty
        return !et_user.getText().toString().equals("");
    }

    /**
     * Method call to validate the password syntax
     *
     */
    private boolean validatePassword(){
        // check field not empty
        return !et_password.getText().toString().equals("");
    }



}
