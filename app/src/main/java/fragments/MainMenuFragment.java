package fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
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
import d2si.apps.planetedashboard.activities.MainActivity;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Main menu fragment
 *
 * Fragment that represents the main menu options
 *
 * @author younessennadj
 */
public class MainMenuFragment extends Fragment {


    View view;
    @BindView(R.id.btn_sales) LinearLayout btn_sales;
    @BindView(R.id.btn_stock) LinearLayout btn_stock;
    @BindView(R.id.btn_purchase) LinearLayout btn_purchase;
    @BindView(R.id.btn_client) LinearLayout btn_client;
    @BindView(R.id.btn_providers) LinearLayout btn_providers;
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
        AppData.loadFragment(getActivity(),new SalesDayFragment());
        AppData.setActionBarTitle(getActivity(),R.string.menu_sales);
    }

    @Override
    /**
     * Method that create the activity
     *
     * @param inflater View Inflater
     * @param container View container
     * @param savedInstanceState The activity instance
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // initialize the font
        typeface(view);
        ButterKnife.bind(this,view);

        // initialize the images with drawables
        img_sales.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_tag_text_outline)
                .color(Color.WHITE));
        img_stock.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_briefcase)
                .color(Color.WHITE));
        img_purchase.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_shopping)
                .color(Color.WHITE));
        img_client.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_account)
                .color(Color.WHITE));
        img_providers.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_account_multiple)
                .color(Color.WHITE));



        return view;
    }
}