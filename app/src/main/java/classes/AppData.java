package classes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateUtils;

import com.norbsoft.typefacehelper.ActionBarHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

// Class regrouping the app static data
public class AppData {
    public static int CHART_RADIUS_1 = 4;
    public static int CHART_WIDTH_1= 2;
    public static int CHART_TEXT_SIZE_1= 15;
    public static int CHART_TEXT_SIZE_2= 18;
    public static List<Integer> CHART_COLORS= new ArrayList<>();
    public static Typeface fontApp;
    public static Typeface fontAppBold;

    public static void init(Context context){
        fontApp = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont.ttf");
        fontAppBold = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont_Bold.ttf");
        CHART_COLORS.add(context.getResources().getColor(R.color.colorPrimary));
        CHART_COLORS.add(context.getResources().getColor(R.color.colorAccent));
        CHART_COLORS.add(context.getResources().getColor(R.color.colorPrimaryDark));
    }

    public static String getDayDateFormatted(){
        Date date = new Date();
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    public static String getYesterdayDateFormatted(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        Date date = new Date(calendar.getTimeInMillis());
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    public static String getWeekDateFormatted(Context context){
        Calendar calLastWeek = Calendar.getInstance();
        calLastWeek.set(Calendar.DAY_OF_YEAR,calLastWeek.get(Calendar.DAY_OF_YEAR)-7);
        return  DateUtils.formatDateRange(context, calLastWeek.getTimeInMillis(),Calendar.getInstance().getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
    }

    public static String getLastWeekDateFormatted(Context context){
        Calendar calLastWeek = Calendar.getInstance();
        calLastWeek.set(Calendar.DAY_OF_YEAR,calLastWeek.get(Calendar.DAY_OF_YEAR)-8);
        Calendar calLast2Weeks = Calendar.getInstance();
        calLast2Weeks.set(Calendar.DAY_OF_YEAR,calLastWeek.get(Calendar.DAY_OF_YEAR)-7);
        return  DateUtils.formatDateRange(context, calLast2Weeks.getTimeInMillis(),calLastWeek.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
    }

    public static String getMonthFormatted(Context context){
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("MMMM").format(cal.getTime());
    }

    public static String getLastMonthFormatted(Context context){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
        return new SimpleDateFormat("MMMM").format(cal.getTime());
    }

    public static String getCurrencyFormatted(Context context,String value){
        return context.getString(R.string.currency)+value;
    }

    //set ActionBar title with specific font
    public static void setActionBarTitle(AppCompatActivity activity, int ressource){
        ActionBarHelper.setTitle(
                activity.getSupportActionBar(),
                typeface(activity,ressource));
    }


}
