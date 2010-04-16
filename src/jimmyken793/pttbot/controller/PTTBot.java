package jimmyken793.pttbot.controller;

import java.awt.Dimension;
import java.io.PrintStream;
import java.lang.reflect.Constructor;

import org.zhouer.zterm.Resource;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteHandlerBinding;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

public class PTTBot implements HumanControl {
	private PrintStream err = System.out;
	private static SiteResource sresource = new SiteResource();
	private SiteConfig sconfig;
	private static SiteHandlerBinding shbind = new SiteHandlerBinding();
	private EventHandler[] events;
	private String[] events_list;
	private int event_count;
	private Resource rc;

	public PTTBot(Resource rc) {
		sconfig = new SiteConfig();
		this.rc = rc;
		String l = rc.getStringValue("bot.events");
		if (l != null) {
			events_list = l.split(",");
		}
		if (events_list == null) {
			events_list = new String[0];
		}
	}

	private EventHandler getEvent(String name, TextArray t, Terminal terminal) {
		try {
			Class cl = Class.forName("jimmyken793.pttbot.events.Event" + name);
			Class cl1 = Class.forName("jimmyken793.pttbot.events.EventHandler");
			Constructor constructor;
			try {
				constructor = cl.getConstructor(new Class[] { TextArray.class, Terminal.class, SiteResource.class, SiteConfig.class });
			} catch (Exception exc) {
				constructor = cl1.getConstructor(new Class[] { TextArray.class, Terminal.class, SiteResource.class, SiteConfig.class });
			}
			EventHandler handler = (EventHandler) constructor.newInstance(new Object[] { t, terminal, sresource, sconfig });
			return handler;
		} catch (Exception exc) {
			err.println("create Event " + name + " failed");
			exc.printStackTrace();
			return null;
		}

	}

	public void react(TextArray t, Terminal terminal) {
		if (events == null) {
			events = new EventHandler[events_list.length];
			event_count = 0;
			for (int i = 0; i < events_list.length; i++) {
				EventHandler e = getEvent(events_list[i], t, terminal);
				if (e != null) {
					events[event_count++] = e;
				}
			}
		}
		for (int i = 0; i < event_count; i++) {
			if (events[i].check(t, terminal, sresource, sconfig)) {
				events[i].perform(t, terminal, sresource, sconfig);
			}
		}
		/*
		 * if (t.getLine(t.getCrow()) != null) { if
		 * (sresource.containsKey(t.getLine(t.getCrow()))) { err.print("found:"
		 * + sresource.get(t.getLine(t.getCrow())) + "\n"); err.println("Line:"
		 * + t.getCrow() + ", Length:" + t.getLine(t.getCrow()).length());
		 * String e = sresource.get(t.getLine(t.getCrow()));
		 * 
		 * if (shbind.containsKey(e)) { String h = shbind.get(e); try { String
		 * name = "jimmyken793.pttbot.events." + h; String name1 =
		 * "jimmyken793.pttbot.events.EventHandler"; err.println(name); Class cl
		 * = Class.forName(name); Class cl1 = Class.forName(name1); Constructor
		 * constructor; try { constructor = cl.getConstructor(new Class[] {
		 * TextArray.class, Terminal.class, SiteResource.class, SiteConfig.class
		 * }); } catch (Exception exc) { constructor = cl1.getConstructor(new
		 * Class[] { TextArray.class, Terminal.class, SiteResource.class,
		 * SiteConfig.class }); } EventHandler handler = (EventHandler)
		 * constructor.newInstance(new Object[] { t, terminal, sresource,
		 * sconfig }); handler.perform(t, terminal, sresource, sconfig); } catch
		 * (Exception exc) { err.println("create Event " + h + " failed");
		 * exc.printStackTrace(); } } } else { err.print("notfound:" +
		 * t.getLine(t.getCrow()) + "\n"); // vt.printBuffer(); } }
		 */
	}
}
