package classes;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import d2si.apps.planetedashboard.R;

// Customize the LineChart library with some general properties
public class LineChartCustom  {


    private LineChart chart;
    private Context  context;
    public static enum LINE_CHART_TYPE {STANDARD,CUBIC,GRADIENT};


    public LineChartCustom(Context context, LineChart chart, String[] formating, LinkedHashMap<String, List<Float>> entries, LINE_CHART_TYPE type ) {

        this.context = context;
        this.chart = chart;

        customizeChart();
        customizeLegend();
        customizeAxis(formating);

        addData(entries,type);
    }

    // customize the legend properties
    public void customizeLegend(){
        Legend l = chart.getLegend();
        l.setTextSize(AppData.CHART_TEXT_SIZE_2);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setXEntrySpace(20);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextColor(context.getResources().getColor(R.color.colorTextSecondary));
        l.setDrawInside(false);
    }

    // customize the Axis properties
    public void customizeAxis(final String[] formating){
        chart.getAxisRight().setTextSize(AppData.CHART_TEXT_SIZE_2);
        chart.getXAxis().setTextSize(AppData.CHART_TEXT_SIZE_1);
        chart.getXAxis().setLabelRotationAngle(45);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(true);
        chart.getAxisRight().setDrawGridLines(true);
        chart.getAxisRight().setTextColor(context.getResources().getColor(R.color.colorTextSecondary));
        chart.getXAxis().setTextColor(context.getResources().getColor(R.color.colorTextSecondary));
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // add special format with strings with formating

        XAxis xAxis = chart.getXAxis();

        if (formating!=null) {
            IAxisValueFormatter formatter = new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return formating[(int) value];
                }
            };

            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
            xAxis.setValueFormatter(formatter);
        } else xAxis.setDrawLabels(false);
    }

    // customize the chart properties
    public void customizeChart(){
        chart.setDrawGridBackground(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
    }

    // Add data from custom global Map with a label to the data set of the chart
    public void addData(LinkedHashMap<String,List<Float>> entries,LINE_CHART_TYPE type){

        LineData lineData = new LineData();
        Iterator iterator = entries.keySet().iterator();

        int i=0;
        while (iterator.hasNext()){

            // get the label
            String key=(String)iterator.next();

            // fill the dataSet
            LineDataSet dataSet = new LineDataSet(getEntriesFromFloat(entries.get(key)), key);

            //customize the dataSet
            dataSet.setDrawValues(false);
            dataSet.setLineWidth(AppData.CHART_WIDTH_1);
            dataSet.setCircleRadius(AppData.CHART_RADIUS_1);
            dataSet.setFormSize(AppData.CHART_TEXT_SIZE_1);

            dataSet.setColor(AppData.CHART_COLORS.get(i));
            dataSet.setCircleColor(AppData.CHART_COLORS.get(i));
            dataSet.setCircleColorHole(AppData.CHART_COLORS.get(i));
            dataSet.setValueTextColor(AppData.CHART_COLORS.get(i));
            dataSet.setFillColor(AppData.CHART_COLORS.get(i));

            // Customize the data according the type

            switch (type){
                case CUBIC:
                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    dataSet.setCubicIntensity(0.2f);
                    dataSet.setFillColor(AppData.CHART_COLORS.get(i));
                    dataSet.setFillAlpha(100);
                    dataSet.setDrawFilled(true);
            }

            //add the dataset to the Data
            lineData.addDataSet(dataSet);
            i++;
        }

        // add the Data to the chart
        chart.setData(lineData);
        chart.invalidate();
    }

    // Get the Data structured as the library from List<Float> to List<Entries>
    public List<Entry> getEntriesFromFloat(List<Float> data){
        List<Entry> entries = new ArrayList<>();
        int i=0;
        for (Float value:data){
            entries.add(new Entry((float) i,value));
            i++;
        }
        return entries;
    }
}
