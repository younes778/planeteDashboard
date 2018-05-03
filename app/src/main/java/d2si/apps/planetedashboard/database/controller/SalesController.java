package d2si.apps.planetedashboard.database.controller;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import io.realm.Realm;
import io.realm.RealmResults;

public abstract class SalesController {

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
            int sign=1;
            if (document.getDoc_type().equalsIgnoreCase("vn")) sign = -1;
            RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
            for (Ligne ligne:lignes)
                total += ligne.getLig_p_net()*ligne.getLig_qte()*sign;
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
            RealmResults<Ligne> lignes = realm.where(Ligne.class).contains("lig_doc_numero", document.getDoc_numero()).findAll();
            for (Ligne ligne:lignes)
                total += ligne.getLig_qte();
        }
        realm.close();
        return total;
    }


    public static int getSalesNoInvoiceByDay(Date date){
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
        realm.close();
        return documents.size();
    }

    public static float getSalesAverageByDay(Date date){
        return getSalesTotalByDay(date)/getSalesQuantityByDay(date);
    }
}
