package jimmyken793.pttbot.events;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public class EventUserLogin extends EventHandler {
	public EventUserLogin(TextArray t, Terminal terminal,ResourceMap sresource,ResourceMap sconfig) {
		super(t, terminal,sresource,sconfig);
	}

	static int call = 0;
	static final String login_prompt="請輸入代號，或以 guest 參觀，或以 new 註冊:               ";
	public void perform(TextArray t, Terminal terminal,ResourceMap sresource,ResourceMap sconfig) {
		if (EventUserLogin.call == 0) {
			if (sconfig.containsKey("Username")){
				terminal.pasteText(sconfig.get("Username")+"\n");
			}else{
				System.out.println("Username Not Found");
			}
			EventUserLogin.call++;
		}
	}
	public boolean check(TextArray t, Terminal terminal,ResourceMap sresource,ResourceMap sconfig) {
		if(t.getLine(t.getCrow()).matches(login_prompt)){
			return true;
		}
		return false;
	}
}
