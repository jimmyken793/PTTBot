package jimmyken793.pttbot.events;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public class EventUserLogin extends EventHandler {
	public EventUserLogin() {
		super();
	}

	static int call = 0;
	static final String login_prompt="請輸入代號，或以 guest 參觀，或以 new 註冊:               ";
	public void perform(TextArray t, Terminal terminal,SiteResource sresource,SiteConfig sconfig) {
		if (EventUserLogin.call == 0) {
			if (sconfig.get("Username") != null)
				terminal.pasteText(sconfig.get("Username")+"\n");
			EventUserLogin.call++;
		}
	}
	public boolean check(TextArray t, Terminal terminal,SiteResource sresource,SiteConfig sconfig) {
		if(t.getLine(t.getCrow())==login_prompt){
			return true;
		}
		return false;
	}
}
