package fragments;

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

import classes.AppData;
import classes.LineChartCustom;
import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class SalesWeekFragment extends Fragment implements OnChartValueSelectedListener {

    private LineChart chart;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chart_line, container, false);

        chart = (LineChart) view.findViewById(R.id.chart);
        chart.setOnChartValueSelectedListener(this);

        // intialize for test
        LinkedHashMap<String,List<Float>> entries = new LinkedHashMap<>();
        List<Float> thisWeekEntries = new ArrayList<Float>(){{add(40f);add(44f);add(48f);add(29f);add(35f);add(60f);add(20f);}};
        List<Float> lastWeekEntries = new ArrayList<Float>(){{add(20f);add(24f);add(28f);add(39f);add(45f);add(50f);add(30f);}};


        entries.put(AppData.getWeekDateFormatted(getActivity()),thisWeekEntries);
        entries.put(AppData.getLastWeekDateFormatted(getActivity()),lastWeekEntries);


        new LineChartCustom(getActivity(),chart,getResources().getStringArray(R.array.days_shors),entries, LineChartCustom.LINE_CHART_TYPE.STANDARD);

        // initialize the font
        typeface(view);
        typeface(chart);

        return view;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}