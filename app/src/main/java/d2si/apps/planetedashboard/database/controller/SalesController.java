package d2si.apps.planetedashboard.database.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import d2si.apps.planetedashboard.database.data.Representant;
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
public abstract class SalesController {
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
     * list of objects to apply the filter
     */
    public static ArrayList<RealmObject> filters;

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
            if (filter == FILTER.NONE || (filter == FILTER.CLIENT && isClientExist(filters, document.getPcf_code())) || (filter == FILTER.REPRESENTANT && isRepresentantExist(filters, document.getRep_code()))) {
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
                    if (isArticleExist(filters, ligne.getArt_code()))
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
            if (filter == FILTER.NONE || (filter == FILTER.CLIENT && isClientExist(filters, document.getPcf_code())) || (filter == FILTER.REPRESENTANT && isRepresentantExist(filters, document.getRep_code()))) {
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes)
                    total += ligne.getLig_qte();
            } else if (filter == FILTER.ITEM) {
                int sign = 1;
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes) {
                    if (isArticleExist(filters, ligne.getArt_code()))
                        total += ligne.getLig_qte();
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
            if (filter == FILTER.NONE || (filter == FILTER.CLIENT && isClientExist(filters, document.getPcf_code())) || (filter == FILTER.REPRESENTANT && isRepresentantExist(filters, document.getRep_code()))) {
                total++;
            } else if (filter == FILTER.ITEM) {
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                boolean isArticleExist = false;
                for (Ligne ligne : lignes) {
                    if (isArticleExist(filters, ligne.getArt_code())) {
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
     * Method that check if article with id exist in articles
     *
     * @param articles list of articles
     * @param id       article Id
     * @return true if article exist, false else
     */
    private static boolean isArticleExist(ArrayList<RealmObject> articles, String id) {
        for (int i = 0; i < articles.size(); i++) {
            if (((Article) articles.get(i)).getArt_code().equalsIgnoreCase(id)) return true;
        }
        return false;
    }

    /**
     * Method that check if client with id exist in tiers
     *
     * @param tiers list of clients
     * @param id    client Id
     * @return true if client exist, false else
     */
    private static boolean isClientExist(ArrayList<RealmObject> tiers, String id) {
        for (int i = 0; i < tiers.size(); i++) {
            if (((Tiers) tiers.get(i)).getPcf_code().equalsIgnoreCase(id)) return true;
        }
        return false;
    }

    /**
     * Method that check if representant with id exist in representants
     *
     * @param representants list of articles
     * @param id            article Id
     * @return true if representant exist, false else
     */
    private static boolean isRepresentantExist(ArrayList<RealmObject> representants, String id) {
        for (int i = 0; i < representants.size(); i++) {
            if (((Representant) representants.get(i)).getRep_code().equalsIgnoreCase(id))
                return true;
        }
        return false;
    }
}
