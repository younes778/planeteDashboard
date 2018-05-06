package d2si.apps.planetedashboard.webservice.data;

public class Article {
	private String art_code;
	private String art_lib;
	private String far_lib;

	public Article() {
	}

	public Article(String art_code, String art_lib, String far_lib) {
		super();
		this.art_code = art_code;
		this.art_lib = art_lib;
		this.far_lib = far_lib;
	}

	public String getFar_lib() {
		return far_lib;
	}



	public void setFar_lib(String far_lib) {
		this.far_lib = far_lib;
	}



	public String getArt_code() {
		return art_code;
	}

	public void setArt_code(String art_code) {
		this.art_code = art_code;
	}

	public String getArt_lib() {
		return art_lib;
	}

	public void setArt_lib(String art_lib) {
		this.art_lib = art_lib;
	}



}
