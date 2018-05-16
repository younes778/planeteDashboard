package d2si.apps.planetedashboard.database.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import d2si.apps.planetedashboard.database.data.Representant;
import d2si.apps.planetedashboard.database.data.SyncReport;
import d2si.apps.planetedashboard.database.data.Tiers;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Controller of sales
 * <p>
 * This controller is used to control sales from database
 *
 * @author younessennadj
 */
public class SalesController {
    /**
     * Filter to be applied
     * None : for no filter
     * Item : for filtering by article
     * Client : for filtering by client
     * Representant : for filtering by representant
     */
    public static enum FILTER {
        NONE, ITEM, CLIENT, REPRESENTANT
    }

    ;
    /**
     * the filter
     */
    public static FILTER filter = FILTER.NONE;
    /**
     * list of ids to apply the filter
     */

    public static ArrayList<String> filters;

    /**
     * Method that get sales total for the day date
     *
     * @param date day of the sales
     * @return the profit of the sales
     */
    public static float getSalesTotalByDay(Date date) {
        // set the date between 00:00:00.000 and 23:59:59.999
        float total = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date dateTo = new Date(calendar.getTimeInMillis());
        Realm realm = Realm.getDefaultInstance();
        // get all documents
        RealmResults<Document> documents = realm.where(Document.class).between("date", dateFrom, dateTo).findAll();
        for (Document document : documents) {
            // according to the filter applied
            // if none then procede to lignes
            // if filtered by client check that client exists, same for representant
            if (filter == FILTER.NONE || (filter == FILTER.CLIENT && isIdExist(filters, document.getPcf_code())) || (filter == FILTER.REPRESENTANT && isIdExist(filters, document.getRep_code()))) {
                int sign = 1;
                // we apply the negative sing if it is negative sales
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                // we get all lignes having doc_numero as secondary key
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes)
                    total += ligne.getLig_p_net() * ligne.getLig_qte() * sign;
            } else if (filter == FILTER.ITEM) { // if the filter is item
                int sign = 1;
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes) {
                    // we check if the article exists then we added the total
                    if (isIdExist(filters, ligne.getArt_code()))
                        total += ligne.getLig_p_net() * ligne.getLig_qte() * sign;
                }
            }
        }
        realm.close();
        return total;
    }

    /**
     * Method that get sales quantity for the day date
     *
     * @param date day of the sales
     * @return the quantity of articles sale
     */
    public static int getSalesQuantityByDay(Date date) {
        int total = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date dateTo = new Date(calendar.getTimeInMillis());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Document> documents = realm.where(Document.class).between("date", dateFrom, dateTo).findAll();
        for (Document document : documents) {
            if (filter == FILTER.NONE || (filter == FILTER.CLIENT && isIdExist(filters, document.getPcf_code())) || (filter == FILTER.REPRESENTANT && isIdExist(filters, document.getRep_code()))) {
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes)
                    total += ligne.getLig_qte();
            } else if (filter == FILTER.ITEM) {
                int sign = 1;
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes) {
                    if (isIdExist(filters, ligne.getArt_code()))
                        total += ligne.getLig_qte();
                }
            }
        }
        realm.close();
        return total;
    }

    public static float getSalesTotalByDate(Date date1, Date date2) {
        // set the date between 00:00:00.000 and 23:59:59.999
        float total = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.setTime(date2);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date dateTo = new Date(calendar.getTimeInMillis());
        Realm realm = Realm.getDefaultInstance();
        // get all documents
        RealmResults<Document> documents = realm.where(Document.class).between("date", dateFrom, dateTo).findAll();
        for (Document document : documents) {
            // according to the filter applied
            // if none then procede to lignes
            // if filtered by client check that client exists, same for representant
            if (filter == FILTER.NONE || (filter == FILTER.CLIENT && isIdExist(filters, document.getPcf_code())) || (filter == FILTER.REPRESENTANT && isIdExist(filters, document.getRep_code()))) {
                int sign = 1;
                // we apply the negative sing if it is negative sales
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                // we get all lignes having doc_numero as secondary key
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes)
                    total += ligne.getLig_p_net() * ligne.getLig_qte() * sign;
            } else if (filter == FILTER.ITEM) { // if the filter is item
                int sign = 1;
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes) {
                    // we check if the article exists then we added the total
                    if (isIdExist(filters, ligne.getArt_code()))
                        total += ligne.getLig_p_net() * ligne.getLig_qte() * sign;
                }
            }
        }
        realm.close();
        return total;
    }


    /**
     * Method that get sales number for the day date
     *
     * @param date day of the sales
     * @return the sales number of articles sale
     */
    public static int getSalesNoInvoiceByDay(Date date) {
        int total = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date dateTo = new Date(calendar.getTimeInMillis());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Document> documents = realm.where(Document.class).between("date", dateFrom, dateTo).findAll();
        for (Document document : documents) {
            if (filter == FILTER.NONE || (filter == FILTER.CLIENT && isIdExist(filters, document.getPcf_code())) || (filter == FILTER.REPRESENTANT && isIdExist(filters, document.getRep_code()))) {
                total++;
            } else if (filter == FILTER.ITEM) {
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                boolean isArticleExist = false;
                for (Ligne ligne : lignes) {
                    if (isIdExist(filters, ligne.getArt_code())) {
                        isArticleExist = true;
                        break;
                    }
                }
                if (isArticleExist) total++;
            }
        }
        realm.close();
        return total;
    }

    /**
     * Method that get sales average price for the day date
     *
     * @param date day of the sales
     * @return the average price of articles sale
     */
    public static float getSalesAverageByDay(Date date) {
        if (getSalesQuantityByDay(date) == 0) return 0;
        return getSalesTotalByDay(date) / getSalesQuantityByDay(date);
    }

    /**
     * Method that check if id exist in ids
     *
     * @param ids list of articles
     * @param id       article Id
     * @return true if id exist, false else
     */
    private static boolean isIdExist(ArrayList<String> ids, String id) {
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).equalsIgnoreCase(id)) return true;
        }
        return false;
    }

    public static ArrayList<Object> getDayData() {
        ArrayList<Object> data = new ArrayList<>();

        Calendar calendarToday = Calendar.getInstance();
        Date dateToday = new Date(calendarToday.getTimeInMillis());
        data.add(getSalesTotalByDay(dateToday));
        data.add(getSalesAverageByDay(dateToday));
        data.add(getSalesQuantityByDay(dateToday));
        data.add(getSalesNoInvoiceByDay(dateToday));

        Calendar calendarYesterday = Calendar.getInstance();
        calendarYesterday.set(Calendar.DAY_OF_MONTH, calendarYesterday.get(Calendar.DAY_OF_MONTH) - 1);
        Date dateYesterday = new Date(calendarYesterday.getTimeInMillis());
        data.add(getSalesTotalByDay(dateYesterday));
        data.add(getSalesAverageByDay(dateYesterday));
        data.add(getSalesQuantityByDay(dateYesterday));
        data.add(getSalesNoInvoiceByDay(dateYesterday));

        return data;
    }

    public static ArrayList<Object> getWeekData() {
        ArrayList<Object> data = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - (6 - i));
            final Date dateFrom = new Date(calendar.getTimeInMillis());
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - (13 - i));
            final Date dateTo = new Date(calendar.getTimeInMillis());

            data.add(getSalesTotalByDay(dateFrom));
            data.add(getSalesTotalByDay(dateTo));
        }
        return data;
    }

    public static ArrayList<Object> getMonthData() {
        ArrayList<Object> data = new ArrayList<>();

        for (int i = 0; i < 31; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
            final Date dateFrom = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            final Date dateTo = new Date(calendar.getTimeInMillis());

            data.add(getSalesTotalByDay(dateFrom));
            data.add(getSalesTotalByDay(dateTo));
        }

        return data;
    }

    public static ArrayList<Object> getYearData() {
        ArrayList<Object> data = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH,i);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            final Date dateYearFrom = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            final Date dateYearTo = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-1);
            calendar.set(Calendar.MONTH,i);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            final Date dateLastYearFrom = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            final Date dateLastYearTo = new Date(calendar.getTimeInMillis());

            data.add(getSalesTotalByDate(dateYearFrom,dateYearTo));
            data.add(getSalesTotalByDate(dateLastYearFrom,dateLastYearTo));
        }

        return data;
    }

    public static Date getLastSyncDate(){
        Realm realm = Realm.getDefaultInstance();
        Date date=realm.where(SyncReport.class).equalTo("success",true).findAll().maxDate("date");
        realm.close();
        return date;
    }


}
