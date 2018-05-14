package d2si.apps.planetedashboard.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.iconics.IconicsDrawable;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.database.controller.SalesFragmentDataSetter;
import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.ui.fragments.SalesDayFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesMonthFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesWeekFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesYearFragment;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Main menu Activity
 * <p>
 * Activity that represents the main menu options
 *
 * @author younessennadj
 */
public class MainMenuActivity extends RealmActivity {

    @BindView(R.id.img_sales)
    ImageView img_sales;
    @BindView(R.id.img_stock)
    ImageView img_stock;
    @BindView(R.id.img_purchase)
    ImageView img_purchase;
    @BindView(R.id.img_client)
    ImageView img_client;
    @BindView(R.id.img_providers)
    ImageView img_providers;

    private MaterialDialog dialog;

    /**
     * Method call when Sales button clicked
     */
    @OnClick(R.id.btn_sales)
    void sales() {

        dialog = new MaterialDialog.Builder(MainMenuActivity.this)
                .title(R.string.progress_calculating_title)
                .content(R.string.progress_calculating_content)
                .progress(true, 0)
                .cancelable(false)
                .show();

        new SalesFragmentDataSetter() {
            @Override
            public void onDataSet() {
                dialog.dismiss();
                AppUtils.launchActivity(MainMenuActivity.this, MainActivity.class, true, String.valueOf(MainActivity.FRAGMENT_SALES));
            }
        }.execute();

    }

    /**
     * Method that create the activity
     *
     * @param savedInstanceState The activity instance
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);

        // set the action bar title and font
        AppUtils.setActionBarTitle(this, R.string.app_name);

        // initialize the images with drawables
        img_sales.setImageDrawable(new IconicsDrawable(this).icon(AppUtils.MENU_DRAWABLES.get(0))
                .color(Color.WHITE));
        img_stock.setImageDrawable(new IconicsDrawable(this).icon(AppUtils.MENU_DRAWABLES.get(1))
                .color(Color.WHITE));
        img_purchase.setImageDrawable(new IconicsDrawable(this).icon(AppUtils.MENU_DRAWABLES.get(2))
                .color(Color.WHITE));
        img_client.setImageDrawable(new IconicsDrawable(this).icon(AppUtils.MENU_DRAWABLES.get(3))
                .color(Color.WHITE));
        img_providers.setImageDrawable(new IconicsDrawable(this).icon(AppUtils.MENU_DRAWABLES.get(4))
                .color(Color.WHITE));

    }
}