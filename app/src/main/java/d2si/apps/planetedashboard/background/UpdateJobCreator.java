package d2si.apps.planetedashboard.background;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class UpdateJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case UpdateJob.TAG:
                return new UpdateJob();
            default:
                return null;
        }
    }
}