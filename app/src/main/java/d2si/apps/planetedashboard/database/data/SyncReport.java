package d2si.apps.planetedashboard.database.data;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class that represents the synchronization report
 */
public class SyncReport extends RealmObject {
    @PrimaryKey
    private String id;
    private Date date;
    private boolean success;
    private String tablesUpdated;

    public SyncReport() {
    }

    /**
     * Document constructor
     *
     * @param success true if it was a successful update, false otherwise
     * @param tablesUpdated   a string of the database tables updated
     * @param date       sync date
     */
    public SyncReport(Date date, boolean success, String tablesUpdated) {
        this.id = date.toString();
        this.date = date;
        this.success = success;
        this.tablesUpdated = tablesUpdated;
    }

    // getters and setters

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTablesUpdated() {
        return tablesUpdated;
    }

    public void setTablesUpdated(String tablesUpdated) {
        this.tablesUpdated = tablesUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
