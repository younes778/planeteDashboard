package d2si.apps.planetedashboard.database.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Article extends RealmObject {
	@PrimaryKey
	private String art_code;
	private String art_lib;

	public Article() {

	}

	public Article(String art_code, String art_lib) {
		super();
		this.art_code = art_code;
		this.art_lib = art_lib;
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
