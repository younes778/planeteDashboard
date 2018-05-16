package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.ArticlesController;
import d2si.apps.planetedashboard.database.controller.ClientsController;
import d2si.apps.planetedashboard.database.controller.RepresentantController;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.database.controller.SalesFragmentDataSetter;
import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Representant;
import d2si.apps.planetedashboard.database.data.Tiers;
import d2si.apps.planetedashboard.ui.adapters.ChoiceRecyclerAdapter;
import d2si.apps.planetedashboard.ui.data.FilterCheckBox;
import d2si.apps.planetedashboard.ui.fragments.SalesDayFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesMonthFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesWeekFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesYearFragment;
import d2si.apps.planetedashboard.webservice.datagetter.DataGetter;
import fr.ganfra.materialspinner.MaterialSpinner;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Main Activity
 * <p>
 * Activity that represents the main functions given by the app including the menu drawer
 *
 * @author younessennadj
 */
public class MainActivity extends RealmActivity {

    public static final int FRAGMENT_SALES = 0;
    public static final int FRAGMENT_STOCK = 1;
    public static final int FRAGMENT_PURCHASE = 2;
    public static final int FRAGMENT_CLIENT = 3;
    public static final int FRAGMENT_PROVIDER = 4;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    private Drawer navDrawer;
    private Toolbar toolbar;
    private int fragment_to_launch;

    private ArrayList<FilterCheckBox> dataToShow;
    private ArrayList<Article> articles;
    private ArrayList<Tiers> clients;
    private ArrayList<Representant> representants;
    private ArrayList<String> families;
    private ChoiceRecyclerAdapter choiceRecyclerAdapter;
    private RecyclerView choiceList;
    private MaterialSpinner showSpinner;
    private MaterialSpinner familySpinner;
    private MaterialDialog dialog;

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
        fragment_to_launch = Integer.parseInt(AppUtils.getDataFromLaunchedActivity(this));
        setupTabs(fragment_to_launch);

