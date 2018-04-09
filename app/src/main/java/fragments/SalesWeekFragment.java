package fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import classes.AppData;
import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class SalesWeekFragment extends Fragment implements OnChartValueSelectedListener {

    private LineChart chart;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sales_week, container, false);

        chart = (LineChart) view.findViewById(R.id.chart);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawGridBackground(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(true);
        chart.getAxisRight().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);

        addData();

        typeface(chart);



        // initialize the font
        typeface(view);

        return view;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }


    private void addData(){
        List<Entry> entries = new ArrayList<Entry>();
            // turn your data into Entry objects
        entries.add(new Entry(1, 40));
        entries.add(new Entry(2, 44));
        entries.add(new Entry(3, 48));
        entries.add(new Entry(4, 29));
        entries.add(new Entry(5, 35));
        entries.add(new Entry(6, 60));
        entries.add(new Entry(7, 20));


        LineDataSet dataSet1 = new LineDataSet(entries, getString(R.string.week_actual)); // add entries to dataset
        dataSet1.setColor(getResources().getColor(R.color.colorPrimary));
        dataSet1.setValueTextColor(getResources().getColor(R.color.colorPrimary));

        entries = new ArrayList<Entry>();
        // turn your data into Entry objects
        entries.add(new Entry(1, 20));
        entries.add(new Entry(2, 24));
        entries.add(new Entry(3, 28));
        entries.add(new Entry(4, 39));
        entries.add(new Entry(5, 45));
        entries.add(new Entry(6, 50));
        entries.add(new Entry(7, 30));

        LineDataSet dataSet2 = new LineDataSet(entries, getString(R.string.week_previous)); // add entries to dataset
        dataSet2.setColor(getResources().getColor(R.color.colorAccent));
        dataSet2.setValueTextColor(getResources().getColor(R.color.colorAccent));

        LineData lineData = new LineData(dataSet1);
        lineData.addDataSet(dataSet2);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }
}