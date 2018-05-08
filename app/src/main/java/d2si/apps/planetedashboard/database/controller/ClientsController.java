package d2si.apps.planetedashboard.database.controller;

import java.util.ArrayList;

import d2si.apps.planetedashboard.database.data.Tiers;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Controller of clients
 * <p>
 * This controller is used to control clients from database
 *
 * @author younessennadj
 */


public class ClientsController {

    /**
     * Method that get all clients from database
     *
     * @return all cleitns from database
     */
    public static ArrayList<Tiers> getAllClients() {
        ArrayList<Tiers> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Tiers> tiers = realm.where(Tiers.class).equalTo("pcf_type", "C").findAll();
        for (Tiers tier : tiers) {
            result.add(tier);
        }
        realm.close();
        return result;
    }

    /**
     * Method that get client labels from database
     *
     * @param tiers list of clients
     * @return list of clients labels
     */
    public static ArrayList<String> getClientsLabel(ArrayList<Tiers> tiers) {
        ArrayList<String> tiersName = new ArrayList<>();
        for (Tiers tier : tiers)
            tiersName.add(tier.getPcf_rs());
        return tiersName;
    }

    /**
     * Method that get clients Ids from database
     *
     * @param tiers list of clients
     * @return list of clients Ids
     */
    public static ArrayList<String> getClientsId(ArrayList<Tiers> tiers) {
        ArrayList<String> tiersName = new ArrayList<>();
        for (Tiers tier : tiers)
            tiersName.add(tier.getPcf_code());
        return tiersName;
    }

    /**
     * Method that get sub list of clients with indexes in which
     *
     * @param tiers list of clients
     * @param which indexes of articles to return
     * @return sub list of clients with which indexes
     */
    public static ArrayList<RealmObject> getClientsSubList(ArrayList<Tiers> tiers, ArrayList<Integer> which) {
        ArrayList<RealmObject> filters = new ArrayList<>();
        for (int i = 0; i < which.size(); i++)
            filters.add(tiers.get(which.get(i)));
        return filters;
    }

    /**
     * Method that get clients families from database
     *
     * @return list of clients families
     */
    public static ArrayList<String> getClientsFamilies() {
        ArrayList<String> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Tiers> tiers = realm.where(Tiers.class).distinct("fat_lib").findAll();
        for (Tiers tier : tiers) {
            if (!tier.getFat_lib().equals(""))
                result.add(tier.getFat_lib());
        }
        realm.close();
        return result;
    }

    /**
     * Method that get clients by family from database
     *
     * @param clients list of cliets
     * @param family  client family
     * @return list of clients associated with family
     */
    public static ArrayList<Tiers> getClientsbyFamily(ArrayList<Tiers> clients, String family) {
        ArrayList<Tiers> result = new ArrayList<>();
        for (Tiers client : clients)
            if (client.getFat_lib().equals(family))
                result.add(client);
        return result;
    }

}
