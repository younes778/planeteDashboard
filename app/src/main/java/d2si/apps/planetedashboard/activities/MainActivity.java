package d2si.apps.planetedashboard.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import classes.AppData;
import d2si.apps.planetedashboard.R;
import fragments.SalesDayFragment;
import fragments.SalesMonthFragment;
import fragments.SalesWeekFragment;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class MainActivity extends AppCompatActivity{

    private Drawer navDrawer;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Apply font to the activity
        typeface(this);

        // Initialize TanLayout
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Add different tabs Sales
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_day)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_week)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_month)));

        // set the font
        typeface(tabLayout);

        // Tab Layout of sales
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        loadFragment(new SalesDayFragment());
                        break;
                    case 1:
                        loadFragment(new SalesWeekFragment());
                        break;
                    case 2:
                        loadFragment(new SalesMonthFragment());

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Get the actionBar toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the action bar title font
        AppData.setActionBarTitle(this,R.string.app_name);


        // For test
        loadFragment(new SalesDayFragment());

        // Nav drawer initialize
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
                        new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_sales).withTypeface(AppData.fontApp).withIcon(CommunityMaterial.Icon.cmd_tag_text_outline),
                        new PrimaryDrawerItem().withIdentifier(2).withName(R.string.menu_stock).withTypeface(AppData.fontApp).withIcon(CommunityMaterial.Icon.cmd_briefcase),
                        new PrimaryDrawerItem().withIdentifier(3).withName(R.string.menu_purchase).withTypeface(AppData.fontApp).withIcon(CommunityMaterial.Icon.cmd_shopping),
                        new PrimaryDrawerItem().withIdentifier(4).withName(R.string.menu_client).withTypeface(AppData.fontApp).withIcon(CommunityMaterial.Icon.cmd_account),
                        new PrimaryDrawerItem().withIdentifier(5).withName(R.string.menu_provider).withTypeface(AppData.fontApp).withIcon(CommunityMaterial.Icon.cmd_account_multiple),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.menu_parameter).withTypeface(AppData.fontApp).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(6).withName(R.string.menu_logout).withTypeface(AppData.fontApp).withIcon(CommunityMaterial.Icon.cmd_logout_variant),
                        new PrimaryDrawerItem().withIdentifier(7).withName(R.string.menu_setting).withTypeface(AppData.fontApp).withIcon(CommunityMaterial.Icon.cmd_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        navDrawer.closeDrawer();
                        if (position==1) {
                            loadFragment(new SalesDayFragment());
                            AppData.setActionBarTitle(MainActivity.this,R.string.menu_sales);
                        }
                        return true;
                    }
                })
                .withSelectedItem(-1)
                .build();

        // set the font of the nav drawer header
        typeface(navDrawer.getHeader());


    }

    // Load fragment into the App
    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment_manager, fragment);
        fragmentTransaction.commit(); // save the changes
    }



    // initialize the icons library context
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
