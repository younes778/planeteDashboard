package d2si.apps.planetedashboard.database.data;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class that represents the ligne
 */
public class QuickAccessData extends RealmObject {

    @PrimaryKey
    private String id;
    private RealmList<Float> day_data = new RealmList<>();
    private RealmList<Float> week_data = new RealmList<>();
    private RealmList<Float> month_data = new RealmList<>();
    private RealmList<Float> year_data = new RealmList<>();


    public QuickAccessData() {
    }

    public QuickAccessData(RealmList<Float> day_data, RealmList<Float> week_data, RealmList<Float> month_data, RealmList<Float> year_data) {
        this.day_data = day_data;
        this.week_data = week_data;
        this.month_data = month_data;
        this.year_data = year_data;
    }

    public QuickAccessData(List<Float> day_data, List<Float> week_data, List<Float> month_data, List<Float> year_data) {
        this.id = "1";
        this.day_data.addAll(day_data);
        this.week_data.addAll(week_data);
        this.month_data.addAll(month_data);
        this.year_data.addAll(year_data);
    }


    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Float> getDay_data() {
        return day_data;
    }

    public void setDay_data(RealmList<Float> day_data) {
        this.day_data = day_data;
    }

    public RealmList<Float> getWeek_data() {
        return week_data;
    }

    public void setWeek_data(RealmList<Float> week_data) {
        this.week_data = week_data;
    }

    public RealmList<Float> getMonth_data() {
        return month_data;
    }

    public void setMonth_data(RealmList<Float> month_data) {
        this.month_data = month_data;
    }

    public RealmList<Float> getYear_data() {
        return year_data;
    }

    public void setYear_data(RealmList<Float> year_data) {
        this.year_data = year_data;
    }
}
