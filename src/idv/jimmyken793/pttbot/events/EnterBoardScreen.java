package idv.jimmyken793.pttbot.events;

import idv.jimmyken793.pttbot.controller.PTTBot;
import idv.jimmyken793.pttbot.resource.ResourceMap;
import idv.jimmyken793.pttbot.terminal.Keyboard;

public class EnterBoardScreen extends EventHandler {

	public EnterBoardScreen(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
	}

	static final String pattern = " ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄ 請按任意鍵繼續 ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄  ";

	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		if (tarray.getLine(tarray.getMrow()).matches(pattern)) {

			for (int i = 1; i <= tarray.getMrow(); i++) {
				System.out.println(tarray.getLine(i));
			}
			return true;
		}
		return false;
	}

	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		int max = tarray.getCrow();
		String content = new String();
		for (int i = 1; i < max - 3; i++) {
			content += tarray.getLine(i) + "\n";
		}
		;
		bot.logEnterBoard((String) bot.getState_var("boardname"), content);
		terminal.pasteText(" ");
		return false;
	}

}
