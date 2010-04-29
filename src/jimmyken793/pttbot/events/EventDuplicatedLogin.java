package jimmyken793.pttbot.events;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.terminal.Terminal;

public class EventDuplicatedLogin extends EventHandler {
	static String[] lines = new String[6];

	public EventDuplicatedLogin(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		super(t, terminal, sresource, sconfig);
		lines[0] = "注意: 您有其它連線已登入此帳號。";
		lines[1] = " 同時多次登入同個帳號可能導致文章數或金錢異常，";
		lines[2] = " 且本站不受理因多重登入造成的損失。";
		lines[3] = "";
		lines[4] = "您想刪除其他重複登入的連線嗎？[Y/n]    ";
	}
	private int length=6;

	public boolean check(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		int max = t.getMrow();
		for (int i = max; i >max-length; i--) {
			if (!(lines[length-1-max+i]==null||t.getLine(i).regionMatches(false,0,lines[length-1-max+i],0,lines[length-1-max+i].length()))) {
				return false;
			}
		}
		return true;
	}

	public void perform(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		System.out.println("Duplicated Login!");
		terminal.pasteText("n\n");
	}

}
