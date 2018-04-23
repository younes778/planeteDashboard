package d2si.apps.planetedashboard.database.controller;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.database.data.Sale;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesGetter;
import io.realm.Realm;
import io.realm.RealmResults;

public abstract class SalesController {

    public static RealmResults<Sale> getSalesByDate(Date dateFrom, Date dateTo){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sale> sales = realm.where(Sale.class).between("date", dateFrom,dateTo).findAll();
        realm.close();
        return sales;
    }

    public static RealmResults<Sale> getSalesByDay(Date date){
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.setTimeInMillis(date.getTime());
        calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
        calendarFrom.set(Calendar.MINUTE, 0);
        calendarFrom.set(Calendar.SECOND, 0);
        Date dateFrom = new Date(calendarFrom.getTimeInMillis());
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTimeInMillis(date.getTime());
        calendarTo.set(Calendar.HOUR_OF_DAY, 23);
        calendarTo.set(Calendar.MINUTE, 59);
        calendarTo.set(Calendar.SECOND, 59);
        Date dateTo = new Date(calendarTo.getTimeInMillis());
        return getSalesByDate(dateFrom,dateTo);
    }


    public static float getSalesTotalByDay(Date date){
        float total = 0;
        RealmResults<Sale> sales = getSalesByDay(date);
        if (sales!=null)
            for (Sale sale:sales) total+=sale.getTotal();
        return total;
    }

    public static int getSalesQuantityByDay(Date date){
        int total = 0;
        RealmResults<Sale> sales = getSalesByDay(date);
        if (sales!=null)
            for (Sale sale:sales) total+=sale.getQteTotal();
        return total;
    }


    public static int getSalesNoInvoiceByDay(Date date){
        RealmResults<Sale> sales = getSalesByDay(date);
        if (sales!=null) return sales.size();
        return 0;
    }

    public static int getSalesAverageByDay(Date date){
        float total = 0;
        RealmResults<Sale> sales = getSalesByDay(date);
        if (sales!=null) {
            for (Sale sale : sales) total += sale.calculateAveragePrice();
            return (int) total/sales.size();
        }
        else return 0;
    }
}
