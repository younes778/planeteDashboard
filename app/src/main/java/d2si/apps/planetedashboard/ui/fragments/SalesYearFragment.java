package d2si.apps.planetedashboard.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.ValueDataEntry;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.ui.data.CustomBarChart;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Sales Fragment by month
 * <p>
 * Fragment that represents the sales by month
 *
 * @author younessennadj
 */
public class SalesYearFragment extends Fragment {

    @BindView(R.id.chart)
    AnyChartView chart;
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
        view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, view);


        // List labels
        ArrayList<String> months = new ArrayList<>();
        // List data titles
        ArrayList<String> setTitles = new ArrayList<>();
        // List data
        ArrayList<ArrayList<Float>> data = new ArrayList<>();


        // fill data titles
        setTitles.add(Calendar.getInstance().get(Calendar.YEAR) + "");
        setTitles.add(Calendar.getInstance().get(Calendar.YEAR) - 1 + "");

        // fill data and labels
        for (int i = 0; i < 12; i++) {
            final Calendar calendar = Calendar.getInstance();
            final int j=i;

            months.add(new DateFormatSymbols().getMonths()[i]);
            data.add(new ArrayList<Float>() {{
                add(SalesController.getSalesByMonth(calendar.get(Calendar.YEAR),j));
                add(SalesController.getSalesByMonth(calendar.get(Calendar.YEAR)-1,j));
            }});
        }

        new CustomBarChart(chart, months, data, setTitles);

        // initialize the font
        typeface(view);
        typeface(chart);

        return view;
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }
}