        // setup the nav drawer
        setupNavDrawer();


    }

    private void setupNavDrawer() {
        // Initialize Nav drawer
        new DrawerBuilder().withActivity(this).build();

        // Get user name
        SharedPreferences pref = AppUtils.getSharedPreference(this);
        String userName = pref.getString(getString(R.string.pref_key_user), "Client D2SI");

        // Add Items to the nav drawer
        navDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(new AccountHeaderBuilder()
                        .withActivity(this)
                        .withHeaderBackground(R.drawable.nav_drawer_background)
                        .withAlternativeProfileHeaderSwitching(false)
                        .addProfiles(
                                new ProfileDrawerItem().withName(userName).withIcon(new IconicsDrawable(this)
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
                        new PrimaryDrawerItem().withIdentifier(7).withName(R.string.menu_setting).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(6)),
                        new PrimaryDrawerItem().withIdentifier(8).withName(R.string.menu_sync).withTypeface(AppUtils.fontApp).withIcon(AppUtils.MENU_DRAWABLES.get(7))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        navDrawer.closeDrawer();
                        switch (position) {
                            case 1: // sales fragment
                                fragment_to_launch = 0;
                                setupTabs(fragment_to_launch);
                                break;
                            case 8: // logout
                                navDrawer.setSelection(fragment_to_launch + 1);
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
                                                editor.putBoolean(getString(R.string.pref_key_connected), false);
                                                editor.apply();
                                                AppUtils.launchActivity(MainActivity.this, LoginActivity.class, true, null);
                                            }
                                        })
                                        .show();
                                break;
                            case 9: // settings
                                navDrawer.setSelection(fragment_to_launch + 1);
                                AppUtils.launchActivity(MainActivity.this,SettingsActivity.class,false,null);
                                break;
                            case 10: // sync data
                                navDrawer.setSelection(fragment_to_launch + 1);

                                // update data

                                if (AppUtils.isNetworkAvailable(getBaseContext())) {
                                    dialog = new MaterialDialog.Builder(MainActivity.this)
                                            .title(R.string.progress_updating_title)
                                            .content(R.string.progress_updating_content)
                                            .progress(true, 0)
                                            .cancelable(false)
                                            .show();
                                    final DataGetter dataGetter = new DataGetter() {
                                        @Override
                                        public void onSalesUpdate(boolean success) {
                                            dialog.dismiss();
                                            if (success) {
                                                dialog = new MaterialDialog.Builder(MainActivity.this)
                                                        .title(R.string.progress_calculating_title)
                                                        .content(R.string.progress_calculating_content)
                                                        .progress(true, 0)
                                                        .cancelable(false)
                                                        .show();

                                                new SalesFragmentDataSetter() {
                                                    @Override
                                                    public void onDataSet() {
                                                        dialog.dismiss();
                                                        setupTabs(fragment_to_launch);
                                                    }
                                                }.execute();
                                            } else
                                                Toast.makeText(getBaseContext(), R.string.error_connexion, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onSalesGet(boolean success) {

                                        }

                                        @Override
                                        public void onUserUpdate(Boolean user) {

                                        }
                                    };

                                    dataGetter.updateSalesByDate(getBaseContext(), SalesController.getLastSyncDate());
                                }
                                else Toast.makeText(getBaseContext(),getString(R.string.error_no_connexion),Toast.LENGTH_LONG).show();
                                break;

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
    private void setupTabs(int fragment) {
        switch (fragment) {
            case FRAGMENT_SALES:
                AppUtils.loadFragment(this, new SalesDayFragment());

                // initialize the action bar title font
                AppUtils.setActionBarTitle(this, R.string.menu_sales);

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
                        switch (tab.getPosition()) {
                            case 0:
                                AppUtils.loadFragment(MainActivity.this, new SalesDayFragment());
                                break;
                            case 1:
                                AppUtils.loadFragment(MainActivity.this, new SalesWeekFragment());
                                break;
                            case 2:
                                AppUtils.loadFragment(MainActivity.this, new SalesMonthFragment());
                                break;
                            case 3:
                                AppUtils.loadFragment(MainActivity.this, new SalesYearFragment());
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
    /**
     * Method that create the menu
     *
     * @param menu the menu created
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return true;
    }

    /**
     * Method that setup the filter
     *
     * @param filter the filter type
     */
    public void setupFilter(SalesController.FILTER filter) {

        switch (filter) {
            case NONE: // if none apply no filter
                if (SalesController.filter!=SalesController.FILTER.NONE) {
                    SalesController.filter = SalesController.FILTER.NONE;
                    SalesController.filters = null;

                    SalesController.updateSalesFragment();
                    setupTabs(0);
                }
                break;
            case ITEM: {// if item setup articles ,spinner show (Label,Id) and spinner familly and the select all function
                articles = ArticlesController.getAllArticles();
                dataToShow = FilterCheckBox.getCheckBoxListFromText(ArticlesController.getArticlesLabel(articles));
                families = ArticlesController.getArticlesFamilies();
                choiceRecyclerAdapter = new ChoiceRecyclerAdapter(getBaseContext(), dataToShow);


                MaterialDialog filterDialog = new MaterialDialog.Builder(this)
                        .title(R.string.dialog_filter_title)
                        .customView(R.layout.dialog_filter, false)
                        .autoDismiss(false)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .neutralText(R.string.dialog_select_all)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dia, @NonNull DialogAction which) {
                                SalesController.filter = SalesController.FILTER.ITEM;
                                SalesController.filters = ArticlesController.getArticlesSubList(articles, FilterCheckBox.getItemSelected(dataToShow));

                                dialog = new MaterialDialog.Builder(MainActivity.this)
                                        .title(R.string.progress_calculating_title)
                                        .content(R.string.progress_calculating_content)
                                        .progress(true, 0)
                                        .cancelable(false)
                                        .show();

                                new SalesFragmentDataSetter() {
                                    @Override
                                    public void onDataSet() {
                                        dialog.dismiss();
                                        setupTabs(0);
                                    }
                                }.execute();

                                dia.dismiss();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                FilterCheckBox.selectAll(dataToShow);
                                updateRecycler();
                            }
                        })
                        .show();

                View dialogView = filterDialog.getCustomView();
                typeface(dialogView);
                choiceList = dialogView.findViewById(R.id.recycler_choice);

                // setup show spinner
                showSpinner = dialogView.findViewById(R.id.spinner_show);
                final ArrayAdapter<String> showAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, getResources().getStringArray(R.array.spinner_filter_array_show));
                showSpinner.setAdapter(showAdapter);

                // setup family spinner
                familySpinner = dialogView.findViewById(R.id.spinner_family);
                ArrayAdapter<String> familyAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, families);
                familySpinner.setAdapter(familyAdapter);

                showSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        updateDataByCategory(showSpinner.getSelectedItemPosition(), SalesController.FILTER.ITEM);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                familySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == -1) {
                            articles = ArticlesController.getAllArticles();
                        } else {
                            articles = ArticlesController.getArticlebyFamily(ArticlesController.getAllArticles(), families.get(i));
                        }

                        updateDataByCategory(showSpinner.getSelectedItemPosition(), SalesController.FILTER.ITEM);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                choiceList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                choiceList.setAdapter(choiceRecyclerAdapter);
            }
            break;
            case CLIENT: { // if client setup tiers ,spinner show (Label,Id) and spinner familly and the select all function
                clients = ClientsController.getAllClients();
                dataToShow = FilterCheckBox.getCheckBoxListFromText(ClientsController.getClientsLabel(clients));
                families = ClientsController.getClientsFamilies();
                choiceRecyclerAdapter = new ChoiceRecyclerAdapter(getBaseContext(), dataToShow);


               dialog = new MaterialDialog.Builder(this)
                        .title(R.string.dialog_filter_title)
                        .customView(R.layout.dialog_filter, false)
                        .autoDismiss(false)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .neutralText(R.string.dialog_select_all)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dia, @NonNull DialogAction which) {
                                SalesController.filter = SalesController.FILTER.CLIENT;
                                SalesController.filters = ClientsController.getClientsSubList(clients, FilterCheckBox.getItemSelected(dataToShow));
                                dialog.dismiss();
                                dialog = new MaterialDialog.Builder(MainActivity.this)
                                        .title(R.string.progress_calculating_title)
                                        .content(R.string.progress_calculating_content)
                                        .progress(true, 0)
                                        .cancelable(false)
                                        .show();

                                new SalesFragmentDataSetter() {
                                    @Override
                                    public void onDataSet() {
                                        dialog.dismiss();
                                        setupTabs(0);
                                    }
                                }.execute();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                FilterCheckBox.selectAll(dataToShow);
                                updateRecycler();
                            }
                        })
                        .show();

                View dialogView = dialog.getCustomView();
                typeface(dialogView);
                choiceList = dialogView.findViewById(R.id.recycler_choice);

                // setup show spinner
                showSpinner = dialogView.findViewById(R.id.spinner_show);
                final ArrayAdapter<String> showAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, getResources().getStringArray(R.array.spinner_filter_array_show));
                showSpinner.setAdapter(showAdapter);

                // setup family spinner
                familySpinner = dialogView.findViewById(R.id.spinner_family);
                ArrayAdapter<String> familyAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, families);
                familySpinner.setAdapter(familyAdapter);

                showSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        updateDataByCategory(showSpinner.getSelectedItemPosition(), SalesController.FILTER.CLIENT);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                familySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == -1) {
                            clients = ClientsController.getAllClients();
                        } else {
                            clients = ClientsController.getClientsbyFamily(ClientsController.getAllClients(), families.get(i));
                        }

                        updateDataByCategory(showSpinner.getSelectedItemPosition(), SalesController.FILTER.CLIENT);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                choiceList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                choiceList.setAdapter(choiceRecyclerAdapter);
            }
            break;
            case REPRESENTANT: {// if item setup articles , spinner familly and the select all function
                representants = RepresentantController.getAllRepresentants();
                dataToShow = FilterCheckBox.getCheckBoxListFromText(RepresentantController.getRepresentantsLabel(representants));
                choiceRecyclerAdapter = new ChoiceRecyclerAdapter(getBaseContext(), dataToShow);


                dialog = new MaterialDialog.Builder(this)
                        .title(R.string.dialog_filter_title)
                        .customView(R.layout.dialog_filter, false)
                        .autoDismiss(false)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .neutralText(R.string.dialog_select_all)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dia, @NonNull DialogAction which) {
                                SalesController.filter = SalesController.FILTER.REPRESENTANT;
                                SalesController.filters = RepresentantController.getRepresentantSubList(representants, FilterCheckBox.getItemSelected(dataToShow));
                                dialog.dismiss();
                                dialog = new MaterialDialog.Builder(MainActivity.this)
                                        .title(R.string.progress_calculating_title)
                                        .content(R.string.progress_calculating_content)
                                        .progress(true, 0)
                                        .cancelable(false)
                                        .show();

                                new SalesFragmentDataSetter() {
                                    @Override
                                    public void onDataSet() {
                                        dialog.dismiss();
                                        setupTabs(0);
                                    }
                                }.execute();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                FilterCheckBox.selectAll(dataToShow);
                                updateRecycler();
                            }
                        })
                        .show();

                View dialogView = dialog.getCustomView();
                typeface(dialogView);
                choiceList = dialogView.findViewById(R.id.recycler_choice);

                // setup show spinner
                showSpinner = dialogView.findViewById(R.id.spinner_show);
                final ArrayAdapter<String> showAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, getResources().getStringArray(R.array.spinner_filter_array_show));
                showSpinner.setAdapter(showAdapter);

                // setup families
                dialogView.findViewById(R.id.ln_family).setVisibility(View.GONE);


                showSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        updateDataByCategory(showSpinner.getSelectedItemPosition(), SalesController.FILTER.REPRESENTANT);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                choiceList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                choiceList.setAdapter(choiceRecyclerAdapter);
            }
            break;
        }
    }

    /**
     * Method that update data to show according to the spinner show and the filter
     *
     * @param showPosition show label or id
     * @param filter       the actual filter applied
     */
    private void updateDataByCategory(int showPosition, SalesController.FILTER filter) {
        if (showPosition == 2) {
            if (filter == SalesController.FILTER.ITEM)
                dataToShow = FilterCheckBox.getCheckBoxListFromText(ArticlesController.getArticlesId(articles));

            if (filter == SalesController.FILTER.CLIENT)
                dataToShow = FilterCheckBox.getCheckBoxListFromText(ClientsController.getClientsId(clients));

            if (filter == SalesController.FILTER.REPRESENTANT)
                dataToShow = FilterCheckBox.getCheckBoxListFromText(RepresentantController.getRepresentantsId(representants));

        } else {
            if (filter == SalesController.FILTER.ITEM)
                dataToShow = FilterCheckBox.getCheckBoxListFromText(ArticlesController.getArticlesLabel(articles));

            if (filter == SalesController.FILTER.CLIENT)
                dataToShow = FilterCheckBox.getCheckBoxListFromText(ClientsController.getClientsLabel(clients));

            if (filter == SalesController.FILTER.REPRESENTANT)
                dataToShow = FilterCheckBox.getCheckBoxListFromText(RepresentantController.getRepresentantsLabel(representants));

        }
        updateRecycler();
    }

    /**
     * Method that update the recycler setting the adapter with data to show
     */
    private void updateRecycler() {
        choiceRecyclerAdapter = new ChoiceRecyclerAdapter(getBaseContext(), dataToShow);
        choiceList.setAdapter(choiceRecyclerAdapter);
    }

    @Override
    /**
     * Method that executre the callback of a menu item selected
     *
     * @param item the menu item selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter_none:
                setupFilter(SalesController.FILTER.NONE);
                return true;
            case R.id.filter_item:
                setupFilter(SalesController.FILTER.ITEM);
                return true;

            case R.id.filter_client:
                setupFilter(SalesController.FILTER.CLIENT);
                return true;
            case R.id.filter_representant:
                setupFilter(SalesController.FILTER.REPRESENTANT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    /**
     * Method that on button back clicked
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppUtils.launchActivity(MainActivity.this, MainMenuActivity.class, true, null);
        }
        return true;
    }

}
