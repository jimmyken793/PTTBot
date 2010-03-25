package jimmyken793;

import java.util.HashMap;

public class Resource {
	private HashMap defmap;
	public String read(String key){
		return (String)defmap.get(key);
	}
	public Resource() {
		defmap = new HashMap();
		defmap.put("LOGIN_PROMPT", "請輸入代號，或以 guest 參觀，或以 new 註冊:               ");
	}
}
