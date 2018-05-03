package d2si.apps.planetedashboard.ui.data;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CoreAxesLinear;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.LabelsOverlapMode;
import com.anychart.anychart.Mapping;
import com.anychart.anychart.Orientation;
import com.anychart.anychart.ScaleStackMode;
import com.anychart.anychart.SeriesBar;
import com.anychart.anychart.Set;
import com.anychart.anychart.TooltipDisplayMode;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

import d2si.apps.planetedashboard.AppUtils;

public class CustomBarChart {

    private AnyChartView chart;
    private Cartesian barChart;

    public CustomBarChart(AnyChartView chart, List<String> legend, ArrayList<ArrayList<Float>> entries, List<String> dataTitles) {
        this.chart = chart;
        customizeChart();
        customizeLegend();
        addData(legend, entries, dataTitles);

        chart.setChart(barChart);

    }

    public void customizeChart() {
        barChart = AnyChart.bar();

        barChart.setAnimation(true);

        barChart.setPadding(10d, 20d, 5d, 20d);

        barChart.getYScale().setStackMode(ScaleStackMode.VALUE);

        barChart.getInteractivity().setHoverMode(HoverMode.BY_X);

        barChart.getTooltip()
                .setTitle(false)
                .setSeparator(false)
                .setDisplayMode(TooltipDisplayMode.SEPARATED)
                .setPositionMode(TooltipPositionMode.POINT)
                .setUseHtml(true)
                .setFontSize(12d)
                .setOffsetX(5d)
                .setOffsetY(0d)
                .setFormat(
                        "function() {\n" +
                                "      return '<span style=\"color: #FFFFFF\">DZ</span>' + Math.abs(this.value).toLocaleString();\n" +
                                "    }");

    }

    public void customizeLegend() {
        barChart.getYAxis().getLabels().setFormat("function(){return anychart.format.number(Math.abs(this.value),{scale:true,zeroFillDecimals: true})}");
        //barChart.getYAxis().getLabels().setFormat("{%Value}{scale:(1000000)(1)|(M)}");

        barChart.getXAxis(0d).setOverlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

        CoreAxesLinear xAxis1 = barChart.getXAxis(1d);
        xAxis1.setEnabled(true);
        xAxis1.setOrientation(Orientation.RIGHT);
        xAxis1.setOverlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

        barChart.getLegend().setEnabled(true);
        barChart.getLegend().setInverted(true);
        barChart.getLegend().setFontSize(13d);
        barChart.getLegend().setPadding(0d, 0d, 20d, 0d);
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

            SeriesBar series = barChart.bar(mappingData);
            series.setName(dataTitles.get(i) + "")
                    .setColor(AppUtils.CHART_COLORS.get(i));
            if (i == 0)
                series.getTooltip()
                        .setPosition("right")
                        .setAnchor(EnumsAnchor.LEFT_CENTER);
            else
                series.getTooltip()
                        .setPosition("left")
                        .setAnchor(EnumsAnchor.RIGHT_CENTER);
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
