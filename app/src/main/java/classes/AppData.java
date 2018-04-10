package classes;

import android.content.Context;
import android.graphics.Typeface;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import d2si.apps.planetedashboard.R;

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

    public static String getCurrencyFormatted(Context context,String value){
        return context.getString(R.string.currency)+value;
    }


}
