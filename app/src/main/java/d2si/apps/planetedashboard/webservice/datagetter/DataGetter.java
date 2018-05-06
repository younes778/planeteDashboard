package d2si.apps.planetedashboard.webservice.datagetter;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import d2si.apps.planetedashboard.database.data.Representant;
import d2si.apps.planetedashboard.database.data.Tiers;
import d2si.apps.planetedashboard.webservice.httpgetter.ArticlesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.LignesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.RepresentantsGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.TiersGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.UserGetter;

public abstract class DataGetter {

    public abstract void onSalesUpdate();
    public abstract void onUserUpdate(Boolean isConected);

    public void checkUserCrediants(final Context context, final String user, final String password) {
        new UserGetter(context, user, password) {
            @Override
            public void onPost(Boolean isConnected) {
                if (isConnected) {
                    SharedPreferences.Editor editor = AppUtils.getSharedPreferenceEdito(context);
                    // save in preferences the connected user
                    editor.putBoolean(context.getString(R.string.pref_is_connected), true);
                    editor.putString(context.getString(R.string.pref_user),user);
                    editor.putString(context.getString(R.string.pref_password),password);
                    editor.apply();
                }
                onUserUpdate(isConnected);
            }
        }.execute();
    }


    public void updateSalesByDate(final Context context, final Date dateFrom, final Date dateTo){
        // delete all database data for test
        AppUtils.clearRealm();

        new SalesGetter(context, dateFrom, dateTo) {
            @Override
            public void onPost(ArrayList<Document> sales) {

                for (Document sale:sales)
                    AppUtils.addObjectToRealm(sale);

                new ArticlesGetter(context, dateFrom, dateTo) {
                    @Override
                    public void onPost(ArrayList<Article> articles) {

                        for (Article article:articles)
                            AppUtils.addObjectToRealm(article);

                        new LignesGetter(context, dateFrom, dateTo) {
                            @Override
                            public void onPost(ArrayList<Ligne> lignes) {

                                for (Ligne ligne:lignes)
                                    AppUtils.addObjectToRealm(ligne);

                                new TiersGetter(context, dateFrom, dateTo) {
                                    @Override
                                    public void onPost(ArrayList<Tiers> tiers) {

                                        for (Tiers tier:tiers)
                                            AppUtils.addObjectToRealm(tier);

                                        new RepresentantsGetter(context, dateFrom, dateTo) {
                                            @Override
                                            public void onPost(ArrayList<Representant> representants) {

                                                for (Representant representant:representants)
                                                    AppUtils.addObjectToRealm(representant);

                                                onSalesUpdate();

                                            }
                                        }.execute();

                                    }
                                }.execute();

                            }
                        }.execute();

                    }
                }.execute();

            }
        }.execute();



    }
}
