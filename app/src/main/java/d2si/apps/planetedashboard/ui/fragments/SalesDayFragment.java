package d2si.apps.planetedashboard.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Sales Fragment by day
 * <p>
 * Fragment that represents the sales by day
 *
 * @author younessennadj
 */
public class SalesDayFragment extends Fragment {


    View view;
    @BindView(R.id.tv_today)
    TextView tv_today;
    @BindView(R.id.tv_yesterday)
    TextView tv_yesterday;
    @BindView(R.id.tv_today_date)
    TextView tv_today_date;
    @BindView(R.id.tv_yesterday_date)
    TextView tv_yesterday_date;
    @BindView(R.id.tv_today_sales)
    TextView tv_today_sales;
    @BindView(R.id.tv_today_quantity)
    TextView tv_today_quantity;
    @BindView(R.id.tv_today_articles_number)
    TextView tv_today_articles_number;
    @BindView(R.id.tv_today_average_price)
    TextView tv_today_average;
    @BindView(R.id.tv_yesterday_sales)
    TextView tv_yesterday_sales;
    @BindView(R.id.tv_yesterday_quantity)
    TextView tv_yesterday_quantity;
    @BindView(R.id.tv_yesterday_articles_number)
    TextView tv_yesterday_articles_number;
    @BindView(R.id.tv_yesterday_average_price)
    TextView tv_yesterday_average;

    public static List<Object> objects;


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
        ButterKnife.bind(this, view);

        // set the bold font for some texts
        tv_today.setTypeface(AppUtils.fontAppBold);
        tv_yesterday.setTypeface(AppUtils.fontAppBold);
        tv_today_sales.setTypeface(AppUtils.fontAppBold);
        tv_yesterday_sales.setTypeface(AppUtils.fontAppBold);

        // set the texts
        tv_today_date.setText(AppUtils.getDayDateFormatted());
        tv_yesterday_date.setText(AppUtils.getYesterdayDateFormatted());
        tv_today_sales.setText(AppUtils.getCurrencyFormatted(getActivity(), (float) objects.get(0), true));
        tv_today_average.setText(AppUtils.getCurrencyFormatted(getActivity(), (float) objects.get(1), false));
        if ((int) objects.get(2) == 0 && (int) objects.get(3) == 0)
            tv_today_quantity.setText("0");
        else
        tv_today_quantity.setText((int) objects.get(2) + getString(R.string.tv_positive)+" "+(int) objects.get(3)+getString(R.string.tv_negative));
        if ((int) objects.get(4) == 0 && (int) objects.get(5) == 0)
            tv_today_articles_number.setText("0");
        else
        tv_today_articles_number.setText((int) objects.get(4) + getString(R.string.tv_positive)+" "+(int) objects.get(5)+getString(R.string.tv_negative));

        tv_yesterday_sales.setText(AppUtils.getCurrencyFormatted(getActivity(), (float) objects.get(6), true));
        tv_yesterday_average.setText(AppUtils.getCurrencyFormatted(getActivity(), (float) objects.get(7), false));
        if ((int) objects.get(8) == 0 && (int) objects.get(9) == 0)
            tv_yesterday_quantity.setText("0");
        else
        tv_yesterday_quantity.setText((int) objects.get(8) + getString(R.string.tv_positive)+" "+(int) objects.get(9)+getString(R.string.tv_negative));
        if ((int) objects.get(10) == 0 && (int) objects.get(11) == 0)
            tv_yesterday_articles_number.setText("0");
        else
        tv_yesterday_articles_number.setText((int) objects.get(10) + getString(R.string.tv_positive)+" "+(int) objects.get(11)+getString(R.string.tv_negative));

        return view;
    }
}