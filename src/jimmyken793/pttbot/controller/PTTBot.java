package jimmyken793.pttbot.controller;

import java.awt.Dimension;
import java.io.PrintStream;
import java.lang.reflect.Constructor;

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

	public PTTBot() {
		sconfig = new SiteConfig();
	}

	public void react(TextArray t, Terminal terminal) {
		if (events == null) {
			for (int i = 0; i < events_list.length; i++) {
				try {
					String name = events_list[i];
					String name1 = "jimmyken793.pttbot.events.EventHandler";
					Class cl = Class.forName(name);
					Class cl1 = Class.forName(name1);
					Constructor constructor;
					try {
						constructor = cl.getConstructor(new Class[] { TextArray.class, Terminal.class, SiteResource.class, SiteConfig.class });
					} catch (Exception exc) {
						constructor = cl1.getConstructor(new Class[] { TextArray.class, Terminal.class, SiteResource.class, SiteConfig.class });
					}
					EventHandler handler = (EventHandler) constructor.newInstance(new Object[] { t, terminal, sresource, sconfig });
				} catch (Exception exc) {
					err.println("create Event " + events_list[i] + " failed");
					exc.printStackTrace();
				}
			}
		}
		if (t.getLine(t.getCrow()) != null) {
			if (sresource.containsKey(t.getLine(t.getCrow()))) {
				err.print("found:" + sresource.get(t.getLine(t.getCrow())) + "\n");
				err.println("Line:" + t.getCrow() + ", Length:" + t.getLine(t.getCrow()).length());
				String e = sresource.get(t.getLine(t.getCrow()));

				if (shbind.containsKey(e)) {
					String h = shbind.get(e);
					try {
						String name = "jimmyken793.pttbot.events." + h;
						String name1 = "jimmyken793.pttbot.events.EventHandler";
						err.println(name);
						Class cl = Class.forName(name);
						Class cl1 = Class.forName(name1);
						Constructor constructor;
						try {
							constructor = cl.getConstructor(new Class[] { TextArray.class, Terminal.class, SiteResource.class, SiteConfig.class });
						} catch (Exception exc) {
							constructor = cl1.getConstructor(new Class[] { TextArray.class, Terminal.class, SiteResource.class, SiteConfig.class });
						}
						EventHandler handler = (EventHandler) constructor.newInstance(new Object[] { t, terminal, sresource, sconfig });
						handler.perform(t, terminal, sresource, sconfig);
					} catch (Exception exc) {
						err.println("create Event " + h + " failed");
						exc.printStackTrace();
					}
				}
			} else {
				err.print("notfound:" + t.getLine(t.getCrow()) + "\n");
				// vt.printBuffer();
			}
		}
	}
}
