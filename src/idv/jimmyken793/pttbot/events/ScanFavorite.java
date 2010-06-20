package idv.jimmyken793.pttbot.events;

import idv.jimmyken793.pttbot.ScreenRecognizer;
import idv.jimmyken793.pttbot.controller.PTTBot;
import idv.jimmyken793.pttbot.resource.ResourceMap;
import idv.jimmyken793.pttbot.terminal.Keyboard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScanFavorite extends EventHandler {
	static int depth = 0;

	public ScanFavorite(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
	}

	@Override
	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		return (!done) && ScreenRecognizer.is_BoardList(tarray);
	}

	static int max_id = 0;
	static boolean done = false;

	@Override
	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {

		System.out.println("scan board");
		int mrow = tarray.getMrow();
		Pattern p = Pattern.compile("●? *([0-9]+) +ˇ?([^\\s]+) +([^\\s].*)");
		for (int i = 0; i < mrow - 4; i++) {
			String board = tarray.getLine(i + 4);
			if(board=="")
				break;
			Matcher m = p.matcher(board);
			if (m.matches()) {
				int id = new Integer(m.group(1));
				if (id < max_id) {
					done = true;
					max_id = 0;
					bot.setmode(bot.get_mode_id("scanBoard"));
					return true;
				} else if (id > max_id) {
					max_id = id;
				}
				bot.add_board(m.group(2),m.group(3));
			}else{
				System.out.println(board);
			}
		}
		terminal.pasteText(Keyboard.K_PAGE_DOWN);
		return false;
	}

}
