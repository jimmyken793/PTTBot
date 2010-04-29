package jimmyken793.pttbot.events;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.Terminal;

public class EventPressAnyKey extends EventHandler {
	
	public EventPressAnyKey(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		super(t, terminal, sresource, sconfig);
	}

	static final String pattern=" ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄ 請按任意鍵繼續 ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄  "; 
	public boolean check(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		if(t.getLine(t.getMrow()).matches(pattern)){
			return true;
		}
		return false;
	}

	public void perform(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		int max=t.getCrow();
		for(int i=1;i<=max;i++){
			System.out.println(t.getLine(i));
		}
		terminal.pasteText("\n");
	}

}
