package jimmyken793.pttbot.events.mainMenu;

import java.util.HashMap;

import jimmyken793.pttbot.Actions;
import jimmyken793.pttbot.ScreenRecognizer;
import jimmyken793.pttbot.controller.PTTBot;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.terminal.Keyboard;

public class EventMoveCursor extends EventHandler {
	HashMap<String, Integer> menu=new HashMap<String, Integer>();
	public EventMoveCursor(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
	}
	@Override
	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		return ScreenRecognizer.is_mainMenu(tarray);
	}
	private static final String cursor="●";
	private static final String target="(F)avorite     【 我 的 最愛 】";
	@Override
	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		boolean m=Actions.moveCursor(terminal, tarray, cursor, target);
		if(m){
			terminal.pasteText(new String(Keyboard.K_RIGHT));
			bot.setmode("boardList");
		}
		return m;
	}

}
