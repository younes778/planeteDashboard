package d2si.apps.planetedashboard.webservice.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.DataBaseUtils;
import d2si.apps.planetedashboard.database.controller.DataBaseController;
import d2si.apps.planetedashboard.database.data.SyncReport;
import io.realm.RealmObject;

/**
 * class that get and update data from server and copy it to database
 *
 * @author younessennadj
 */

public abstract class DataController {

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
        new UserController(context, user, password) {
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
        DataBaseUtils.clearRealm();

        new ObjectsController(context, dateFrom, dateTo, ObjectsController.DATA_TYPE.SALES) {
            @Override
            public void onPost(ArrayList<? extends RealmObject> sales) {
                if (sales == null) {
                    DataBaseUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_document)));
                    onSalesGet(false);
                } else {
                    objectsToCopy = new ArrayList<>();
                    objectsToCopy.add(sales);

                    new ObjectsController(context, dateFrom, dateTo, DATA_TYPE.ARTICLES) {
                        @Override
                        public void onPost(ArrayList<? extends RealmObject> articles) {
                            if (articles == null) {
                                DataBaseUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_article)));
                                onSalesGet(false);
                            } else {
                                objectsToCopy.add(articles);

                                new ObjectsController(context, dateFrom, dateTo, DATA_TYPE.LIGNES) {
                                    @Override
                                    public void onPost(ArrayList<? extends RealmObject> lignes) {
                                        if (lignes == null) {
                                            DataBaseUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_ligne)));
                                            onSalesGet(false);
                                        } else {

                                            objectsToCopy.add(lignes);

                                            new ObjectsController(context, dateFrom, dateTo, DATA_TYPE.TIERS) {
                                                @Override
                                                public void onPost(ArrayList<? extends RealmObject> tiers) {
                                                    if (tiers == null) {
                                                        DataBaseUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_tiers)));
                                                        onSalesGet(false);
                                                    } else {
                                                        objectsToCopy.add(tiers);

                                                        new ObjectsController(context, dateFrom, dateTo, DATA_TYPE.REPRESENTANT) {
                                                            @Override
                                                            public void onPost(ArrayList<? extends RealmObject> representants) {
                                                                if (representants == null) {
                                                                    DataBaseUtils.addOneObjectToRealm(new SyncReport(dateTo, false, context.getString(R.string.sync_report_tables_error_representant)));
                                                                    onSalesGet(false);
                                                                } else {
                                                                    objectsToCopy.add(representants);
                                                                    new DataBaseController(objectsToCopy, true) {
                                                                        @Override
                                                                        public void onPost() {
                                                                            DataBaseUtils.addOneObjectToRealm(new SyncReport(dateTo, true, context.getString(R.string.sync_report_tables_updated)));
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

        new ObjectsController(context, dateFrom, null, ObjectsController.DATA_TYPE.SALES) {
            @Override
            public void onPost(ArrayList<? extends RealmObject> sales) {
                if (sales == null) {
                    DataBaseUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_document)));
                    onSalesUpdate(false);
                } else {
                    objectsToCopy = new ArrayList<>();
                    objectsToCopy.add(sales);

                    new ObjectsController(context, dateFrom, null, DATA_TYPE.ARTICLES) {
                        @Override
                        public void onPost(ArrayList<? extends RealmObject> articles) {
                            if (articles == null) {
                                DataBaseUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_article)));
                                onSalesUpdate(false);
                            } else {
                                objectsToCopy.add(articles);

                                new ObjectsController(context, dateFrom, null, DATA_TYPE.LIGNES) {
                                    @Override
                                    public void onPost(ArrayList<? extends RealmObject> lignes) {
                                        if (lignes == null) {
                                            DataBaseUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_ligne)));
                                            onSalesUpdate(false);
                                        } else {

                                            objectsToCopy.add(lignes);

                                            new ObjectsController(context, dateFrom, null, DATA_TYPE.TIERS) {
                                                @Override
                                                public void onPost(ArrayList<? extends RealmObject> tiers) {
                                                    if (tiers == null) {
                                                        DataBaseUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_tiers)));
                                                        onSalesUpdate(false);
                                                    } else {
                                                        objectsToCopy.add(tiers);

                                                        new ObjectsController(context, dateFrom, null, DATA_TYPE.REPRESENTANT) {
                                                            @Override
                                                            public void onPost(ArrayList<? extends RealmObject> representants) {
                                                                if (representants == null) {
                                                                    DataBaseUtils.addOneObjectToRealm(new SyncReport(lastSync, false, context.getString(R.string.sync_report_tables_error_representant)));
                                                                    onSalesUpdate(false);
                                                                } else {
                                                                    objectsToCopy.add(representants);
                                                                    new DataBaseController(objectsToCopy, false) {
                                                                        @Override
                                                                        public void onPost() {
                                                                            DataBaseUtils.addOneObjectToRealm(new SyncReport(lastSync, true, context.getString(R.string.sync_report_tables_updated)));
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
