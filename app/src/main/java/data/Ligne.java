package data;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Ligne
 *
 * Object that represents an article
 *
 * @author younessennadj
 */

public class Ligne extends RealmObject {
	@PrimaryKey
	private String numero;
	private int qte;
	private float prix;
	
	 /**
     * Ligne constructor
     *
     * @param numero ligne id
     * @param qte Quantity of articles 
     * @param prix Unit Price of the article
     */
	public Ligne(String numero, int qte, float prix) {
		this.numero = numero;
		this.qte = qte;
		this.prix = prix;
	}
	
	/**
     * Id getter
     *
     * @return Ligne id
     */
	public String getNumero() {
		return numero;
	}
	/**
     * Id setter
     *
     * @param numero ligne id
     */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
     * Quantity getter
     *
     * @return Articles Quantity
     */
	public int getQte() {
		return qte;
	}
	/**
     * Quantity setter
     *
     * @param qte Articles Quantity
     */
	public void setQte(int qte) {
		this.qte = qte;
	}
	/**
     * Unit price getter
     *
     * @return Articles unit price
     */
	public float getPrix() {
		return prix;
	}
	/**
     * Unit price  setter
     *
     * @param prix Articles unit price
     */
	public void setPrix(float prix) {
		this.prix = prix;
	}
	
	
}
