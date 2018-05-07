package d2si.apps.planetedashboard.database.controller;

import java.util.ArrayList;

import d2si.apps.planetedashboard.database.data.Representant;
import d2si.apps.planetedashboard.database.data.Tiers;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RepresentantController {

    public static ArrayList<Representant> getAllRepresentants(){
        ArrayList<Representant> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Representant> representants = realm.where(Representant.class).findAll();
        for (Representant representant:representants) {
            result.add(representant);
        }
        realm.close();
        return result;
    }

    public static ArrayList<String> getRepresentantsLabel(ArrayList<Representant> representants){
        ArrayList<String> representantName = new ArrayList<>();
        for (Representant representant:representants)
            representantName.add(representant.getRep_nom()+" "+representant.getRep_prenom());
        return representantName;
    }

    public static ArrayList<String> getRepresentantsId(ArrayList<Representant> representants){
        ArrayList<String> representantName = new ArrayList<>();
        for (Representant representant:representants)
            representantName.add(representant.getRep_code());
        return representantName;
    }

    public static ArrayList<RealmObject> getRepresentantSubList(ArrayList<Representant> representants, ArrayList<Integer> which){
        ArrayList<RealmObject> filters = new ArrayList<>();
        for (int i=0;i<which.size();i++)
            filters.add(representants.get(which.get(i)));
        return filters;
    }

}
