package d2si.apps.planetedashboard.ui.data;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CartesianSeriesColumn;
import com.anychart.anychart.CartesianSeriesLine;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.Mapping;
import com.anychart.anychart.MarkerType;
import com.anychart.anychart.Orientation;
import com.anychart.anychart.Position;
import com.anychart.anychart.Set;
import com.anychart.anychart.Stroke;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

import d2si.apps.planetedashboard.AppUtils;

/**
 * class that represent a custom line chart
 *
 * @author younessennadj
 */
public class CustomColumnChart {

    private AnyChartView chart;
    private Cartesian columnChart;

    /**
     * constructor
     *
     * @param chart      the chart object
     * @param legend     legen names
     * @param entries    data to show
     * @param dataTitles series titles
     */
    public CustomColumnChart(AnyChartView chart, List<String> legend, ArrayList<ArrayList<Float>> entries, List<String> dataTitles) {
        this.chart = chart;
        customizeChart();
        customizeLegend();
        addData(legend, entries, dataTitles);

        chart.setChart(columnChart);

    }

    public void customizeChart() {
        columnChart = AnyChart.column();

        columnChart.setAnimation(true);


        columnChart.setPadding(10d, 20d, 20d, 20d);


        columnChart.getTooltip()
                .setTitleFormat("{%X}")
                .setPosition(Position.CENTER_BOTTOM)
                .setAnchor(EnumsAnchor.CENTER_BOTTOM)
                .setOffsetX(0d)
                .setOffsetY(5d)
                .setFormat("{%Value}{groupsSeparator: }");

        columnChart.getTooltip().setPositionMode(TooltipPositionMode.POINT);
        columnChart.getInteractivity().setHoverMode(HoverMode.BY_X);

    }

    public void customizeLegend() {

        columnChart.getYScale().setMinimum(0d);

        columnChart.getYAxis().getLabels().setFormat("{%Value}{groupsSeparator: }");

        columnChart.getLegend().setEnabled(true);
        columnChart.getLegend().setInverted(true);
        columnChart.getLegend().setFontSize(13d);
        columnChart.getLegend().setPadding(0d, 0d, 20d, 0d);

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
            else mappingData = set.mapAs("{ x: 'x', value: 'value" + (i + 1) + "' }");


            CartesianSeriesColumn series = columnChart.column(mappingData);
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

            series.setStroke(AppUtils.CHART_COLORS.get(i), 3, null, null, null);


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
