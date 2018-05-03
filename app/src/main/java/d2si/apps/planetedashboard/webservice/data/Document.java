package d2si.apps.planetedashboard.webservice.data;

import java.util.Date;

public class Document {
	private String doc_numero;
	private String doc_type;
	private Date date;
	private String pcf_code;
	private String rep_code;

	public Document() {
	}

	public Document(String doc_numero, String doc_type, Date date, String pcf_code, String rep_code) {
		super();
		this.doc_numero = doc_numero;
		this.doc_type = doc_type;
		this.date = date;
		this.pcf_code = pcf_code;
		this.rep_code = rep_code;
	}


	public String getDoc_numero() {
		return doc_numero;
	}

	public void setDoc_numero(String doc_numero) {
		this.doc_numero = doc_numero;
	}

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPcf_code() {
		return pcf_code;
	}

	public void setPcf_code(String pcf_code) {
		this.pcf_code = pcf_code;
	}

	public String getRep_code() {
		return rep_code;
	}

	public void setRep_code(String rep_code) {
		this.rep_code = rep_code;
	}
}
