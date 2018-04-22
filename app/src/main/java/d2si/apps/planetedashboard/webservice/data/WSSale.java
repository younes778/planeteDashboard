package d2si.apps.planetedashboard.webservice.data;

import java.sql.Date;

/**
 * Sale
 *
 * Object that represents the sale
 *
 * @author younessennadj
 */

public class WSSale {
	private String numero;
	private Date date;
	private WSLigne[] lignes;
	
	/**
     * Sale constructor
     *
     * @param numero sale id
     * @param date date of the sale
	 * @param lignes lines of sale
     */
	 public WSSale(String numero, Date date, WSLigne[] lignes) {
		this.numero = numero;
		this.date = date;
		this.lignes = lignes;
	}

	public WSSale(){

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
	public WSLigne[] getLignes() {
		return lignes;
	}
	/**
     * Ligne setter
     *
     * @param lignes sale lignes
     */
	public void setLignes(WSLigne[] lignes) {
		this.lignes = lignes;
	}
}
