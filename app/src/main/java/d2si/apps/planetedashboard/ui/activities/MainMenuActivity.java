package d2si.apps.planetedashboard.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.mikepenz.iconics.IconicsDrawable;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Main menu Activity
 *
 * Activity that represents the main menu options
 *
 * @author younessennadj
 */
public class MainMenuActivity extends RealmActivity {

    @BindView(R.id.img_sales) ImageView img_sales;
    @BindView(R.id.img_stock) ImageView img_stock;
    @BindView(R.id.img_purchase) ImageView img_purchase;
    @BindView(R.id.img_client) ImageView img_client;
    @BindView(R.id.img_providers) ImageView img_providers;

    /**
     * Method call when Sales button clicked
     *
     */
    @OnClick(R.id.btn_sales) void sales() {
        AppData.launchActivity(this,MainActivity.class,false,String.valueOf(MainActivity.FRAGMENT_SALES));
    }

    /**
     * Method that create the activity
     *
     * @param savedInstanceState The activity instance
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        calendar1.set(Calendar.MONTH,0);
        Date date1 = new Date(calendar1.getTimeInMillis());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH,1);
        calendar2.set(Calendar.MONTH,1);
        Date date2 = new Date(calendar2.getTimeInMillis());
        SalesController.updateSalesByDate(this,date1,date2);

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);

        // set the action bar title and font
        AppData.setActionBarTitle(this,R.string.app_name);

        // initialize the images with drawables
        img_sales.setImageDrawable(new IconicsDrawable(this).icon(AppData.MENU_DRAWABLES.get(0))
                .color(Color.WHITE));
        img_stock.setImageDrawable(new IconicsDrawable(this).icon(AppData.MENU_DRAWABLES.get(1))
                .color(Color.WHITE));
        img_purchase.setImageDrawable(new IconicsDrawable(this).icon(AppData.MENU_DRAWABLES.get(2))
                .color(Color.WHITE));
        img_client.setImageDrawable(new IconicsDrawable(this).icon(AppData.MENU_DRAWABLES.get(3))
                .color(Color.WHITE));
        img_providers.setImageDrawable(new IconicsDrawable(this).icon(AppData.MENU_DRAWABLES.get(4))
                .color(Color.WHITE));


    }
}