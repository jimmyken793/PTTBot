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
	private EventHandler[][] events;
	private int event_count;
	private TextArray textArray;
	private Terminal terminal;
	private String[] modes;
	public static final int MODE_LOGIN = 0;
	public static final int MODE_MAINMENU = 1;
	public static final int MODE_NUM = 2;
	private String[][] event_names;
	private int mode = MODE_LOGIN;

	private String[] getEventList(String key) {
		String l = shbind.get("bot.events." + key);
		if (l != null) {
			return l.split(",");
		} else {
			return new String[0];
		}
	}

	public PTTBot(Resource rc, TextArray t, Terminal terminal) {
		modes = new String[MODE_NUM];
		modes[0] = "login";
		modes[1] = "mainMenu";
		sconfig = new ResourceMap(".zterm_pttconfig");
		event_names = new String[MODE_NUM][];
		for (int i = 0; i < MODE_NUM; i++) {
			event_names[i] = getEventList(modes[i]);
		}
		this.terminal = terminal;
		textArray = t;
		events = new EventHandler[MODE_NUM][];
		for (int mode = 0; mode < MODE_NUM; mode++) {
			events[mode] = new EventHandler[event_names[mode].length];
			for (int i = 0; i < event_names[mode].length; i++) {
				EventHandler e = getEvent(modes[mode],event_names[mode][i], textArray, this.terminal);
				if (e != null) {
					events[mode][i] = e;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private EventHandler getEvent(String mode,String name, TextArray t, Terminal terminal) {
		try {
			Class<EventHandler> cl = (Class<EventHandler>) Class.forName("jimmyken793.pttbot.events."+mode+".Event" + name);
			Class<EventHandler> cl1 = (Class<EventHandler>) Class.forName("jimmyken793.pttbot.events.EventHandler");
			Constructor<EventHandler> constructor;
			try {
				constructor = cl.getConstructor(new Class[] { PTTBot.class, ResourceMap.class, ResourceMap.class });
			} catch (Exception exc) {
				constructor = cl1.getConstructor(new Class[] { PTTBot.class, ResourceMap.class, ResourceMap.class });
			}
			EventHandler handler = (EventHandler) constructor.newInstance(new Object[] { this, sresource, sconfig });
			return handler;
		} catch (Exception exc) {
			err.println("create Event " + name + " failed");
			exc.printStackTrace();
			return null;
		}

	}

	public synchronized void react() {
		boolean changed = false;
		for (int i = 0; i < events[mode].length && !changed; i++) {
			if (events[mode][i].check(this, sresource, sconfig)) {
				if(!changed)
					changed = events[mode][i].perform(this, sresource, sconfig);
			}
		}
		if (changed) {
			System.out.println("mode change into " + mode);
			react();
		}
	}

	public void setmode(int mode) {
		if (mode >= 0 && mode < MODE_NUM)
			this.mode = mode;
	}
	public int getmode() {
		return mode;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public TextArray getTextArray() {
		return textArray;
	}
}
