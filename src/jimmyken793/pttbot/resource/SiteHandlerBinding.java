package jimmyken793.pttbot.resource;

public class SiteHandlerBinding extends ResourceMap {
	protected void loadDefault(){
		put("LOGIN_PROMPT", "EventUserLogin");
		put("PASSWORD_PROMPT", "EventUserPassword");
	}
	
	public SiteHandlerBinding(String filename) {
		super(filename);
	}
}
