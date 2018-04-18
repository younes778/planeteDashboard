package d2si.apps.planetedashboard.activities;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import classes.AppData;
import d2si.apps.planetedashboard.R;

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

        // set the action bar title and font
        AppData.setActionBarTitle(this,R.string.app_name);

        // initialize the images with drawables
        img_sales.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_tag_text_outline)
                .color(Color.WHITE));
        img_stock.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_briefcase)
                .color(Color.WHITE));
        img_purchase.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_shopping)
                .color(Color.WHITE));
        img_client.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_account)
                .color(Color.WHITE));
        img_providers.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_account_multiple)
                .color(Color.WHITE));


    }
}