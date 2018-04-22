package d2si.apps.planetedashboard.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Sales Fragment by day
 *
 * Fragment that represents the sales by day
 *
 * @author younessennadj
 */
public class SalesDayFragment extends Fragment {


    View view;
    @BindView(R.id.tv_today) TextView tv_today;
    @BindView(R.id.tv_yesterday) TextView tv_yesterday;
    @BindView(R.id.tv_today_date) TextView tv_today_date;
    @BindView(R.id.tv_yesterday_date) TextView tv_yesterday_date;


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
        view = inflater.inflate(R.layout.fragment_sales_day, container, false);

        // initialize the font
        typeface(view);
        ButterKnife.bind(this,view);

        // set the bold font for some texts
        tv_today.setTypeface(AppData.fontAppBold);
        tv_yesterday.setTypeface(AppData.fontAppBold);

        // set the texts
        tv_today_date.setText(AppData.getDayDateFormatted());
        tv_yesterday_date.setText(AppData.getYesterdayDateFormatted());

        return view;
    }
}