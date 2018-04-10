package fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.norbsoft.typefacehelper.TypefaceHelper;

import classes.AppData;
import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class SalesDayFragment extends Fragment {


    View view;
    TextView tv_today,tv_yesterday,tv_today_date,tv_yesterday_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sales_day, container, false);

        tv_today = (TextView) view.findViewById(R.id.tv_today);
        tv_yesterday = (TextView) view.findViewById(R.id.tv_yesterday);
        tv_today_date = (TextView) view.findViewById(R.id.tv_today_date);
        tv_yesterday_date = (TextView) view.findViewById(R.id.tv_yesterday_date);

        // initialize the font
        typeface(view);
        tv_today.setTypeface(AppData.fontAppBold);
        tv_yesterday.setTypeface(AppData.fontAppBold);

        tv_today_date.setText(AppData.getDayDateFormatted());
        tv_yesterday_date.setText(AppData.getYesterdayDateFormatted());



        return view;
    }
}