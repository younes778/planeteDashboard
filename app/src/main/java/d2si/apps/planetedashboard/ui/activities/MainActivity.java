package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.ui.fragments.SalesDayFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesMonthFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesWeekFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesYearFragment;

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
    private Toolbar toolbar;
    private int fragment_to_launch;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inti fragment with the Main menu with the tabs
        fragment_to_launch= Integer.parseInt(AppUtils.getDataFromLaunchedActivity(this));
        setupTabs(fragment_to_launch);

        // setup the nav drawer
        setupNavDrawer();


    }

    private void setupNavDrawer(){
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
                        new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_sales).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(0)),
                        new PrimaryDrawerItem().withIdentifier(2).withName(R.string.menu_stock).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(1)),
                        new PrimaryDrawerItem().withIdentifier(3).withName(R.string.menu_purchase).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(2)),
                        new PrimaryDrawerItem().withIdentifier(4).withName(R.string.menu_client).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(3)),
                        new PrimaryDrawerItem().withIdentifier(5).withName(R.string.menu_provider).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(4)),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.menu_parameter).withTypeface(AppUtils.fontApp).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(6).withName(R.string.menu_logout).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(5)),
                        new PrimaryDrawerItem().withIdentifier(7).withName(R.string.menu_setting).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(6))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        navDrawer.closeDrawer();
                        if (position==1) {
                            setupTabs(FRAGMENT_SALES);
                        }
                        if (position==8){
                            new MaterialDialog.Builder(MainActivity.this)
                                    .title(R.string.dialog_logout_title)
                                    .content(R.string.dialog_logout_content)
                                    .positiveText(R.string.dialog_confirm)
                                    .negativeText(R.string.dialog_cancel)
                                    .cancelable(false)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            SharedPreferences.Editor editor = AppUtils.getSharedPreferenceEdito(MainActivity.this);
                                            editor.putBoolean(getString(R.string.pref_is_connected), false);
                                            editor.apply();
                                            AppUtils.launchActivity(MainActivity.this,LoginActivity.class,true,null);
                                        }
                                    })
                                    .show();
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
                AppUtils.loadFragment(this,new SalesDayFragment());

                // initialize the action bar title font
                AppUtils.setActionBarTitle(this,R.string.menu_sales);

                //remove all tabs first
                tabLayout.removeAllTabs();

                // Add different tabs Sales
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_day)));
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_week)));
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_month)));
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_year)));

                // set the font of the tabs
                typeface(tabLayout);

                // Tab Layout of sales click listener
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        // load the right fragment when select
                        switch (tab.getPosition()){
                            case 0:
                                AppUtils.loadFragment(MainActivity.this,new SalesDayFragment());
                                break;
                            case 1:
                                AppUtils.loadFragment(MainActivity.this,new SalesWeekFragment());
                                break;
                            case 2:
                                AppUtils.loadFragment(MainActivity.this,new SalesMonthFragment());
                                break;
                            case 3:
                                AppUtils.loadFragment(MainActivity.this,new SalesYearFragment());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter_none:

                return true;
            case R.id.filter_item:

                return true;
            case R.id.filter_client:

                return true;
            case R.id.filter_representant:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
