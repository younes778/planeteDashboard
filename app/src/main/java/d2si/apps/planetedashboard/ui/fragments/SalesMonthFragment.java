package d2si.apps.planetedashboard.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.database.data.Sale;
import d2si.apps.planetedashboard.ui.data.LineChartCustom;
import d2si.apps.planetedashboard.R;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Sales Fragment by month
 *
 * Fragment that represents the sales by month
 *
 * @author younessennadj
 */
public class SalesMonthFragment extends Fragment implements OnChartValueSelectedListener {

    @BindView(R.id.chart) LineChart chart;
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
        view = inflater.inflate(R.layout.fragment_chart_line, container, false);
        ButterKnife.bind(this,view);

        // Chart Value selected listener
        chart.setOnChartValueSelectedListener(this);


        // intialize for test
        LinkedHashMap<String,List<Float>> entries = new LinkedHashMap<>();
        List<Float> thisMonthEntries = new ArrayList<>();
        for (int i=0;i<31;i++)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH,1);
            calendar.set(Calendar.DAY_OF_MONTH,i+1);
            Date date = new Date(calendar.getTimeInMillis());
            thisMonthEntries.add(SalesController.getSalesTotalByDay(date));
        }

        List<Float> lastMonthEntries = new ArrayList<>();
        for (int i=0;i<31;i++)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH,0);
            calendar.set(Calendar.DAY_OF_MONTH,i+1);
            Date date = new Date(calendar.getTimeInMillis());
            lastMonthEntries.add(SalesController.getSalesTotalByDay(date));
        }



        entries.put(AppData.getMonthFormatted(),thisMonthEntries);
        entries.put(AppData.getLastMonthFormatted(),lastMonthEntries);
        new LineChartCustom(getActivity(),chart,AppData.CHART_MONTHS_FORMAT,entries,LineChartCustom.LINE_CHART_TYPE.CUBIC);

        // initialize the font
        typeface(view);
        typeface(chart);

        return view;
    }

    @Override
    /**
     * Method that react when valuer selected
     *
     * @param e Value entry
     * @param h Value highlighted
     */
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    /**
     * Method that react when nothing is selected
     *
     */
    public void onNothingSelected() {

    }
}