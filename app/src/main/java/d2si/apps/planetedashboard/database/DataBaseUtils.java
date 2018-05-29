package d2si.apps.planetedashboard.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

import static d2si.apps.planetedashboard.AppUtils.dateFormat;

/**
 * Database Utils class
 * <p>
 * Class regrouping the database handling static methods
 *
 * @author younessennadj
 */

public class DataBaseUtils {
    /**
     * Method that add object to database
     *
     * @param objects objects to add
     */
    public static void addObjectToRealm(List<List<? extends RealmObject>> objects) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (int i = 0; i < objects.size(); i++)
            for (RealmObject object : objects.get(i))
                realm.copyToRealm(object); // Persist unmanaged objects

        realm.commitTransaction();
        realm.close();
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(dateFormat);
        return f.format(date);
    }

    /**
     * Method that add object to database
     *
     * @param object objects to add
     */
    public static void addOneObjectToRealm(RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(object); // Persist unmanaged objects

        realm.commitTransaction();
        realm.close();
    }

    /**
     * Method that add or update object to database
     *
     * @param object objects to add
     */
    public static void addOrUpdateOneObjectToRealm(RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(object); // Persist unmanaged objects

        realm.commitTransaction();
        realm.close();
    }

    /**
     * Method that delete all database data
     */
    public static void clearRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // delete all realm objects
        realm.deleteAll();

        //commit realm changes
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Method that form data as needed for requests
     */
    public static String formDateSql(Date date) {
        return new SimpleDateFormat("yyyy-dd-MM").format(date);
    }

    /**
     * Method that form data as needed for requests
     */
    public static String formDateTimeSql(Date date) {
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(date);
    }
}
