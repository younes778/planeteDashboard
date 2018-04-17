package classes;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateUtils;

import com.norbsoft.typefacehelper.ActionBarHelper;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * App data class
 *
 * Class regrouping the app static data and methods
 *
 * @author younessennadj
 */
public class AppData {
    public static boolean VERSTION_TEST = true;
    public static String ACTIVITY_DATA="Data";
    public static String[] CHART_MONTHS_FORMAT= new String[31];
    public static int CHART_RADIUS_1 = 4;
    public static int CHART_WIDTH_1= 2;
    public static int CHART_TEXT_SIZE_1= 15;
    public static int CHART_TEXT_SIZE_2= 18;
    public static List<Integer> CHART_COLORS= new ArrayList<>();
    public static Typeface fontApp;
    public static Typeface fontAppBold;

    /**
     * Method that initialize the data resources
     *
     * @param context App actual context
     */
    public static void init(Context context){
        addChartColors(context);
        initializeFonts(context);
        addChartMonthFormat();
    }

    /**
     * Method that initialize app fonts
     *
     * @param context App actual context
     */
    public static void initializeFonts(Context context){

        // init fonts from assests
        fontApp = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont.ttf");
        fontAppBold = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont_Bold.ttf");

        // initialize the default font
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.BOLD, AppData.fontApp)
                .create();
        TypefaceHelper.init(typeface);

    }

    /**
     * Method that add chart colors
     *
     * @param context App actual context
     */
    public static void addChartColors(Context context){
        CHART_COLORS.add(context.getResources().getColor(R.color.colorPrimary));
        CHART_COLORS.add(context.getResources().getColor(R.color.colorAccent));
        CHART_COLORS.add(context.getResources().getColor(R.color.colorPrimaryDark));
    }

    /**
     * Method that add specific format to the Chart Month {1,15,30}
     *
     */
    public static void addChartMonthFormat(){
        for (int i=0;i<CHART_MONTHS_FORMAT.length;i++)
            CHART_MONTHS_FORMAT[i]="";

        CHART_MONTHS_FORMAT[0]="01";
        CHART_MONTHS_FORMAT[14]="15";
        CHART_MONTHS_FORMAT[30]="31";
    }

    /**
     * Method that set ActionBar title with specific font
     *
     * @param activity actual activity
     * @param resource actionbar title resource
     */
    public static void setActionBarTitle(Activity activity, int resource){
        ActionBarHelper.setTitle(
                ((AppCompatActivity) activity).getSupportActionBar(),
                typeface(activity,resource));
    }

    /**
     * Method that return today date formatted 'DayName, month day,year'
     *
     * @return  today date formatted
     */
    public static String getDayDateFormatted(){
        Date date = new Date();
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    /**
     * Method that return yesterday date formatted 'DayName, month day,year'
     *
     * @return  yesterday date formatted
     */
    public static String getYesterdayDateFormatted(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        Date date = new Date(calendar.getTimeInMillis());
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    /**
     * Method that return this week date formatted 'month day1-day7'
     *
     * @param context App actual context
     *
     * @return  this week date formatted
     */
    public static String getWeekDateFormatted(Context context){
        Calendar calLastWeek = Calendar.getInstance();
        calLastWeek.set(Calendar.DAY_OF_YEAR,calLastWeek.get(Calendar.DAY_OF_YEAR)-7);
        return  DateUtils.formatDateRange(context, calLastWeek.getTimeInMillis(),Calendar.getInstance().getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * Method that return last week date formatted 'month day1-day7'
     *
     * @param context App actual context
     *
     * @return  last week date formatted
     */
    public static String getLastWeekDateFormatted(Context context){
        Calendar calLastWeek = Calendar.getInstance();
        calLastWeek.set(Calendar.DAY_OF_YEAR,calLastWeek.get(Calendar.DAY_OF_YEAR)-8);
        Calendar calLast2Weeks = Calendar.getInstance();
        calLast2Weeks.set(Calendar.DAY_OF_YEAR,calLastWeek.get(Calendar.DAY_OF_YEAR)-7);
        return  DateUtils.formatDateRange(context, calLast2Weeks.getTimeInMillis(),calLastWeek.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * Method that return this month date formatted 'month'
     *
     * @return  this month date formatted
     */
    public static String getMonthFormatted(){
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("MMMM").format(cal.getTime());
    }

    /**
     * Method that return last month date formatted 'month day1-day7'
     *
     * @return  last month date formatted
     */
    public static String getLastMonthFormatted(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
        return new SimpleDateFormat("MMMM").format(cal.getTime());
    }

    /**
     * Method that return money value formatted with the currency symbol
     *
     * @param context App actual context
     * @param value money value
     *
     * @return  last month date formatted
     */
    public static String getCurrencyFormatted(Context context,String value){
        return context.getString(R.string.currency)+value;
    }

    /**
     * Method that launch activity launched from activity caller
     *
     * @param caller activity caller
     * @param launched activity class that will be launched
     * @param finishing indicate whether finish the activity caller or not
     * @param extra data to be transferred to the activity to be launched
     *
     */
    public static void launchActivity(AppCompatActivity caller,Class launched,boolean finishing,String extra){
        Intent activityLauncher = new Intent(caller,launched);
        activityLauncher.putExtra(ACTIVITY_DATA,extra);
        caller.startActivity(activityLauncher);
        if (finishing) caller.finish();
    }

    /**
     * Method that will get the data put by the activity caller
     *
     * @param launched activity that will be launched
     *
     * @return the Data passed from activity to the launched activity
     */
    public static String getDataFromLaunchedActivity(AppCompatActivity launched){
        Intent activityIntent= launched.getIntent();
        Bundle b = activityIntent.getExtras();
        String result=null;

        if(b!=null)
        {
            result =(String) b.get(ACTIVITY_DATA);
        }

        return result;
    }

    /**
     * Method that load fragment
     *
     * @param activity the activity to load on
     * @param fragment The fragment to load
     */
    public static void loadFragment(Activity activity, Fragment fragment){

        // create a FragmentManager
        FragmentManager fm = activity.getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment_manager, fragment);
        // save the changes
        fragmentTransaction.commit();

    }

}
