package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import classes.AppData;
import classes.LineChartCustom;
import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class SalesWeekFragment extends Fragment implements OnChartValueSelectedListener {

    private LineChart chart;
    private View view;
    private TextView tv_chart_title;
    private LineChartCustom linechart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chart_line, container, false);

        tv_chart_title = (TextView) view.findViewById(R.id.tv_chart_title);
        chart = (LineChart) view.findViewById(R.id.chart);

        tv_chart_title.setText(R.string.chart_title_sales_week);
        chart.setOnChartValueSelectedListener(this);

        // intialize for test
        List<Entry> entries1 = new ArrayList<Entry>();

        entries1.add(new Entry(1, 40));
        entries1.add(new Entry(2, 44));
        entries1.add(new Entry(3, 48));
        entries1.add(new Entry(4, 29));
        entries1.add(new Entry(5, 35));
        entries1.add(new Entry(6, 60));
        entries1.add(new Entry(7, 20));

        List<Entry> entries2 = new ArrayList<Entry>();

        entries2.add(new Entry(1, 20));
        entries2.add(new Entry(2, 24));
        entries2.add(new Entry(3, 28));
        entries2.add(new Entry(4, 39));
        entries2.add(new Entry(5, 45));
        entries2.add(new Entry(6, 50));
        entries2.add(new Entry(7, 30));

        List<List<Entry>> entries = new ArrayList<>();
        entries.add(entries1);
        entries.add(entries2);

        List<String> labels = new ArrayList<>();
        labels.add("This week");
        labels.add("Last week");

        linechart = new LineChartCustom(chart,tv_chart_title,getString(R.string.chart_title_sales_week),getResources().getStringArray(R.array.days_shors),entries,labels);


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