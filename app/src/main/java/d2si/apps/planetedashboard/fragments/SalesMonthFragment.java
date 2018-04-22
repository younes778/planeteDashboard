package d2si.apps.planetedashboard.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.classes.AppData;
import d2si.apps.planetedashboard.classes.LineChartCustom;
import d2si.apps.planetedashboard.R;

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
        List<Float> thisMonthEntries = new ArrayList<Float>(){{add(40f);add(44f);add(48f);add(29f);add(35f);add(60f);add(20f);add(40f);add(44f);add(48f);add(29f);add(35f);add(60f);add(20f);add(40f);add(44f);add(48f);add(29f);add(35f);add(60f);add(20f);add(34f);add(20f);add(40f);add(44f);add(48f);add(29f);add(35f);add(60f);add(20f);add(34f);}};
        List<Float> lastMonthEntries = new ArrayList<Float>(){{add(20f);add(24f);add(28f);add(39f);add(45f);add(50f);add(30f);add(20f);add(24f);add(28f);add(39f);add(45f);add(50f);add(30f);add(20f);add(24f);add(28f);add(39f);add(45f);add(50f);add(30f);add(50f);add(30f);add(20f);add(24f);add(28f);add(39f);add(45f);add(50f);add(30f);}};
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