package idv.jimmyken793.pttbot.events;

import idv.jimmyken793.pttbot.controller.PTTBot;
import idv.jimmyken793.pttbot.resource.ResourceMap;

public class TransState extends EventHandler {

	public TransState(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
	}

	@Override
	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		if (!tarray.getLine(1).contains("【主功能表】")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		bot.setmode("mainMenu");
		return true;
	}

}
