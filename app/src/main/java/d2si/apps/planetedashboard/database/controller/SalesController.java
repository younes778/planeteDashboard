package d2si.apps.planetedashboard.database.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.database.data.Sale;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesGetter;
import io.realm.Realm;
import io.realm.RealmResults;

public class SalesController {

    public static void updateSalesByDate(Context context, Date dateFrom, Date dateTo){
        new SalesGetter(context, dateFrom.toString(), dateTo.toString()) {
            @Override
            public void onPost(ArrayList<Sale> sales) {
                for (Sale sale:sales)
                    AppData.addObjectToRealm(sale);
            }
        }.execute();
    }

    public static RealmResults<Sale> getSalesByDate(Date dateFrom, Date dateTo){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sale> sales = realm.where(Sale.class).between("date", dateFrom,dateTo).findAll();
        return sales;
    }

    public static RealmResults<Sale> getSalesByDay(Date date){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sale> sales = realm.where(Sale.class).equalTo("date", date).findAll();
        return sales;
    }

    public static float getSalesTotalByDate(Date dateFrom, Date dateTo){
        float total = 0;
        RealmResults<Sale> sales = getSalesByDate(dateFrom,dateTo);
        if (sales!=null)
        for (Sale sale:sales) total+=sale.getTotal();
        return total;
    }

    public static float getSalesTotalByDay(Date date){
        float total = 0;
        RealmResults<Sale> sales = getSalesByDay(date);
        if (sales!=null)
            for (Sale sale:sales) total+=sale.getTotal();
        return total;
    }
}
