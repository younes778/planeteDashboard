package d2si.apps.planetedashboard.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.mikepenz.iconics.IconicsDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;

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

    /**
     * Method call when Sales button clicked
     */
    @OnClick(R.id.btn_sales)
    void sales() {
        AppUtils.launchActivity(this, MainActivity.class, true, String.valueOf(MainActivity.FRAGMENT_SALES));
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