package d2si.apps.planetedashboard.ui.data;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CartesianSeriesLine;
import com.anychart.anychart.CoreAxesLinear;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.LabelsOverlapMode;
import com.anychart.anychart.Mapping;
import com.anychart.anychart.MarkerType;
import com.anychart.anychart.Orientation;
import com.anychart.anychart.ScaleStackMode;
import com.anychart.anychart.SeriesBar;
import com.anychart.anychart.Set;
import com.anychart.anychart.Stroke;
import com.anychart.anychart.TooltipDisplayMode;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

import d2si.apps.planetedashboard.AppUtils;

public class CustomLineChart {

    private AnyChartView chart;
    private Cartesian barChart;

    public CustomLineChart(AnyChartView chart, List<String> legend, ArrayList<ArrayList<Float>> entries, List<String> dataTitles) {
        this.chart = chart;
        customizeChart();
        customizeLegend();
        addData(legend, entries, dataTitles);

        chart.setChart(barChart);

    }

    public void customizeChart() {
        barChart = AnyChart.line();

        barChart.setAnimation(true);


        barChart.setPadding(10d, 20d, 20d, 20d);


        barChart.getCrosshair().setEnabled(true);
        barChart.getCrosshair()
                .setYLabel(true)
                .setYStroke((Stroke) null, null, null , null, null);

        barChart.getTooltip().setPositionMode(TooltipPositionMode.POINT);


    }

    public void customizeLegend() {

        barChart.getYAxis().getLabels().setFormat("{%Value}{scale:(1)(1000)(1000)(1000)|( )(K)(M)(B)}");
        barChart.getYAxis().setOrientation(Orientation.RIGHT);
        barChart.getXAxis().getLabels().setPadding(5d, 5d, 5d, 5d);

        barChart.getLegend().setEnabled(true);
        barChart.getLegend().setFontSize(13d);
        barChart.getLegend().setPadding(0d, 0d, 10d, 0d);

    }

    public void addData(List<String> legend, ArrayList<ArrayList<Float>> entries, List<String> dataTitles) {
        List<DataEntry> seriesData = new ArrayList<>();
        // fill data

        for (int i = 0; i < entries.size(); i++) {
            seriesData.add(new CustomDataEntry(legend.get(i), entries.get(i)));
        }

        Set set = new Set(seriesData);
        for (int i = 0; i < dataTitles.size(); i++) {
            Mapping mappingData;
            if (i == 0)
                mappingData = set.mapAs("{ x: 'x', value: 'value' }");
            else mappingData = set.mapAs("{ x: 'x', value: 'value" + (i+1) + "' }");

            CartesianSeriesLine series = barChart.line(mappingData);
            series.setName(dataTitles.get(i) + "")
                    .setColor(AppUtils.CHART_COLORS.get(i));
            series.getHovered().getMarkers().setEnabled(true);
            series.getHovered().getMarkers()
                    .setType(MarkerType.CIRCLE)
                    .setSize(4d);
            series.getTooltip()
                    .setPosition("left")
                    .setAnchor(EnumsAnchor.RIGHT_CENTER)
                    .setOffsetX(5d)
                    .setOffsetY(5d);

            series.setStroke(AppUtils.CHART_COLORS.get(i),3,null,null,null);


        }
    }

    class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, List<Float> values) {
            super(x, values.get(0));
            for (int i = 1; i < values.size(); i++)
                setValue("value" + (i + 1), values.get(i));
        }
    }


}
