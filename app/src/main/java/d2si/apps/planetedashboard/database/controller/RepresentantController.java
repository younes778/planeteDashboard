package d2si.apps.planetedashboard.database.controller;

import java.util.ArrayList;

import d2si.apps.planetedashboard.database.data.Representant;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Controller of representants
 * <p>
 * This controller is used to control representants from database
 *
 * @author younessennadj
 */
public class RepresentantController {

    /**
     * Method that get all representants from database
     *
     * @return all representants from database
     */
    public static ArrayList<Representant> getAllRepresentants() {
        ArrayList<Representant> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Representant> representants = realm.where(Representant.class).findAll();
        for (Representant representant : representants) {
            result.add(representant);
        }
        realm.close();
        return result;
    }

    /**
     * Method that get representants labels from database
     *
     * @param representants list of clients
     * @return list of representants labels
     */
    public static ArrayList<String> getRepresentantsLabel(ArrayList<Representant> representants) {
        ArrayList<String> representantName = new ArrayList<>();
        for (Representant representant : representants)
            representantName.add(representant.getRep_nom() + " " + representant.getRep_prenom());
        return representantName;
    }

    /**
     * Method that get representants Ids from database
     *
     * @param representants list of clients
     * @return list of representants Ids
     */
    public static ArrayList<String> getRepresentantsId(ArrayList<Representant> representants) {
        ArrayList<String> representantName = new ArrayList<>();
        for (Representant representant : representants)
            representantName.add(representant.getRep_code());
        return representantName;
    }

    /**
     * Method that get sub list of articles with indexes in which
     *
     * @param representants list of representants
     * @param which         indexes of articles to return
     * @return sub list of representants with which indexes
     */
    public static ArrayList<String> getRepresentantSubList(ArrayList<Representant> representants, ArrayList<Integer> which) {
        ArrayList<String> filters = new ArrayList<>();
        for (int i = 0; i < which.size(); i++)
            filters.add(representants.get(which.get(i)).getRep_code());
        return filters;
    }

}
