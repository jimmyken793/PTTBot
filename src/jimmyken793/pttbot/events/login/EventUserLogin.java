package jimmyken793.pttbot.events.login;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.controller.PTTBot;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public class EventUserLogin extends EventHandler {
	public EventUserLogin(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		super(bot,sresource,sconfig);
	}

	static int call = 0;
	static final String login_prompt="請輸入代號，或以 guest 參觀，或以 new 註冊:               ";
	public boolean perform(PTTBot bot, ResourceMap sresource,ResourceMap sconfig) {
		if (EventUserLogin.call == 0) {
			if (sconfig.containsKey("Username")){
				terminal.pasteText(sconfig.get("Username")+"\n");
			}else{
				System.out.println("Username Not Found");
			}
			EventUserLogin.call++;
		}
		return false;
	}
	public boolean check(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		if(tarray.getLine(tarray.getCrow()).matches(login_prompt)){
			return true;
		}
		return false;
	}
}
