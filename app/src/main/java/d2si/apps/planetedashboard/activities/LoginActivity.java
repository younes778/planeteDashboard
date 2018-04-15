package d2si.apps.planetedashboard.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.balysv.materialripple.MaterialRippleLayout;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import classes.AppData;
import d2si.apps.planetedashboard.R;
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
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_user) ExtendedEditText et_user;
    @BindView(R.id.field_user) TextFieldBoxes field_user;
    @BindView(R.id.et_password) ExtendedEditText et_password;
    @BindView(R.id.field_password) TextFieldBoxes field_password;
    @BindView(R.id.btn_login) MaterialRippleLayout btn_login;

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
            Intent MainActivityLauncher = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(MainActivityLauncher);
            finish();
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

        // init the AppData ressources
        AppData.init(getBaseContext());
        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);
        AppData.setActionBarTitle(this,R.string.activity_login);

        // To Test
        AppData.launchActivity(this,MainActivity.class);

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

    /**
     * Method call to initialize the icons library context
     *
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

}
