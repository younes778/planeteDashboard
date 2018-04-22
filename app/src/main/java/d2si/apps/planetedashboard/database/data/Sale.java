package d2si.apps.planetedashboard.database.data;

import java.util.Date;

import d2si.apps.planetedashboard.AppData;
import d2si.apps.planetedashboard.webservice.data.WSLigne;
import d2si.apps.planetedashboard.webservice.data.WSSale;
import io.realm.*;
import io.realm.annotations.PrimaryKey;

/**
 * Sale
 * <p>
 * Object that represents the sale
 *
 * @author younessennadj
 */

public class Sale extends RealmObject {
    @PrimaryKey
    private String numero;
    private Date date;
    private RealmList<Ligne> lignes= new RealmList<>();

    /**
     * Sale constructor
     */
    public Sale() {

    }

    /**
     * Sale constructor
     *
     * @param sale webservice sale which has different architecture
     */

    public Sale(WSSale sale){

        this.numero = sale.getNumero();
        this.date = sale.getDate();
        this.lignes = new RealmList<Ligne>();
        for (WSLigne ligne:sale.getLignes())
            this.lignes.add(new Ligne(this.numero+"_"+ligne.getNumero(),ligne.getQte(),ligne.getPrix()));
    }

    /**
     * Id getter
     *
     * @return sale id
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Id setter
     *
     * @param numero sale id
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Date getter
     *
     * @return sale date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Date setter
     *
     * @param date sale date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Lignes getter
     *
     * @return lignes of the sale
     */
    public RealmList<Ligne> getLignes() {
        return lignes;
    }

    /**
     * Total getter of all ligne prices
     *
     * @return Total price of differente lignes of Sale
     */
    public float getTotal() {
        float total = 0;
        for (Ligne ligne:lignes) total+=ligne.getPrix();
        return total;
    }
}
