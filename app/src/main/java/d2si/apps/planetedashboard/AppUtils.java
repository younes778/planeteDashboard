package d2si.apps.planetedashboard;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.typeface.IIcon;
import com.norbsoft.typefacehelper.ActionBarHelper;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.realm.Realm;
import io.realm.RealmObject;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * App d2si.apps.planetedashboard.database.data class
 * <p>
 * Class regrouping the app static d2si.apps.planetedashboard.database.data and methods
 *
 * @author younessennadj
 */
public class AppUtils {

    public static String dateFormat = "dd-MMM-yyyy HH:mm:ss";
    public static String serverName;
    public static String dBName;
    public static int CONNEXION_TIMEOUT = 20000;
    public static int READ_TIMEOUT = 5000000;
    public static ArrayList<IIcon> MENU_DRAWABLES = new ArrayList() {{
        add(CommunityMaterial.Icon.cmd_tag_text_outline);
        add(CommunityMaterial.Icon.cmd_package_variant_closed);
        add(CommunityMaterial.Icon.cmd_cart_outline);
        add(CommunityMaterial.Icon.cmd_account_outline);
        add(CommunityMaterial.Icon.cmd_account_multiple_outline);
        add(CommunityMaterial.Icon.cmd_logout_variant);
        add(CommunityMaterial.Icon.cmd_tune_vertical);
        add(CommunityMaterial.Icon.cmd_sync);
    }};

    public static boolean VERSION_TEST = true;
    public static String ACTIVITY_DATA = "Data";
    public static List<String> CHART_COLORS = new ArrayList<>();
    public static Typeface fontApp;
    public static Typeface fontAppBold;

    /**
     * Method that initialize the data resources
     *
     * @param context App actual context
     */
    public static void init(Context context) {
        addChartColors(context);
        initializeFonts(context);
        initServer(context);
    }

    /**
     * Method that initialize server and database name
     *
     * @param context App actual context
     */
    public static void initServer(Context context) {
        SharedPreferences pref = AppUtils.getSharedPreference(context);
        serverName = pref.getString(context.getString(R.string.pref_key_server), "");
        dBName = pref.getString(context.getString(R.string.pref_key_database), "");
    }

