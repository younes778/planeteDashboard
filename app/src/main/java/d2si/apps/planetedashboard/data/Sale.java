package d2si.apps.planetedashboard.data;

import java.sql.Date;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Sale
 *
 * Object that represents the sale
 *
 * @author younessennadj
 */

public class Sale extends RealmObject {
	@PrimaryKey
	private String numero;
	private Date date;
	private RealmList<Ligne> lignes;

    /**
     * Sale constructor
     *
     */
    public Sale(){

    }

	/**
     * Sale constructor
     *
     * @param numero sale id
     * @param date date of the sale
     */
	 public Sale(String numero, Date date,final ArrayList<Ligne> lignes) {
		this.numero = numero;
		this.date = date;
		this.lignes  = new RealmList<Ligne>(){{addAll(lignes);}};
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
     * Ligne setter
     *
     * @param lignes sale lignes
     */
	public void setLignes(final ArrayList<Ligne> lignes) {
		this.lignes = new RealmList<Ligne>(){{addAll(lignes);}};
	}
}
