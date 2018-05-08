package d2si.apps.planetedashboard.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.AnyChartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.ui.data.CustomLineChart;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Sales Fragment by week
 * <p>
 * Fragment that represents the sales by week
 *
 * @author younessennadj
 */
public class SalesWeekFragment extends Fragment {

    @BindView(R.id.chart)
    AnyChartView chart;
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
        ButterKnife.bind(this, view);

        // List data titles
        ArrayList<String> setTitles = new ArrayList<>();
        // List data
        ArrayList<ArrayList<Float>> data = new ArrayList<>();


        // fill data titles
        setTitles.add(AppUtils.getWeekDateFormatted(getActivity()));
        setTitles.add(AppUtils.getLastWeekDateFormatted(getActivity()));

        // fill data and labels
        for (int i = 7; i < 14; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
            final Date dateFrom = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.MONTH, 0);
            final Date dateTo = new Date(calendar.getTimeInMillis());

            data.add(new ArrayList<Float>() {{
                add(SalesController.getSalesTotalByDay(dateFrom));
                add(SalesController.getSalesTotalByDay(dateTo));
            }});
        }

        new CustomLineChart(chart, Arrays.asList(getResources().getStringArray(R.array.days_shors)), data, setTitles);
        // initialize the font
        typeface(view);
        typeface(chart);

        return view;
    }
}