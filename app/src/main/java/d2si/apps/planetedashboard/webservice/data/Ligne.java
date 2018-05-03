package d2si.apps.planetedashboard.webservice.data;


public class Ligne {

	private String lig_doc_numero;
	private int lig_qte;
	private float lig_p_net;
	private String art_code;

	public Ligne() {
	}

	public Ligne(String lig_doc_numero, int lig_qte, float lig_p_net, String art_code) {
		this.lig_doc_numero = lig_doc_numero;
		this.lig_qte = lig_qte;
		this.lig_p_net = lig_p_net;
		this.art_code = art_code;
	}

	public String getLig_doc_numero() {
		return lig_doc_numero;
	}

	public void setLig_doc_numero(String lig_doc_numero) {
		this.lig_doc_numero = lig_doc_numero;
	}

	public int getLig_qte() {
		return lig_qte;
	}

	public void setLig_qte(int lig_qte) {
		this.lig_qte = lig_qte;
	}

	public float getLig_p_net() {
		return lig_p_net;
	}

	public void setLig_p_net(float lig_p_net) {
		this.lig_p_net = lig_p_net;
	}

	public String getArt_code() {
		return art_code;
	}

	public void setArt_code(String art_code) {
		this.art_code = art_code;
	}
	
}
