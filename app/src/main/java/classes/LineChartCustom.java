package classes;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;


public class LineChartCustom  {

    TextView tv_title;
    LineChart chart;

    public LineChartCustom(LineChart chart,TextView tv_title,String title,String[] formating,List<List<Entry>> entries, List<String> labels) {

        this.chart = chart;
        this.tv_title = tv_title;
        this.tv_title.setText(title);

        customizeChart();
        customizeLegend();
        customizeAxis(formating);

        addData(entries, labels);
    }

    public void customizeLegend(){
        Legend l = chart.getLegend();
        l.setTextSize(AppData.CHART_TEXT_SIZE_2);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
    }

    public void customizeAxis(final String[] formating){
        chart.getAxisRight().setTextSize(AppData.CHART_TEXT_SIZE_2);
        chart.getXAxis().setTextSize(AppData.CHART_TEXT_SIZE_1);
        chart.getXAxis().setLabelRotationAngle(45);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(true);
        chart.getAxisRight().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return formating[(int) value-1];
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
    }

    public void customizeChart(){
        chart.setDrawGridBackground(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
    }

    public void addData(List<List<Entry>> entries, List<String> labels){

        LineData lineData = new LineData();
        for (int i=0;i<entries.size();i++){

            LineDataSet dataSet = new LineDataSet(entries.get(i), labels.get(i));

            dataSet.setDrawValues(false);
            dataSet.setLineWidth(AppData.CHART_WIDTH_1);
            dataSet.setCircleRadius(AppData.CHART_RADIUS_1);
            dataSet.setFormSize(AppData.CHART_TEXT_SIZE_1);

            dataSet.setColor(AppData.CHART_COLORS.get(i));
            dataSet.setCircleColor(AppData.CHART_COLORS.get(i));
            dataSet.setCircleColorHole(AppData.CHART_COLORS.get(i));
            dataSet.setValueTextColor(AppData.CHART_COLORS.get(i));

            lineData.addDataSet(dataSet);

        }

        chart.setData(lineData);
        chart.invalidate();

    }
}
