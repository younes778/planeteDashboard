package d2si.apps.planetedashboard.background;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * UpdateJobCreator
 * <p>
 * the job creator class that create the job who update the database
 *
 * @author younessennadj
 */


public class UpdateJobCreator implements JobCreator {

    @Override
    @Nullable
    /**
     * Method that creates a job according to the tag
     *
     * @param tag job tag
     * @return the job created
     */
    public Job create(@NonNull String tag) {
        switch (tag) {
            case UpdateJob.TAG:
                return new UpdateJob();
            default:
                return null;
        }
    }
}