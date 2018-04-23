package d2si.apps.planetedashboard.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.ui.fragments.SalesDayFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesMonthFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesWeekFragment;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Main Activity
 *
 * Activity that represents the main functions given by the app including the menu drawer
 *
 * @author younessennadj
 */
public class MainActivity extends RealmActivity{

    public static final int FRAGMENT_SALES = 0;
    public static final int FRAGMENT_STOCK = 1;
    public static final int FRAGMENT_PURCHASE = 2;
    public static final int FRAGMENT_CLIENT = 3;
    public static final int FRAGMENT_PROVIDER = 4;

    @BindView(R.id.tabs) TabLayout tabLayout;
    private Drawer navDrawer;

    @Override
    /**
     * Method that create the activity
     *
     * @param savedInstanceState The activity instance
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);

        // Get the actionBar toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inti fragment with the Main menu with the tabs
        int fragment_to_launch= Integer.parseInt(AppData.getDataFromLaunchedActivity(this));
        setupTabs(fragment_to_launch);

        // Initialize Nav drawer
        new DrawerBuilder().withActivity(this).build();

        // Add Items to the nav drawer
        navDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(new AccountHeaderBuilder()
                        .withActivity(this)
                        .withHeaderBackground(R.drawable.nav_drawer_background)
                        .withAlternativeProfileHeaderSwitching(false)
                        .addProfiles(
                                new ProfileDrawerItem().withName("Client D2SI").withIcon(new IconicsDrawable(this)
                                        .icon(MaterialDesignIconic.Icon.gmi_account_circle)
                                        .color(Color.WHITE))).withCurrentProfileHiddenInList(true).withDividerBelowHeader(false).withAlternativeProfileHeaderSwitching(false).withOnlyMainProfileImageVisible(true).withSelectionListEnabled(false)
                        .build()
                        )
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_sales).withTypeface(AppData.fontApp).withIcon(AppData.MENU_DRAWABLES.get(0)),
                        new PrimaryDrawerItem().withIdentifier(2).withName(R.string.menu_stock).withTypeface(AppData.fontApp).withIcon(AppData.MENU_DRAWABLES.get(1)),
                        new PrimaryDrawerItem().withIdentifier(3).withName(R.string.menu_purchase).withTypeface(AppData.fontApp).withIcon(AppData.MENU_DRAWABLES.get(2)),
                        new PrimaryDrawerItem().withIdentifier(4).withName(R.string.menu_client).withTypeface(AppData.fontApp).withIcon(AppData.MENU_DRAWABLES.get(3)),
                        new PrimaryDrawerItem().withIdentifier(5).withName(R.string.menu_provider).withTypeface(AppData.fontApp).withIcon(AppData.MENU_DRAWABLES.get(4)),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.menu_parameter).withTypeface(AppData.fontApp).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(6).withName(R.string.menu_logout).withTypeface(AppData.fontApp).withIcon(AppData.MENU_DRAWABLES.get(5)),
                        new PrimaryDrawerItem().withIdentifier(7).withName(R.string.menu_setting).withTypeface(AppData.fontApp).withIcon(AppData.MENU_DRAWABLES.get(6))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        navDrawer.closeDrawer();
                        if (position==1) {
                            setupTabs(FRAGMENT_SALES);
                        }
                        return true;
                    }
                })
                .withSelectedItem(fragment_to_launch)
                .build();

        // set the font of the nav drawer header
        typeface(navDrawer.getHeader());


    }

    /**
     * Method that setup the tabs according to the fragment to be load, set the Action Bar title & load the initial fragment
     *
     * @param fragment The fragment that the tabs will be loaded for
     */
    private void setupTabs(int fragment){
        switch (fragment){
            case FRAGMENT_SALES:
                AppData.loadFragment(this,new SalesDayFragment());

                // initialize the action bar title font
                AppData.setActionBarTitle(this,R.string.menu_sales);

                //remove all tabs first
                tabLayout.removeAllTabs();

                // Add different tabs Sales
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_day)));
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_week)));
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_month)));

                // set the font of the tabs
                typeface(tabLayout);

                // Tab Layout of sales click listener
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        // load the right fragment when select
                        switch (tab.getPosition()){
                            case 0:
                                AppData.loadFragment(MainActivity.this,new SalesDayFragment());
                                break;
                            case 1:
                                AppData.loadFragment(MainActivity.this,new SalesWeekFragment());
                                break;
                            case 2:
                                AppData.loadFragment(MainActivity.this,new SalesMonthFragment());
                                break;

                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                // setup tabs
                tabLayout.setVisibility(View.VISIBLE);

                break;
        }
    }

}
