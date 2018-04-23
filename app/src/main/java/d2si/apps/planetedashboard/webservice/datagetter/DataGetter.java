package d2si.apps.planetedashboard.webservice.datagetter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.database.data.Sale;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesGetter;

public abstract class DataGetter {

    public abstract void onSalesUpdate();

    public void updateSalesByDate(Context context, Date dateFrom, Date dateTo){
        new SalesGetter(context, dateFrom, dateTo) {
            @Override
            public void onPost(ArrayList<Sale> sales) {
                // delete all database data for test
                AppData.clearRealm();
                for (Sale sale:sales)
                    AppData.addObjectToRealm(sale);
                onSalesUpdate();
            }
        }.execute();
    }
}
