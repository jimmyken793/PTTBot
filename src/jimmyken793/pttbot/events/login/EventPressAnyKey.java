package jimmyken793.pttbot.events.login;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.controller.PTTBot;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.Terminal;

public class EventPressAnyKey extends EventHandler {
	
	public EventPressAnyKey(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
	}

	static final String pattern=" ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄ 請按任意鍵繼續 ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄  "; 
	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		if(tarray.getLine(tarray.getMrow()).matches(pattern)){
			return true;
		}
		return false;
	}

	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		int max=tarray.getCrow();
		for(int i=1;i<=max;i++){
			System.out.println(tarray.getLine(i));
		}
		terminal.pasteText("\n");
		return false;
	}

}
