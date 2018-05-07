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

public abstract class SalesController {

    public static enum FILTER  {NONE,ITEM,CLIENT,REPRESENTANT};
    public static FILTER filter = FILTER.NONE;
    public static ArrayList<RealmObject> filters;

    public static float getSalesTotalByDay(Date date){
        float total = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date dateTo = new Date(calendar.getTimeInMillis());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Document> documents = realm.where(Document.class).between("date", dateFrom,dateTo).findAll();
        for (Document document:documents) {
            if (filter== FILTER.NONE || (filter==FILTER.CLIENT && isClientExist(filters,document.getPcf_code())) || (filter==FILTER.REPRESENTANT && isRepresentantExist(filters,document.getRep_code()))) {
                int sign = 1;
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes)
                    total += ligne.getLig_p_net() * ligne.getLig_qte() * sign;
            } else if (filter==FILTER.ITEM){
                int sign = 1;
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes) {
                    if (isArticleExist(filters,ligne.getArt_code()))
                    total += ligne.getLig_p_net() * ligne.getLig_qte() * sign;
                }
            }
        }
        realm.close();
        return total;
    }

    public static int getSalesQuantityByDay(Date date){
        int total = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date dateTo = new Date(calendar.getTimeInMillis());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Document> documents = realm.where(Document.class).between("date", dateFrom,dateTo).findAll();
        for (Document document:documents) {
            if (filter== FILTER.NONE || (filter==FILTER.CLIENT && isClientExist(filters,document.getPcf_code())) || (filter==FILTER.REPRESENTANT && isRepresentantExist(filters,document.getRep_code()))) {
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes)
                    total += ligne.getLig_qte();
            } else if (filter==FILTER.ITEM){
                int sign = 1;
                if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                for (Ligne ligne : lignes) {
                    if (isArticleExist(filters,ligne.getArt_code()))
                        total += ligne.getLig_qte();
                }
            }
        }
        realm.close();
        return total;
    }


    public static int getSalesNoInvoiceByDay(Date date){
        int total =0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date dateTo = new Date(calendar.getTimeInMillis());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Document> documents = realm.where(Document.class).between("date", dateFrom,dateTo).findAll();
        for (Document document:documents) {
            if (filter== FILTER.NONE || (filter==FILTER.CLIENT && isClientExist(filters,document.getPcf_code())) || (filter==FILTER.REPRESENTANT && isRepresentantExist(filters,document.getRep_code()))) {
                total++;
            } else if (filter==FILTER.ITEM) {
                RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
                boolean isArticleExist = false;
                for (Ligne ligne : lignes) {
                    if (isArticleExist(filters,ligne.getArt_code())){
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

    public static float getSalesAverageByDay(Date date){
        if (getSalesQuantityByDay(date)==0) return 0;
        return getSalesTotalByDay(date)/getSalesQuantityByDay(date);
    }

    // To be moved





    public static ArrayList<Representant> getSalesRepresentants(){
        ArrayList<Representant> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Representant> representants = realm.where(Representant.class).findAll();
        for (Representant representant:representants) {
            result.add(representant);
        }
        realm.close();
        return result;
    }


    public static ArrayList<String> getRepresentantName(ArrayList<Representant> representants){
        ArrayList<String> representantName = new ArrayList<>();
        for (Representant representant:representants)
            representantName.add(representant.getRep_nom());
        return representantName;
    }

    public static ArrayList<RealmObject> getSubRepresentant(ArrayList<Representant> representants, Integer[] which){
        ArrayList<RealmObject> filters = new ArrayList<>();
        for (int i=0;i<which.length;i++)
            filters.add(representants.get(which[i]));
        return filters;
    }

    private static boolean isArticleExist(ArrayList<RealmObject> articles,String id){
        for (int i=0;i<articles.size();i++){
            if (((Article) articles.get(i)).getArt_code().equalsIgnoreCase(id)) return true;
        }
        return false;
    }

    private static boolean isClientExist(ArrayList<RealmObject> tiers,String id){
        for (int i=0;i<tiers.size();i++){
            if (((Tiers) tiers.get(i)).getPcf_code().equalsIgnoreCase(id)) return true;
        }
        return false;
    }

    private static boolean isRepresentantExist(ArrayList<RealmObject> representants,String id){
        for (int i=0;i<representants.size();i++){
            if (((Representant) representants.get(i)).getRep_code().equalsIgnoreCase(id)) return true;
        }
        return false;
    }
}
