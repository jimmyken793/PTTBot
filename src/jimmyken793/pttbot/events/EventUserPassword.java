package jimmyken793.pttbot.events;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public class EventUserPassword extends EventHandler {
	public EventUserPassword() {
		super();
	}

	static int call = 0;
	static final String password_prompt="請輸入您的密碼: ";
	public void perform(TextArray t, Terminal terminal,SiteResource sresource,SiteConfig sconfig) {
		if (EventUserPassword.call == 0) {
			if (sconfig.get("Password") != null){
				terminal.pasteText(sconfig.get("Password") + "\n");
				System.out.print(sconfig.get("Password") + "\n");
			}else{
				System.err.print("Password Not Found");
			}
			EventUserPassword.call++;
		}
	}

	public boolean check(TextArray t, Terminal terminal,SiteResource sresource,SiteConfig sconfig) {
		if(t.getLine(t.getCrow())==password_prompt){
			return true;
		}
		return false;
	}
}
