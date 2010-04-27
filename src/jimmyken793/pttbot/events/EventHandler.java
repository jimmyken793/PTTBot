package jimmyken793.pttbot.events;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public abstract class EventHandler {
	protected Terminal terminal;
	protected TextArray tarray;
	protected ResourceMap sresource;
	protected ResourceMap sconfig;
	public void setTerminal(Terminal t) {
		terminal=t;
	}
	public void setSiteResource(SiteResource sresource) {
		this.sresource=sresource;
	}
	public void setSiteConfig(SiteConfig sconfig) {
		this.sconfig=sconfig;
	}

	public EventHandler(TextArray t, Terminal terminal,ResourceMap sresource,ResourceMap sconfig){
		this.terminal=terminal;
		this.tarray=t;
		this.sresource=sresource;
		this.sconfig=sconfig;
	}
	public abstract void perform(TextArray t, Terminal terminal,ResourceMap sresource,ResourceMap sconfig);
	public abstract boolean check(TextArray t, Terminal terminal,ResourceMap sresource,ResourceMap sconfig);
}
