package d2si.apps.planetedashboard.webservice.datagetter;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.controller.DataBaseHandler;
import d2si.apps.planetedashboard.database.controller.DataBaseUpdater;
import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import d2si.apps.planetedashboard.database.data.QuickAccessData;
import d2si.apps.planetedashboard.database.data.Representant;
import d2si.apps.planetedashboard.database.data.SyncReport;
import d2si.apps.planetedashboard.database.data.Tiers;
import d2si.apps.planetedashboard.webservice.httpgetter.ArticlesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.ArticlesUpdater;
import d2si.apps.planetedashboard.webservice.httpgetter.LignesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.LignesUpdater;
import d2si.apps.planetedashboard.webservice.httpgetter.RepresentantsGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.RepresentantsUpdater;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesUpdater;
import d2si.apps.planetedashboard.webservice.httpgetter.TiersGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.TiersUpdater;
import d2si.apps.planetedashboard.webservice.httpgetter.UserGetter;
import io.realm.RealmObject;

/**
 * class that get and update data from server and copy it to database
 *
 * @author younessennadj
 */

public abstract class DataGetter {

    /**
     * Method that executes on sales update
     */
    public abstract void onSalesUpdate(boolean succes);

    /**
     * Method that executes on sales get
     */
    public abstract void onSalesGet(boolean success);

    /**
     * Method that executes on user updated
     *
     * @param isConected true if the user is connected, false if not
     */
    public abstract void onUserUpdate(Boolean isConected);

    private List<List<? extends RealmObject>> objectsToCopy;

    /**
     * Method that check the user crediants
     *
     * @param context  app actual context
     * @param user     user name
     * @param password user password
     */
    public void checkUserCrediants(final Context context, final String user, final String password) {
        new UserGetter(context, user, password) {
            @Override
            public void onPost(Boolean isConnected) {
                onUserUpdate(isConnected);
            }
        }.execute();
    }


    /**
     * Method that get sales, articles, lignes, clients and representants from server for the first time
     *
     * @param context  app actual context
     * @param dateFrom start date
     * @param dateTo   end date
     */
    public void getSalesByDate(final Context context, final Date dateFrom, final Date dateTo) {
        // delete all database
        AppUtils.clearRealm();

        new SalesGetter(context, dateFrom, dateTo) {
            @Override
            public void onPost(ArrayList<Document> sales) {
                if (sales == null) {
                    AppUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_document)));
                    onSalesGet(false);
                } else {
                    objectsToCopy = new ArrayList<>();
                    objectsToCopy.add(new ArrayList<RealmObject>(sales));

                    new ArticlesGetter(context, dateFrom, dateTo) {
                        @Override
                        public void onPost(ArrayList<Article> articles) {
                            if (articles == null) {
                                AppUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_article)));
                                onSalesGet(false);
                            } else {
                                objectsToCopy.add(new ArrayList<RealmObject>(articles));

                                new LignesGetter(context, dateFrom, dateTo) {
                                    @Override
                                    public void onPost(ArrayList<Ligne> lignes) {
                                        if (lignes == null) {
                                            AppUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_ligne)));
                                            onSalesGet(false);
                                        } else {

                                            objectsToCopy.add(new ArrayList<RealmObject>(lignes));

                                            new TiersGetter(context, dateFrom, dateTo) {
                                                @Override
                                                public void onPost(ArrayList<Tiers> tiers) {
                                                    if (tiers == null) {
                                                        AppUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_tiers)));
                                                        onSalesGet(false);
                                                    } else {
                                                        objectsToCopy.add(new ArrayList<RealmObject>(tiers));

                                                        new RepresentantsGetter(context, dateFrom, dateTo) {
                                                            @Override
                                                            public void onPost(ArrayList<Representant> representants) {
                                                                if (representants == null) {
                                                                    AppUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_representant)));
                                                                    onSalesGet(false);
                                                                } else {
                                                                    objectsToCopy.add(new ArrayList<RealmObject>(representants));
                                                                    new DataBaseHandler(objectsToCopy) {
                                                                        @Override
                                                                        public void onPost() {
                                                                            AppUtils.addOneObjectToRealm(new SyncReport(dateTo, true, context.getString(R.string.sync_report_tables_updated)));
                                                                            onSalesGet(true);
                                                                        }
                                                                    }.execute();

                                                                }
                                                            }
                                                        }.execute();

                                                    }
                                                }
                                            }.execute();
                                        }
                                    }
                                }.execute();

                            }
                        }
                    }.execute();
                }
            }
        }.execute();
    }

    /**
     * Method that update sales, articles, lignes, clients and representants from server
     *
     * @param context  app actual context
     * @param dateFrom start date
     */
    public void updateSalesByDate(final Context context, final Date dateFrom) {
        final Date lastSync = new Date(Calendar.getInstance().getTimeInMillis());

        new SalesUpdater(context, dateFrom) {
            @Override
            public void onPost(ArrayList<Document> sales) {
                if (sales == null) {
                    AppUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_document)));
                    onSalesUpdate(false);
                } else {
                    objectsToCopy = new ArrayList<>();
                    objectsToCopy.add(new ArrayList<RealmObject>(sales));

                    new ArticlesUpdater(context, dateFrom) {
                        @Override
                        public void onPost(ArrayList<Article> articles) {
                            if (articles == null) {
                                AppUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_article)));
                                onSalesUpdate(false);
                            } else {
                                objectsToCopy.add(new ArrayList<RealmObject>(articles));

                                new LignesUpdater(context, dateFrom) {
                                    @Override
                                    public void onPost(ArrayList<Ligne> lignes) {
                                        if (lignes == null) {
                                            AppUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_ligne)));
                                            onSalesUpdate(false);
                                        } else {

                                            objectsToCopy.add(new ArrayList<RealmObject>(lignes));

                                            new TiersUpdater(context, dateFrom) {
                                                @Override
                                                public void onPost(ArrayList<Tiers> tiers) {
                                                    if (tiers == null) {
                                                        AppUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_tiers)));
                                                        onSalesUpdate(false);
                                                    } else {
                                                        objectsToCopy.add(new ArrayList<RealmObject>(tiers));

                                                        new RepresentantsUpdater(context, dateFrom) {
                                                            @Override
                                                            public void onPost(ArrayList<Representant> representants) {
                                                                if (representants == null) {
                                                                    AppUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_representant)));
                                                                    onSalesUpdate(false);
                                                                } else {
                                                                    objectsToCopy.add(new ArrayList<RealmObject>(representants));
                                                                    new DataBaseUpdater(objectsToCopy) {
                                                                        @Override
                                                                        public void onPost() {
                                                                            AppUtils.addOneObjectToRealm(new SyncReport(lastSync, true, context.getString(R.string.sync_report_tables_updated)));
                                                                            onSalesUpdate(true);
                                                                        }
                                                                    }.execute();

                                                                }
                                                            }
                                                        }.execute();

                                                    }
                                                }
                                            }.execute();
                                        }
                                    }
                                }.execute();

                            }
                        }
                    }.execute();
                }
            }
        }.execute();
    }
}
