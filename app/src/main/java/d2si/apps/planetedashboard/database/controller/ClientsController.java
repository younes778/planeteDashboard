package d2si.apps.planetedashboard.database.controller;

import java.util.ArrayList;

import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Tiers;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ClientsController {

    public static ArrayList<Tiers> getAllClients(){
        ArrayList<Tiers> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Tiers> tiers = realm.where(Tiers.class).equalTo("pcf_type","C").findAll();
        for (Tiers tier:tiers) {
            result.add(tier);
        }
        realm.close();
        return result;
    }

    public static ArrayList<String> getClientsLabel(ArrayList<Tiers> tiers){
        ArrayList<String> tiersName = new ArrayList<>();
        for (Tiers tier:tiers)
            tiersName.add(tier.getPcf_rs());
        return tiersName;
    }

    public static ArrayList<String> getClientsId(ArrayList<Tiers> tiers){
        ArrayList<String> tiersName = new ArrayList<>();
        for (Tiers tier:tiers)
            tiersName.add(tier.getPcf_code());
        return tiersName;
    }

    public static ArrayList<RealmObject> getClientsSubList(ArrayList<Tiers> tiers, ArrayList<Integer> which){
        ArrayList<RealmObject> filters = new ArrayList<>();
        for (int i=0;i<which.size();i++)
            filters.add(tiers.get(which.get(i)));
        return filters;
    }

    public static ArrayList<String> getClientsFamilies(){
        ArrayList<String> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Tiers> tiers = realm.where(Tiers.class).distinct("fat_lib").findAll();
        for (Tiers tier:tiers) {
            if (!tier.getFat_lib().equals(""))
            result.add(tier.getFat_lib());
        }
        realm.close();
        return result;
    }

    public static ArrayList<Tiers> getClientsbyFamily(ArrayList<Tiers> clients,String family){
        ArrayList<Tiers> result = new ArrayList<>();
        for (Tiers client:clients)
            if (client.getFat_lib().equals(family))
                result.add(client);
        return result;
    }

}
