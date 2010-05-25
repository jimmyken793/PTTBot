package jimmyken793.pttbot.events.login;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.controller.PTTBot;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public class EventUserPassword extends EventHandler {
	public EventUserPassword(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		super(bot,sresource,sconfig);
	}

	static int call = 0;
	static final String password_prompt="請輸入您的密碼: ";
	public boolean perform(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		if (EventUserPassword.call == 0) {
			if (sconfig.get("Password") != null){
				System.out.print("password\n");
				terminal.pasteText(sconfig.get("Password") + "\n");
			}else{
				System.err.print("Password Not Found");
			}
			EventUserPassword.call++;
		}
		return false;
	}

	public boolean check(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		if(tarray.getLine(tarray.getCrow()).matches(password_prompt)){
			return true;
		}
		return false;
	}
}
