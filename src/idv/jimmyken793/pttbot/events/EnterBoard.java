package idv.jimmyken793.pttbot.events;

import idv.jimmyken793.pttbot.ScreenRecognizer;
import idv.jimmyken793.pttbot.controller.PTTBot;
import idv.jimmyken793.pttbot.resource.ResourceMap;
import idv.jimmyken793.pttbot.terminal.Keyboard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EnterBoard extends EventHandler {
	public EnterBoard(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
	}

	@Override
	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		return (!done) && ScreenRecognizer.is_BoardList(tarray);
	}

	int max_id = 0;
	boolean done = false;

	@Override
	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {

		String board = tarray.getLine(tarray.getCrow());
		Matcher m = Pattern.compile("●? *([0-9]+) +ˇ?([^\\s]+) +([^\\s].*)").matcher(board);
		if (m.find()) {
			int id = new Integer(m.group(1));
			if (id < max_id) {
				done = true;
				System.out.println("done");
			} else {
				max_id = id;
				bot.setState_var("boardname", m.group(2));
				terminal.pasteText(Keyboard.K_DOWN);
				terminal.pasteText(Keyboard.K_RIGHT);
			}
		}
		return false;
	}
}
