package d2si.apps.planetedashboard.ui.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CoreAxesLinear;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.LabelsOverlapMode;
import com.anychart.anychart.Mapping;
import com.anychart.anychart.Orientation;
import com.anychart.anychart.ScaleStackMode;
import com.anychart.anychart.SeriesBar;
import com.anychart.anychart.Set;
import com.anychart.anychart.TooltipDisplayMode;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.ui.data.CustomBarChart;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Sales Fragment by month
 *
 * Fragment that represents the sales by month
 *
 * @author younessennadj
 */
public class SalesYearFragment extends Fragment {

    @BindView(R.id.chart) AnyChartView chart;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this,view);


        // List labels
        ArrayList<String> months = new ArrayList<>();
        // List data titles
        ArrayList<String> setTitles = new ArrayList<>();
        // List data
        ArrayList<ArrayList<Float>> data = new ArrayList<>();


        // fill data titles
        setTitles.add(Calendar.getInstance().get(Calendar.YEAR)+"");
        setTitles.add(Calendar.getInstance().get(Calendar.YEAR)-1+"");

        // fill data and labels
        for (int i=0;i<12;i++)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH,1);
            calendar.set(Calendar.DAY_OF_MONTH,i+1);
            final Date dateFrom = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.MONTH,0);
            final Date dateTo = new Date(calendar.getTimeInMillis());

            months.add(new DateFormatSymbols().getMonths()[i]);
            data.add(new ArrayList<Float>(){{add(SalesController.getSalesTotalByDay(dateFrom));add(SalesController.getSalesTotalByDay(dateTo)*-1);}});
        }

        new CustomBarChart(chart,months,data,setTitles);

        // initialize the font
        typeface(view);
        typeface(chart);

        return view;
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }
}