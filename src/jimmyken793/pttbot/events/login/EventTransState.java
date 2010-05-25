package jimmyken793.pttbot.events.login;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.controller.PTTBot;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.terminal.Terminal;

public class EventTransState extends EventHandler {

	public EventTransState(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
	}

	@Override
	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		int mode = bot.getmode();
		switch (mode) {
		case PTTBot.MODE_LOGIN:
			if (tarray.getLine(1).contains("【主功能表】")) {
				return true;
			}
			break;
		case PTTBot.MODE_MAINMENU:
			break;
		}
		return false;
	}

	@Override
	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		int mode = bot.getmode();
		switch (mode) {
		case PTTBot.MODE_LOGIN:
			bot.setmode(PTTBot.MODE_MAINMENU);
			return true;
		case PTTBot.MODE_MAINMENU:
			break;
		}
		return false;
	}

}