    /**
     * Method that initialize app fonts
     *
     * @param context App actual context
     */
    public static void initializeFonts(Context context) {

        // init fonts from assests
        fontApp = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont.ttf");
        fontAppBold = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont_Bold.ttf");

        // initialize the default font
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.BOLD, AppUtils.fontApp)
                .create();
        TypefaceHelper.init(typeface);

    }

    /**
     * Method that add chart colors
     *
     * @param context App actual context
     */
    public static void addChartColors(Context context) {
        CHART_COLORS.add("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorPrimary)).substring(2));
        CHART_COLORS.add("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorAccent)).substring(2));
        CHART_COLORS.add("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorPrimaryDark)).substring(2));
    }

    /**
     * Method that set ActionBar title with specific font
     *
     * @param activity actual activity
     * @param resource actionbar title resource
     */
    public static void setActionBarTitle(Activity activity, int resource) {
        ActionBarHelper.setTitle(
                ((AppCompatActivity) activity).getSupportActionBar(),
                typeface(activity, resource));
    }

    /**
     * Method that return today date formatted 'DayName, month day,year'
     *
     * @return today date formatted
     */
    public static String getDayDateFormatted() {
        Date date = new Date();
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    /**
     * Method that return yesterday date formatted 'DayName, month day,year'
     *
     * @return yesterday date formatted
     */
    public static String getYesterdayDateFormatted() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        Date date = new Date(calendar.getTimeInMillis());
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    /**
     * Method that return this week date formatted 'month day1-day7'
     *
     * @param context App actual context
     * @return this week date formatted
     */
    public static String getWeekDateFormatted(Context context) {
        Calendar calLastWeek = Calendar.getInstance();
        calLastWeek.set(Calendar.DAY_OF_YEAR, calLastWeek.get(Calendar.DAY_OF_YEAR) - 7);
        return DateUtils.formatDateRange(context, calLastWeek.getTimeInMillis(), Calendar.getInstance().getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * Method that return last week date formatted 'month day1-day7'
     *
     * @param context App actual context
     * @return last week date formatted
     */
    public static String getLastWeekDateFormatted(Context context) {
        Calendar calLastWeek = Calendar.getInstance();
        calLastWeek.set(Calendar.DAY_OF_YEAR, calLastWeek.get(Calendar.DAY_OF_YEAR) - 8);
        Calendar calLast2Weeks = Calendar.getInstance();
        calLast2Weeks.set(Calendar.DAY_OF_YEAR, calLastWeek.get(Calendar.DAY_OF_YEAR) - 7);
        return DateUtils.formatDateRange(context, calLast2Weeks.getTimeInMillis(), calLastWeek.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * Method that return this month date formatted 'month'
     *
     * @return this month date formatted
     */
    public static String getMonthFormatted() {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("MMMM").format(cal.getTime());
    }

    /**
     * Method that return last month date formatted 'month day1-day7'
     *
     * @return last month date formatted
     */
    public static String getLastMonthFormatted() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return new SimpleDateFormat("MMMM").format(cal.getTime());
    }

    /**
     * Method that return money value formatted with the currency symbol
     *
     * @param context App actual context
     * @param value   money value
     * @return last month date formatted
     */
    public static String getCurrencyFormatted(Context context, float value, boolean withCurrency) {

        if (withCurrency)
            return context.getString(R.string.currency, context.getResources().getString(R.string.current_currency), new DecimalFormat("#.##").format(value));
        else return new DecimalFormat("#.##").format(value);
    }

    /**
     * Method that launch activity launched from activity caller
     *
     * @param caller    activity caller
     * @param launched  activity class that will be launched
     * @param finishing indicate whether finish the activity caller or not
     * @param extra     d2si.apps.planetedashboard.database.data to be transferred to the activity to be launched
     */
    public static void launchActivity(AppCompatActivity caller, Class launched, boolean finishing, String extra) {
        Intent activityLauncher = new Intent(caller, launched);
        activityLauncher.putExtra(ACTIVITY_DATA, extra);
        caller.startActivity(activityLauncher);
        if (finishing) caller.finish();
    }

    /**
     * Method that will get the d2si.apps.planetedashboard.database.data put by the activity caller
     *
     * @param launched activity that will be launched
     * @return the Data passed from activity to the launched activity
     */
    public static String getDataFromLaunchedActivity(AppCompatActivity launched) {
        Intent activityIntent = launched.getIntent();
        Bundle b = activityIntent.getExtras();
        String result = null;

        if (b != null) {
            result = (String) b.get(ACTIVITY_DATA);
        }

        return result;
    }

    /**
     * Method that load fragment
     *
     * @param activity the activity to load on
     * @param fragment The fragment to load
     */
    public static void loadFragment(Activity activity, Fragment fragment) {

        // create a FragmentManager
        FragmentManager fm = activity.getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment_manager, fragment);
        // save the changes
        fragmentTransaction.commit();
    }

    /**
     * Method that form Url of a get request with format "ServerUrl:port/urlRequest?field1=value1&field2=value2..."
     *
     * @param url        Server url
     * @param port       Server port
     * @param urlRequest the service requested
     * @param fields     Fields name to be filled to the get request
     * @param values     Values of the fields of the get request
     * @return Urf formatted for a get
     */
    public static String formGetUrl(String url, int port, String urlRequest, ArrayList<String> fields, ArrayList<String> values) {
        String res = url + ":" + port + "/" + urlRequest;
        if (fields != null)
            for (int i = 0; i < fields.size(); i++) {
                if (i == 0) res = res + "?";
                res = res + fields.get(i) + "=" + values.get(i);
                if (i != fields.size() - 1) res = res + "&";
            }
        return res;
    }

    /**
     * Method that add object to database
     *
     * @param objects objects to add
     */
    public static void addObjectToRealm(List<List<? extends RealmObject>> objects) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (int i = 0; i < objects.size(); i++)
            for (RealmObject object : objects.get(i))
                realm.copyToRealm(object); // Persist unmanaged objects

        realm.commitTransaction();
        realm.close();
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(dateFormat);
        return f.format(date);
    }

    /**
     * Method that add object to database
     *
     * @param object objects to add
     */
    public static void addOneObjectToRealm(RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealm(object); // Persist unmanaged objects

        realm.commitTransaction();
        realm.close();
    }

    /**
     * Method that add or update object to database
     *
     * @param object objects to add
     */
    public static void addOrUpdateOneObjectToRealm(RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(object); // Persist unmanaged objects

        realm.commitTransaction();
        realm.close();
    }

    /**
     * Method that delete all database data
     */
    public static void clearRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // delete all realm objects
        realm.deleteAll();

        //commit realm changes
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Method that form data as needed for requests
     */
    public static String formDateSql(Date date) {
        return new SimpleDateFormat("yyyy-dd-MM").format(date);
    }

    /**
     * Method that form data as needed for requests
     */
    public static String formDateTimeSql(Date date) {
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(date);
    }

    /**
     * Method that get the shared preference
     *
     * @param context actual context
     * @return shared preferences of the app
     */

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(
                "Pref", Context.MODE_PRIVATE);
    }

    /**
     * Method that get the editor shared preference
     *
     * @param context actual context
     * @return shared preferences editor of the app
     */
    public static SharedPreferences.Editor getSharedPreferenceEdito(Context context) {
        return context.getSharedPreferences("Pref", Context.MODE_PRIVATE).edit();
    }

    /**
     * Method that check if there is an active internet connexion
     *
     * @param context actual context
     * @return true if there is an active internet connexion, false else
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Method that hide soft keyboard on click in the activity
     *
     * @param view    actual view
     * @param context actual context
     */
    public static void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static ArrayList<String> getDaysShort(Context context) {
        ArrayList<String> daysShort = new ArrayList<>();
        int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (int j = i % 7; j != i - 1; j = (j + 1) % 7)
            daysShort.add(context.getResources().getStringArray(R.array.days_shors)[j]);
        daysShort.add(context.getResources().getStringArray(R.array.days_shors)[i - 1]);
        return daysShort;
    }

    public static String encryptString(String inputString) throws Exception {

        String strKey = "RGBRGB";

        final MessageDigest Sha = MessageDigest.getInstance("sha1");
        byte[] hash = Sha.digest(strKey.getBytes("UTF-8"));
        hash = Arrays.copyOf(hash, 16);
        final SecretKey Key = new SecretKeySpec(hash, "DESede");
        IvParameterSpec param= new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, Key, param);
        byte[] byteBuff = cipher.doFinal(inputString.getBytes("UTF-8"));
        return new String(Base64.encode(byteBuff, 0));

    }


}
