package d2si.apps.planetedashboard.background;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.SalesController;
import d2si.apps.planetedashboard.database.controller.SalesFragmentDataSetter;
import d2si.apps.planetedashboard.database.data.SyncReport;
import d2si.apps.planetedashboard.webservice.datagetter.DataGetter;

public class UpdateJob extends Job {

    public static final String TAG = "job_update";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // run the update job
        SharedPreferences pref = AppUtils.getSharedPreference(getContext());
        boolean isAutoSync = pref.getBoolean(getContext().getString(R.string.pref_key_sync_auto), true);
        // check if auto sync is actif
        if (isAutoSync)
        // check if internet is available
        if (AppUtils.isNetworkAvailable(getContext())) {

            final DataGetter dataGetter = new DataGetter() {
                @Override
                public void onSalesUpdate(boolean success) {


                    new SalesFragmentDataSetter() {
                        @Override
                        public void onDataSet() {
                        }
                    }.execute();
                }

                @Override
                public void onSalesGet(boolean success) {

                }

                @Override
                public void onUserUpdate(Boolean user) {

                }
            };

            dataGetter.updateSalesByDate(getContext(), SalesController.getLastSyncDate());
        } else
            AppUtils.addOneObjectToRealm(new SyncReport(new Date(Calendar.getInstance().getTimeInMillis()), false, getContext().getString(R.string.sync_report_tables_error_connexion)));
        return Result.SUCCESS;
    }

    public static void scheduleJob(Context context) {

        // get sync period
        SharedPreferences pref = AppUtils.getSharedPreference(context);
        int syncDelay = pref.getInt(context.getString(R.string.pref_key_sync_delay), 60);

        // the minimum is 15 minutes
        if (syncDelay<15) syncDelay=15;

        // start the priodic job
        int jobId = new JobRequest.Builder(UpdateJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(syncDelay))
                .build()
                .schedule();
        // save the job id
        SharedPreferences.Editor editor = AppUtils.getSharedPreferenceEdito(context);
        editor.putInt(context.getString(R.string.pref_key_update_job_id), jobId);
    }

    public static void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }
}
