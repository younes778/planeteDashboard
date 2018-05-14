package d2si.apps.planetedashboard.database.controller;

import android.os.AsyncTask;

import d2si.apps.planetedashboard.ui.fragments.SalesDayFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesMonthFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesWeekFragment;
import d2si.apps.planetedashboard.ui.fragments.SalesYearFragment;

public abstract class SalesFragmentDataSetter extends AsyncTask<Void, Void, Void> {


    @Override
/**
 * Method that execute the http task
 *
 * @param params parameters to use in background
 */
    protected Void doInBackground(Void... params) {

        SalesDayFragment.objects = SalesController.getDayData();
        SalesWeekFragment.objects = SalesController.getWeekData();
        SalesMonthFragment.objects = SalesController.getMonthData();
        SalesYearFragment.objects = SalesController.getYearData();
        return null;
    }

    @Override
/**
 * Method that execute the http task
 *
 */
    protected void onPostExecute(Void v) {
        onDataSet();
    }

    public abstract void onDataSet();
}
