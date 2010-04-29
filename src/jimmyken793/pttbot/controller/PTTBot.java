package jimmyken793.pttbot.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import java.lang.reflect.Constructor;

import org.zhouer.zterm.Resource;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteHandlerBinding;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public class PTTBot implements HumanControl {
	private PrintStream err = System.out;
	private static ResourceMap sresource = new ResourceMap(".zterm_pttresource");
	private ResourceMap sconfig;
	private static ResourceMap shbind = new ResourceMap(".zterm_botbinding");
	private EventHandler[] events;
	private String[] events_list;
	private int event_count;
	private Resource rc;
	private TextArray textArray;
	private Terminal terminal;

	public PTTBot(Resource rc, TextArray t, Terminal terminal) {
		sconfig = new ResourceMap(".zterm_pttconfig");
		this.rc = rc;
		String l = shbind.get("bot.events");
		if (l != null) {
			events_list = l.split(",");
		}
		if (events_list == null) {
			events_list = new String[0];
		}
		this.terminal = terminal;
		textArray = t;
		events = new EventHandler[events_list.length];
		event_count = 0;
		for (int i = 0; i < events_list.length; i++) {
			EventHandler e = getEvent(events_list[i], textArray, this.terminal);
			if (e != null) {
				events[event_count++] = e;
			}
		}
	}

	private EventHandler getEvent(String name, TextArray t, Terminal terminal) {
		try {
			Class<EventHandler> cl = (Class<EventHandler>) Class.forName("jimmyken793.pttbot.events.Event" + name);
			Class<EventHandler> cl1 = (Class<EventHandler>) Class.forName("jimmyken793.pttbot.events.EventHandler");
			Constructor constructor;
			try {
				constructor = cl.getConstructor(new Class[] { TextArray.class, Terminal.class, ResourceMap.class, ResourceMap.class });
			} catch (Exception exc) {
				constructor = cl1.getConstructor(new Class[] { TextArray.class, Terminal.class, ResourceMap.class, ResourceMap.class });
			}
			EventHandler handler = (EventHandler) constructor.newInstance(new Object[] { t, terminal, sresource, sconfig });
			return handler;
		} catch (Exception exc) {
			err.println("create Event " + name + " failed");
			exc.printStackTrace();
			return null;
		}

	}

	public void react() {
		for (int i = 0; i < event_count; i++) {
			if (events[i].check(textArray, terminal, sresource, sconfig)) {
				events[i].perform(textArray, terminal, sresource, sconfig);
			}
		}
	}
}
